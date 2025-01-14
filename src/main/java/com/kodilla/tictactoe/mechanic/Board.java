package com.kodilla.tictactoe.mechanic;

public class Board {

    private final char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    public boolean makeMove(int field, char player) {
        int row = (field - 1) / 3;
        int col = (field - 1) % 3;
        try {
            if (board[row][col] == ' ') {
                board[row][col] = player;
                return true;
            }
            return false;
        }

        return false;
    }
}
