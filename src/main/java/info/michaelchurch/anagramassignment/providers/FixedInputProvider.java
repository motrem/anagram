package info.michaelchurch.anagramassignment.providers;

import java.util.Queue;

/**
 * Implementation which is instantiated with a Queue and returns elements
 */
public class FixedInputProvider implements InputProvider {

    private final Queue<String> lines;

    public FixedInputProvider(Queue<String> lines) {
        this.lines = lines;
    }

    @Override
    public boolean hasNextLine() {
        return lines.peek() != null;
    }

    @Override
    public String nextLine() {
        return lines.poll();
    }

}
