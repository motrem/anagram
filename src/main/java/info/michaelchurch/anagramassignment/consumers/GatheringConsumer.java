package info.michaelchurch.anagramassignment.consumers;

import java.util.StringJoiner;

/**
 * Implementation which gathers the string it pools for retrieval. This is intended for test/dev use
 */
public class GatheringConsumer implements OutputConsumer {

    private StringJoiner sj = new StringJoiner("\n");

    @Override
    public void consume(String output) {
        sj.add(output);
    }

    public String retrieveOutput(){
        return sj.toString();
    }
}
