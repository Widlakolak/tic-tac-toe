package com.kodilla.tictactoe.mechanic;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final char[][] board;
    private final int[][] referenceBoard;
    private int moveCount = 0;
    private final List<Integer> fieldList = new ArrayList<>();
    private final int boardSize;
    private final int rows;
    private final int cols;

    public Board(int rows, int cols) {
        this.board = new char[rows][cols];
        this.referenceBoard = new int[rows][cols];
        this.boardSize = rows * cols;
        this.rows = rows;
        this.cols = cols;

        int fieldNumber = 1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
                referenceBoard[i][j] = fieldNumber;
                fieldList.add(fieldNumber);
                fieldNumber++;
            }
        }
    }

    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
        moveCount = 0;
        fieldList.clear();
        for (int i = 1; i <= boardSize; i++) {
            fieldList.add(i);
        }
    }

    public void undoMove(int field) {
        int row = (field - 1) / cols;
        int col = (field - 1) % cols;

        if (board[row][col] != ' ') {
            board[row][col] = ' ';
            moveCount--;
            fieldList.add(field);
        }
    }

    public boolean makeMove(int field, char player) {
        if (field < 1 || field > boardSize) {
            return false;
        }

        int row = (field - 1) / cols;
        int col = (field - 1) % cols;

        if (board[row][col] != ' ') {
            return false;
        }

        board[row][col] = player;
        moveCount++;
        fieldList.remove(Integer.valueOf(field));
        return true;
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

    public List<Integer> getFieldList() {
        return fieldList;
    }

    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
}
