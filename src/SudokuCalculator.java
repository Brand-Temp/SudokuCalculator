/**
 * An attempt to make a search algorithm to solve Sudoku puzzles.
 * Input is read in from a text file, give as a command line argument, of the form:
 * 1. a square number n - e.g. 9, for a regular sudoku grid
 * 2. coordinates in the n*n grid, and their corresponding value between 1 and n - e.g. 0,0,1 would place a 1 in the
 *    very first box.
 *
 * @author Brandon James Temple
 * @since 04/07/2018
 * @version 0.1
 */

import java.io.*;
import java.util.LinkedList;

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
        SudokuNode root;
        try {
            BufferedReader r = new BufferedReader(new FileReader(f));
            int gridSize = Integer.parseInt(r.readLine());
            if (gridSize <= 0) {
                System.out.println("given gridsize is invalid, it must be > 0");
                return;
            }
            double square = Math.sqrt(gridSize);
            if (square % 1 != 0) {
                System.out.println("gridesize is not a square number, so no proper sudoku grid can be constructed sorry :(");
                return;
            }

            root = new SudokuNode(gridSize);
            String line = r.readLine();
            while(line != null) {
                String values[] = line.split(",");
                if(values.length != 3) {
                    System.out.println("The coordinate-value pair was not formed properly. Should be x,y,v");
                    return;
                }
                int x = Integer.parseInt(values[0]);
                int y = Integer.parseInt(values[1]);
                int v = Integer.parseInt(values[2]);
                root.addConstraint(x,y,v);
                line = r.readLine();
            }
            r.close();
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found :( \nTry again, there might have been a typo");
            return;
        } catch (IOException e) {
            System.out.println("The file contained invalid characters, sorry. It should only contain numerals and commas.");
            return;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return;
        }

        LinkedList<SudokuNode> fringe = new LinkedList<>();
        fringe.add(root);
        int c = 0;
        while(fringe.size() != 0) {
            SudokuNode exp = fringe.removeFirst();
            if (exp.testComplete()) {
                System.out.println("A solution was found!");
                System.out.println("Round: " + c);
                exp.print();
                System.exit(0);
            }
            LinkedList<SudokuNode> children = exp.getChildren();
            if (children.size() > 0) {
                fringe.addAll(0, children);
            }
            /*System.out.println("Round: " + c);
            exp.print();
            System.out.println("");*/
            c++;
        }
        System.out.println("A solution could not be found :'(");
    }


}
