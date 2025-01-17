package com.kodilla.tictactoe.mechanic;

public class Board {

    private final char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    private final int[][] referenceBoard = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    private int moveCount = 0;

    public boolean makeMove(int field, char player) {
        int row = (field - 1) / 3;
        int col = (field - 1) % 3;
        try {
            if (board[row][col] == ' ') {
                board[row][col] = player;
                moveCount++;
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public char[][] getBoard() {
        return board;
    }

    public int[][] getReferenceBoard() {
        return referenceBoard;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getBoardSize() {
        return board.length * board[0].length;
    }
}
