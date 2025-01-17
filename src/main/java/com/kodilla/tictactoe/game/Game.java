package com.kodilla.tictactoe.game;

import com.kodilla.tictactoe.logic.DrawChecker;
import com.kodilla.tictactoe.logic.WinChecker;
import com.kodilla.tictactoe.mechanic.Board;

import java.util.Scanner;

public class Game {

    private final Board board = new Board();
    private final DrawChecker drawChecker = new DrawChecker();
    private final WinChecker winChecker = new WinChecker();
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
        System.out.println("The player who succeeds in placing three of their marks\nin a horizontal, vertical, or diagonal row first is the winner.");
        printReferenceBoard(board.getReferenceBoard());

        while (true) {
            char player = isXTurn ? X : O;
            System.out.println(player + " turn. Enter a number between 1 and 9:");
            try {
            field = keyboardScan.nextInt();
            if (field < 1 || field > 9) {
                printReferenceBoard(board.getReferenceBoard());
                System.out.println("Now try harder!\nYou entered : " + field + "\nTip: Look at the reference board\nand enter the number from the field\nyou want to place your " + player + " sign.");
            } else {
                if (board.makeMove(field, player)) {
                    System.out.println(player + " to position " + field);
                    printBoard(board.getBoard());
                    if (winChecker.win(board)) {
                        System.out.println("Congratulations!! " + player + " won!");
                        break;
                    }
                    if (drawChecker.draw(board)) {
                        System.out.println("Draw! No one won.");
                        break;
                    }
                    isXTurn = !isXTurn;
                } else {
                    System.out.println(field + " - That space is taken!");
                }

            }
            }
            catch (Exception e) {
                String invalidInput = keyboardScan.next();
                printReferenceBoard(board.getReferenceBoard());
                System.out.println("Now try harder!\nYou entered : " + invalidInput  + "\nTip: Look at the reference board\nand enter the number from the field\nyou want to place your " + player + " sign.");
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