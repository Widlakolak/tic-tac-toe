package com.kodilla.tictactoe.game;

import com.kodilla.tictactoe.logic.Bot;
import com.kodilla.tictactoe.logic.DrawChecker;
import com.kodilla.tictactoe.logic.WinChecker;
import com.kodilla.tictactoe.mechanic.Board;

import java.util.Scanner;

public class Game {

    private final Board board;
    private final DrawChecker drawChecker = new DrawChecker();
    private final WinChecker winChecker = new WinChecker();
    private final Bot bot = new Bot();
    private static final char X = 'X';
    private static final char O = 'O';
    private boolean isXTurn = true;

    public Game(int rows, int cols) {
        this.board = new Board(rows, cols);
    }

    public void start() {
        askInput();
    }

    public void askInput() {
        Scanner keyboardScan = new Scanner(System.in);
        int field;

        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("The player who succeeds in placing three of their marks" +
                "\nin a horizontal, vertical, or diagonal row first is the winner.");
        printReferenceBoard(board.getReferenceBoard());

        while (true) {
            char player = isXTurn ? X : O;
            int maxField = board.getBoardSize();

            if (isXTurn) {
                System.out.println(player + " turn. Enter a number between 1 and " + maxField + ":");
                try {
                    field = keyboardScan.nextInt();
                    if (!board.makeMove(field, player)) {
                        printReferenceBoard(board.getReferenceBoard());
                        System.out.println("Now try harder!\nYou entered : " + field +
                                "\nTip: Look at the reference board\nand enter the number from the field" +
                                "\nyou want to place your " + player + " sign.");
                        continue;
                    }
                    System.out.println(player + " to position " + field);
                } catch (Exception e) {
                    String invalidInput = keyboardScan.next();
                    printReferenceBoard(board.getReferenceBoard());
                    System.out.println("Now try harder!\nYou entered : " + invalidInput +
                            "\nTip: Look at the reference board\nand enter the number from the field" +
                            "\nyou want to place your " + player + " sign.");
                    continue;
                }
            } else {
                System.out.println("Computer's turn...");
                field = bot.autoMove(board);
                if (field == 0) {
                    System.out.println("Draw! No one won.");
                    System.out.println("Do you want to play again? (yes/no)");
                    String response1 = keyboardScan.next().toLowerCase();
                    if (!response1.equals("yes")) {
                        System.out.println("Thanks for playing!");
                        break;
                    } else {
                        resetGame();
                        continue;
                    }
                }
                board.makeMove(field, player);
                System.out.println("Computer placed " + player + " on position " + field);
            }

            printBoard(board.getBoard());

            if (winChecker.win(board)) {
                System.out.println("Congratulations!! " + player + " won!");
                System.out.println("Do you want to play again? (yes/no)");
                String response = keyboardScan.next().toLowerCase();
                if (!response.equals("yes")) {
                    System.out.println("Thanks for playing!");
                    break;
                } else {
                    resetGame();
                }
            }
            if (drawChecker.draw(board)) {
                System.out.println("Draw! No one won.");
                System.out.println("Do you want to play again? (yes/no)");
                String response1 = keyboardScan.next().toLowerCase();
                if (!response1.equals("yes")) {
                    System.out.println("Thanks for playing!");
                    break;
                } else {
                    resetGame();
                }
            }
            isXTurn = !isXTurn;
        }
    }

    private void resetGame() {
        board.resetBoard();
        isXTurn = false;
    }

    public static void printBoard(char[][] board) {
        int size = board.length;
        String boardString = "-".repeat(size*4-2);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + (j < board[i].length - 1 ? " | " : ""));
            }
            System.out.println();
            if (i < size - 1) {
                System.out.println(boardString);
            }
        }
        System.out.println();
    }

    public static void printReferenceBoard(int[][] referenceBoard) {
        int size = referenceBoard.length;
        String boardString = "-".repeat(size*5-2);
        System.out.println("Example Board: Use these numbers to make a move.");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < referenceBoard[i].length; j++) {
                if (j < size - 1) {
                    System.out.printf("%02d | ", referenceBoard[i][j]);
                } else {
                    System.out.printf("%02d", referenceBoard[i][j]);
                }
            }
            System.out.println();
            if (i < size - 1) {
                System.out.println(boardString);
            }
        }
    }
}