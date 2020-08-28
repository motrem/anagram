package info.michaelchurch.anagramassignment;

import info.michaelchurch.anagramassignment.consumers.StdOutConsumer;
import info.michaelchurch.anagramassignment.providers.ScannerInputProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    /**
     *
     * @param args First arg should be a path to an existing, accessible, unlocked file in the format described in
     *             the spec eg "C:\Users\michael\Downloads\example2.txt"
     */
    public static void main(String[] args) throws FileNotFoundException {

        if (args.length < 1) {
            throw new IllegalArgumentException("Missing first command-line argument, which should be a file path");
        }

        final String filename = args[0];
        final File file = new File(filename);
        try(final Scanner scanner = new Scanner(file)) {
            new AnagramComputer().computeAnagrams(new ScannerInputProvider(scanner), new StdOutConsumer());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
