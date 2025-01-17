package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.mechanic.Board;

public class WinChecker {

    public boolean win(Board board) {
        char[][] winningBoard = board.getBoard();
        int length = winningBoard.length;
        int reqLength = winningBoard.length;

        for (int i = 0; i < length; i++){
            if (lineCheck(winningBoard, i, 0, 0,1, reqLength) || lineCheck(winningBoard, 0, i, 1, 0, reqLength)){
                return true;
            }
        }
        if (lineCheck(winningBoard, 0, 0, 1, 1, reqLength) || lineCheck(winningBoard, 0, length - 1, 1, -1, reqLength)){
            return true;
        }

        return false;
    }

    private boolean lineCheck(char[][] board, int startRow, int startCol, int stepRow, int stepCol, int reqLength) {

        int count = 0;
        char lastPlayer = ' ';

        for (int i = 0; i < board.length; i++){
            int row = startRow + stepRow * i;
            int col = startCol + stepCol * i;

            if (board[row][col] != ' ' && ( lastPlayer == ' ' || board[row][col] == lastPlayer)) {
                lastPlayer = board[row][col];
                count++;
                if (count == reqLength) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }
}
