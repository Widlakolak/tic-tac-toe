package com.kodilla.tictactoe.game;

import com.kodilla.tictactoe.logic.Bot;
import com.kodilla.tictactoe.logic.DrawChecker;
import com.kodilla.tictactoe.logic.WinChecker;
import com.kodilla.tictactoe.mechanic.Board;
import com.kodilla.tictactoe.mechanic.Ranking;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    private Board board;
    private final DrawChecker drawChecker;
    private final WinChecker winChecker;
    private Bot bot;
    private final Ranking ranking;
    private static final char X = 'X';
    private static final char O = 'O';
    private char playerChar;
    private char opponentChar;
    private boolean isPlayerTurn = true;

    public Game() {
        this.board = new Board(3, 3);
        this.drawChecker = new DrawChecker();
        this.winChecker = new WinChecker();
        this.ranking = new Ranking();
    }

    public Board getBoard() {
        return board;
    }
    

    public void start() {
        ranking.displayRanking();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic-Tac-Toe game!");
        System.out.println("Choose board size (rows and cols):\n(Default - 3 and 3).");
        int rows = 3;
        int cols = 3;

        try {
            rows = scanner.nextInt();
            cols = scanner.nextInt();
            if (rows <= 1 || cols <= 1) {
                throw new IllegalArgumentException("Computer won!\nRows and cols must be greater than 1.");
            }
        } catch (Exception e) {
            System.out.println("Using default size 3x3.");
            scanner.nextLine();
        }

        board = new Board(rows, cols);

        System.out.println("Do you want to play as X or O?\nGame starts with X.");
        try {
            playerChar = scanner.next().toUpperCase().charAt(0);
            if (playerChar != 'X' && playerChar != 'O') {
                throw new IllegalArgumentException("You will play as X.");
            }
        } catch (Exception e) {
            playerChar = 'X';
        }

        opponentChar = (playerChar == 'X') ? 'O' : 'X';
        isPlayerTurn = (playerChar == 'X');

        System.out.println("Choose difficulty level:\n(1 = Easy, 2 = Medium, 3 = Insane)");
        int difficulty = 1;
        try {
            difficulty = scanner.nextInt();
            if (difficulty < 1 || difficulty > 3) {
                throw new IllegalArgumentException("It was difficult... set an easy level?");
            }
        } catch (Exception e) {
            System.out.println("Set to Easy.");
            scanner.nextLine();
        }

        int maxDepth = switch (difficulty) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> Integer.MAX_VALUE;
            default -> 3;
        };
        bot = new Bot(maxDepth);

        askInput();
    }

    public void askInput() {
        Scanner keyboardScan = new Scanner(System.in);
        int field;

        System.out.println("The player who succeeds in placing their marks" +
                "\nin a horizontal, vertical, or diagonal row first is the winner.");
        printReferenceBoard(board.getReferenceBoard());

        while (true) {
            char player = isPlayerTurn ? playerChar : opponentChar;
            int maxField = board.getBoardSize();

            if (isPlayerTurn) {
                System.out.println(player + " turn. Enter a number between 1 and " + maxField + ":");
                try {
                    field = keyboardScan.nextInt();
                    if (!board.makeMove(field, player)) {
                        printReferenceBoard(board.getReferenceBoard());
                        System.out.println("Now try harder!\nYou entered : " + field +
                                "\nTip: Look at the reference board." +
                                "\nWhere you want to place your " + player + " sign?");
                        continue;
                    }
                    System.out.println(player + " to position " + field);
                } catch (InputMismatchException e) {
                    String invalidInput = keyboardScan.next();
                    printReferenceBoard(board.getReferenceBoard());
                    System.out.println("Now try harder!\nYou entered : " + invalidInput +
                            "\nTip: Look at the reference board\nand enter the number from the field" +
                            "\nyou want to place your " + player + " sign.");
                    keyboardScan.nextLine();
                    continue;
                }
            } else {
                System.out.println("Computer's turn (" + opponentChar + ")...");
                field = bot.getMove(board, opponentChar, playerChar);
                if (field == 0) {
                    handleEndGame("Draw! No one won.", "Draw");
                        break;
                    }
                board.makeMove(field, player);
                System.out.println("Computer placed " + player + " on position " + field);
            }

            printBoard(board.getBoard());

            if (winChecker.win(board)) {
                handleEndGame("Congratulations!! " + player + " won!", String.valueOf(player));
                break;
            }
            if (drawChecker.draw(board)) {
                handleEndGame("Draw! No one won.", "Draw");
                break;
            }
            isPlayerTurn = !isPlayerTurn;
        }
    }

    private void handleEndGame(String message, String result) {
        System.out.println(message);
        ranking.updateRanking(result);
        System.out.println("Do you want to play again? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().toLowerCase();
        if (!response.equals("yes")) {
            ranking.displayRanking();
            System.out.println("Thanks for playing!");
        } else {
            resetGame();
            askInput();
        }
    }

    public void resetGame() {
        board.resetBoard();
        isPlayerTurn = true;
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

    public char getPlayerChar() {
        return playerChar;
    }

    public WinChecker getWinChecker() {
        return winChecker;
    }

    public DrawChecker getDrawChecker() {
        return drawChecker;
    }

    public char getOpponentChar() {
        return opponentChar;
    }

    public Bot getBot() {
        return bot;
    }
}