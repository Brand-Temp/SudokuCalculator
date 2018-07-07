/**
 * Companion node class for the Sudoku Calculator
 *
 * @author Brandon James Temple
 * @since 04/07/2018
 * @version 0.1
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SudokuNode {
    private int[][] board;
    private int size;

    public SudokuNode(int s) {
        size = s;
        board = new int[size][size];
    }

    private SudokuNode(int b[][], int x, int y, int v) {
        size = b.length;
        board = new int[size][size];
        for(int i = 0; i < size; i++) {
            board[i] = b[i].clone();
        }
        board[y][x] = v;
    }

    public void addConstraint(int x, int y, int v) throws NumberFormatException {
        if (x >= size || y >= size) {
            throw new NumberFormatException("x or y coord is not within sudoku grid.");
        }
        if (v < 1 || v > size) {
            throw new NumberFormatException("v was not a valid sudoku number.");
        }

        board[y][x] = v;
    }

    public LinkedList<SudokuNode> getChildren() {
        LinkedList<SudokuNode> children = new LinkedList<>();
        // find next spot
        int x = -1;
        int y = -1;
        for (int i = 0; i < size; i++) {
            boolean change = false;
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    y = i;
                    x = j;
                    change = true;
                    break;
                }
            }
            if (change) {
                break;
            }
        }

        // get already used values
        HashSet<Integer> usedValues = new HashSet<>();
        // same row
        for(int i = 0; i < size; i++) {
            if (i == y) {
                continue;
            }
            if (board[i][x] != 0) {
                usedValues.add(board[i][x]);
            }
        }
        //same column
        for(int i = 0; i < size; i++) {
            if (i == x) {
                continue;
            }
            if (board[y][i] != 0) {
                usedValues.add(board[y][i]);
            }
        }
        // same square
        int squareWidth = (int)Math.sqrt(size);
        int xbounds[] = new int[2];
        int ybounds[] = new int[2];
        int initxbound = 0;
        while(true) {
            if (x < initxbound) {
                xbounds[0] = initxbound - squareWidth;
                xbounds[1] = initxbound;
                break;
            } else {
                initxbound += squareWidth;
            }
        }
        int initybound = 0;
        while(true) {
            if (y < initybound) {
                ybounds[0] = initybound - squareWidth;
                ybounds[1] = initybound;
                break;
            } else {
                initybound += squareWidth;
            }
        }
        for (int i = xbounds[0]; i < xbounds[1]; i++) {
            for (int j = ybounds[0]; j < ybounds[1]; j++) {
                if (board[j][i] != 0) {
                    usedValues.add(board[j][i]);
                }
            }
        }
        HashSet<Integer> possibleValues = new HashSet<>();
        for(int i = 1; i <= size; i++) {
            possibleValues.add(i);
        }
        possibleValues.removeAll(usedValues);

        // generate SudokuNode for each valid
        TreeSet<Integer> sortedPossibles = new TreeSet<>();
        sortedPossibles.addAll(possibleValues);
        for (int i: sortedPossibles) {
            children.add(new SudokuNode(board, x, y, i));
        }
        return children;
    }

    private int checkRow(int r) {
        for (int i = 0; i < size; i++) {
            if (board[r][i] == 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean testComplete() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
