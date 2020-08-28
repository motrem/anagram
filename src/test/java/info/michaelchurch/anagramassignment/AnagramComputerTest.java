package info.michaelchurch.anagramassignment;

import info.michaelchurch.anagramassignment.consumers.GatheringConsumer;
import info.michaelchurch.anagramassignment.providers.FixedInputProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class AnagramComputerTest {

    @Test
    public void testComputeAnagrams_specifiedByExample () {

        // Proves the example given in the spec
        final Queue<String> example1 = new LinkedList<>();
        example1.addAll(Arrays.asList((
            "abc\n" +
            "fun\n" +
            "bac\n" +
            "fun\n" +
            "cba\n" +
            "unf\n" +
            "hello"
            ).split("\n")));

        final FixedInputProvider fixedInputProvider = new FixedInputProvider(example1);
        final GatheringConsumer gatheringConsumer = new GatheringConsumer();
        new AnagramComputer().computeAnagrams(fixedInputProvider, gatheringConsumer);

        final String actual = gatheringConsumer.retrieveOutput();

        // expect:

        // abc,bac,cba
        // unf,fun
        // hello

        // **in any order**
        final String []actualLines = actual.split("\n");
        Assert.assertEquals(3, actualLines.length);

        List<String> helloLineWords = new ArrayList<>();
        helloLineWords.add("hello");
        assertExpectedWordsArePresent(helloLineWords, actualLines);

        List<String> abcLineWords = new ArrayList<>();
        abcLineWords.add("abc");
        abcLineWords.add("bac");
        abcLineWords.add("cba");
        assertExpectedWordsArePresent(abcLineWords, actualLines);

        List<String> unfLineWords = new ArrayList<>();
        unfLineWords.add("unf");
        unfLineWords.add("fun");
        assertExpectedWordsArePresent(unfLineWords, actualLines);
    }

    private void assertExpectedWordsArePresent(final Collection expectedWords, final String[] actualLines) {

        try {
            Arrays.asList(actualLines).stream()
                    .filter(line -> Arrays.asList(line.split(",")).containsAll(expectedWords))
                    .findAny()
                    .get();
        } catch (NoSuchElementException e) {
            Assert.fail("Expected words " + expectedWords + " were not Found");
        }
    }

    @Test
    public void testComputeAnagrams_noOutputWhenFileEmpty () {

        final Queue<String> emptyInput = new LinkedList<>();

        final FixedInputProvider fixedInputProvider = new FixedInputProvider(emptyInput);
        final GatheringConsumer gatheringConsumer = new GatheringConsumer();
        new AnagramComputer().computeAnagrams(fixedInputProvider, gatheringConsumer);

        final String actual = gatheringConsumer.retrieveOutput();

        final String expected = "";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testComputeAnagrams_OneWordInFile () {

        final Queue<String> singleWordInput = new LinkedList<>();
        singleWordInput.add("foo");

        final FixedInputProvider fixedInputProvider = new FixedInputProvider(singleWordInput);
        final GatheringConsumer gatheringConsumer = new GatheringConsumer();
        new AnagramComputer().computeAnagrams(fixedInputProvider, gatheringConsumer);

        final String actual = gatheringConsumer.retrieveOutput();

        final String expected = "foo";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testComputeAnagrams_ShouldNotRepeatDuplicateWords () {

        final Queue<String> singleWordInput = new LinkedList<>();
        singleWordInput.add("foo");
        singleWordInput.add("bar");
        singleWordInput.add("foo");

        final FixedInputProvider fixedInputProvider = new FixedInputProvider(singleWordInput);
        final GatheringConsumer gatheringConsumer = new GatheringConsumer();
        new AnagramComputer().computeAnagrams(fixedInputProvider, gatheringConsumer);

        final String actual = gatheringConsumer.retrieveOutput();

        final String expected = "foo\nbar";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetPointsForLongWord() {

        // Also demonstrates that words up to this length are supported
        final String word = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";

        final long actualValue = AnagramComputer.getPointsForWord(word);
        final long expectedValue = 25971130368L;
        Assert.assertEquals(expectedValue, actualValue);
        Assert.assertTrue(actualValue < Long.MAX_VALUE);
    }

    @Test
    public void testGetPointsForWord_AnagramsShouldHaveSameValue() {
        final String word1 = "schoolmaster";
        final String word2 = "theclassroom";
        final long word1Value = AnagramComputer.getPointsForWord(word1);
        final long word2Value = AnagramComputer.getPointsForWord(word2);
        Assert.assertEquals(word1Value, word2Value);
    }

    @Test
    public void testGetPointsForWord_NonAnagramsShouldHaveDifferentValues() {
        final String word1 = "hajji";
        final String word2 = "haika";
        final long word1Value = AnagramComputer.getPointsForWord(word1);
        final long word2Value = AnagramComputer.getPointsForWord(word2);
        Assert.assertNotEquals(word1Value, word2Value);
    }

    @Test
    public void testGetPointsForChar_a() {
        final int actualValue = AnagramComputer.getPointsForChar('a');
        final int expectedValue = 1;
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testGetPointsForChar_z() {
        final int actualValue = AnagramComputer.getPointsForChar('z');
        final int expectedValue = 33554432;
        Assert.assertEquals(expectedValue, actualValue);
    }
}