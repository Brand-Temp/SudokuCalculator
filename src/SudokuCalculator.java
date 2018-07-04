/**
 * An attempt to make a search algorithm to solve Sudoku puzzles.
 * Input is read in from a text file, give as a command line argument, of the form:
 * 1. a square number n
 * 2. coordinates in the n*n grid, and their corresponding value between 1 and n
 *
 * @author Brandon James Temple
 * @since 04/07/2018
 * @version 0.1
 */

import java.io.File;

public class SudokuCalculator {

    /**
     * main - analyses text file, then calls the search
     * @param args
     */
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Incorrect number of command line arguments. Should be 1.");
            return;
        }

        File f = new File(args[0]);
        if(!f.isFile()) {
            System.out.println("'"  + args[0] + "' is not a file.");
        }


    }
}
