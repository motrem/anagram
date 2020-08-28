package info.michaelchurch.anagramassignment;

import org.junit.Test;

import java.io.FileNotFoundException;


public class MainTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMain_shouldFailOnMissingArgument() throws FileNotFoundException {
        Main.main(new String[0]);
    }

    @Test(expected = FileNotFoundException.class)
    public void testMain_shouldFailOnInvalidFilePath () throws FileNotFoundException {
        String args[] = {"This_is_not_a_legit_path"};
        Main.main(args);
    }

}