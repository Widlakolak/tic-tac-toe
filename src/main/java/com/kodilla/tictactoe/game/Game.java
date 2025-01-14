package com.kodilla.tictactoe.game;

import java.util.Scanner;

public class Game {

    char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    int[][] referenceBoard = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    private static final char X = 'X';
    private static final char O = 'O';
    private boolean isXTurn = true;


    public static void main(String[] args) {
        Game game = new Game();
        game.askInput();
    }

    public void askInput() {
        Scanner keyboardScan = new Scanner(System.in);
        int field;

        System.out.println("Welcome to Tic Tac Toe!");
        printBoard(board);
        System.out.println("The player who succeeds in placing three of their marks\nin a horizontal, vertical, or diagonal row first is the winner.");
        printReferenceBoard(referenceBoard);

        while (true) {
            System.out.println((isXTurn ? X : O) + " turn. Enter a number between 1 and 9:");
            field = keyboardScan.nextInt();

            if (field < 1 || field > 9) {
                printReferenceBoard(referenceBoard);
                System.out.println("Now try harder!\nYou entered : " + field + "\nTip: Look at the reference board\nand enter the number from the field\nyou want to place your " + (isXTurn ? X : O) + " sign.");
            } else {
                System.out.println("You selected field: " + field);
                printBoard(board);
                isXTurn = !isXTurn;
            }


        }
    }


    public static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + (j < board[i].length - 1 ? " | " : ""));
            }
            System.out.println();
            if (i < board.length - 1) {
                System.out.println("--+---+--");
            }
        }
        System.out.println();
    }

    public static void printReferenceBoard(int[][] referenceBoard) {
        System.out.println("Example Board: Use these numbers to make a move.");
        for (int i = 0; i < referenceBoard.length; i++) {
            for (int j = 0; j < referenceBoard[i].length; j++) {
                System.out.print(referenceBoard[i][j] + (j < referenceBoard[i].length - 1 ? " | " : ""));
            }
            System.out.println();
            if (i < referenceBoard.length - 1) {
                System.out.println("--+---+---");
            }
        }
    }
}