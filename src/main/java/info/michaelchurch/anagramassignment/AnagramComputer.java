package info.michaelchurch.anagramassignment;

import info.michaelchurch.anagramassignment.consumers.OutputConsumer;
import info.michaelchurch.anagramassignment.providers.InputProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnagramComputer {

    /**
     * Attempts to gather data from the InputProvider and pipes its output to the OutputConsumer
     * @param inputProvider
     * @param outputConsumer
     */
    public void computeAnagrams(final InputProvider inputProvider, final OutputConsumer outputConsumer) {
        int lastWordsLength = 1;
        List<String> wordsOfSameLength = new ArrayList<>();
        while (inputProvider.hasNextLine()) {
            final String word = inputProvider.nextLine();
            final int currentWordsLength = word.length();
            if (currentWordsLength != lastWordsLength) {
                // given words are guaranteed to be ordered by size, then if the current one is longer than
                // the previous, we know the current batch of same-length-words is complete, and so can be processed:
                processWordsOfSameLength(wordsOfSameLength, outputConsumer);
                // reset counters for the next batch:
                lastWordsLength = currentWordsLength;
                wordsOfSameLength = new ArrayList<>();
            }
            wordsOfSameLength.add(word);
        }
        // even though file is completed, we have the last set (the longest words) left to handle:
        processWordsOfSameLength(wordsOfSameLength, outputConsumer);
    }

    private void processWordsOfSameLength(final List<String> wordsOfSameLength, final OutputConsumer outputConsumer) {

        if (wordsOfSameLength.isEmpty()) { return; }

        // if there is only one word, we can print it and finish:
        if (wordsOfSameLength.size() == 1) {
            outputConsumer.consume(wordsOfSameLength.get(0));
            return;
        }

        /*
        Build a Map that will look like eg:

        512:   "foo", "oof"
        23454: "bar"
        523214: "alp","lap","pal"

         */
        final Map<Long, List<String>> pointsToWordsMap = wordsOfSameLength.stream()
                .distinct()
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(AnagramComputer::getPointsForWord));

        for (List<String> wordlist : pointsToWordsMap.values()) {
            outputConsumer.consume(wordlist.stream().collect(Collectors.joining(",")));
        }
    }


    // Each letter has a point value based on binary doubling, so that for two words of the same length, they will
    // have the same points value if and only if they contain the same letters, regardless of order - i.e. are
    // anagrams
    static long getPointsForWord(final String word) {

        long thisWordsPoints = 0;

        for (char c : word.toCharArray()) {
            thisWordsPoints += getPointsForChar(c);
        }
        return thisWordsPoints;
    }

    // a => 1, b => 2, c=> 4 ..... z => 33554432
    static int getPointsForChar(char c){
        double position = c - 97;
        return (int)Math.pow(2.0, position);
    }

}
