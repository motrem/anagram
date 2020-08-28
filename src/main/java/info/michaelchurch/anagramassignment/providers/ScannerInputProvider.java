package info.michaelchurch.anagramassignment.providers;

import java.util.Scanner;

/**
 * Implementation backed by a java.util.Scanner. This implementation does not
 * hold responsibility for closing the scanner provided to its constructor.
 * The scanner gets round the constraint that "The files may not fit into memory all at once"
 */
public class ScannerInputProvider implements InputProvider {

    private final Scanner scanner;

    public ScannerInputProvider(final Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
