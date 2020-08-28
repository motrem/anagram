package info.michaelchurch.anagramassignment.consumers;

/**
 * An implementation simply pipes everything it receives to stdout
 */
public class StdOutConsumer implements OutputConsumer {

    @Override
    public void consume(final String output) {
        System.out.println(output);
    }
}
