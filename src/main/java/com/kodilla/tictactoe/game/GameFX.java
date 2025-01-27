package com.kodilla.tictactoe.game;

import com.kodilla.tictactoe.logic.Bot;
import com.kodilla.tictactoe.logic.DrawChecker;
import com.kodilla.tictactoe.logic.WinChecker;
import com.kodilla.tictactoe.mechanic.Board;
import com.kodilla.tictactoe.mechanic.Ranking;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class GameFX {

    private Board board;
    private final DrawChecker drawChecker;
    private final WinChecker winChecker;
    private Bot bot;
    private final Ranking ranking;
    private static final char X = 'X';
    private static final char O = 'O';
    private char playerChar;
    private char opponentChar;
    private boolean isPlayerTurn;
    private Button[][] buttons;

    public GameFX() {
        this.ranking = new Ranking();
        this.winChecker = new WinChecker();
        this.drawChecker = new DrawChecker();
    }

    public void start(Stage primaryStage) {
        // Inicjalizacja tymczasowej sceny, aby stage miał właściciela
        VBox tempRoot = new VBox();
        Scene tempScene = new Scene(tempRoot, 1, 1);
        primaryStage.setScene(tempScene);
        primaryStage.show();

        // Teraz dialogi mogą używać primaryStage jako właściciela
        ranking.displayRankingFX(primaryStage);
        showAlert(primaryStage, "Welcome!", "The player who succeeds in placing their marks" +
                "\nin a horizontal, vertical, or diagonal row first is the winner.");
        int size = getBoardSize(primaryStage);
        this.playerChar = getPlayerCharacter(primaryStage);
        this.opponentChar = (playerChar == X) ? O : X;
        this.isPlayerTurn = (playerChar == X);

        int difficulty = getDifficulty(primaryStage);
        this.bot = new Bot(difficulty);

        this.board = new Board(size, size);
        buttons = new Button[size][size];

        GridPane grid = createGrid();
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().add(grid);

        // Zamiana sceny na właściwą
        Scene mainScene = new Scene(root, 600, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("TicTacToe");

        if (!isPlayerTurn) {
            makeBotMove(primaryStage);
        }
    }

    private void displayRanking(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle("Current Ranking");
        alert.setHeaderText(null);
        alert.setContentText(ranking.getRankingText());
        alert.showAndWait();
    }

    private int getBoardSize(Stage stage) {
        TextInputDialog dialog = new TextInputDialog("3");
        dialog.initOwner(stage);
        dialog.setTitle("Board Size");
        dialog.setHeaderText("Choose board size (rows and cols)");
        dialog.setContentText("Default size is 3x3:");

        Optional<String> result = dialog.showAndWait();
        int size = 3;
        try {
            size = Integer.parseInt(result.orElse("3"));
            if (size <= 1) throw new IllegalArgumentException();
            return size;
        } catch (Exception e) {
            showAlert(stage, "Invalid Input", "Using default size 3x3.");
            return 3;
        }
    }

    private char getPlayerCharacter(Stage stage) {
        TextInputDialog dialog = new TextInputDialog("X");
        dialog.initOwner(stage);
        dialog.setTitle("Player Choice");
        dialog.setHeaderText("Do you want to play as X or O?");
        dialog.setContentText("Default is X:");

        Optional<String> result = dialog.showAndWait();
        try {
            playerChar = result.orElse("X").toUpperCase().charAt(0);
            if (playerChar != X && playerChar != O) throw new IllegalArgumentException();
            return playerChar;
        } catch (Exception e) {
            showAlert(stage, "Invalid Input", "You will play as X.");
            return X;
        }
    }

    private int getDifficulty(Stage stage) {
        TextInputDialog dialog = new TextInputDialog("2");
        dialog.initOwner(stage);
        dialog.setTitle("Difficulty");
        dialog.setHeaderText("Choose difficulty level (1 = Easy, 2 = Medium, 3 = Insane)");
        dialog.setContentText("Default is Medium:");

        Optional<String> result = dialog.showAndWait();

        int difficulty;
        try {
            difficulty = Integer.parseInt(result.orElse("2"));
            if (difficulty < 1 || difficulty > 3) throw new IllegalArgumentException();

            return switch (difficulty) {
                case 1 -> 1;
                case 2 -> 3;
                case 3 -> Integer.MAX_VALUE;
                default -> 3;
            };
        } catch (Exception e) {
            showAlert(stage,"Invalid Input", "Set to Medium.");
            return 3;
        }
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                buttons[row][col] = button;

                final int r = row;
                final int c = col;

                button.setOnAction(e -> {
                    if (!isPlayerTurn) return;
                    handleMove(r, c, button);
                });

                grid.add(button, col, row);
            }
        }
        return grid;
    }

    private void handleMove(int row, int col, Button button) {
        if (!button.getText().isEmpty()) return;

        char currentPlayer = playerChar;
        int move = row * board.getCols() + col + 1;

        if (board.makeMove(move, currentPlayer)) {
            button.setText(String.valueOf(currentPlayer));

            if (winChecker.win(board)) {
                handleEndGame(currentPlayer + " wins!", String.valueOf(currentPlayer), (Stage) button.getScene().getWindow());
            } else if (drawChecker.draw(board)) {
                handleEndGame("It's a draw!", "Draw", (Stage) button.getScene().getWindow());
            } else {
                isPlayerTurn = false;
                makeBotMove((Stage) button.getScene().getWindow());
            }
        }
    }

    private void makeBotMove(Stage stage) {
        int move = bot.getMove(board, opponentChar, playerChar);
        if (move > 0 && board.makeMove(move, opponentChar)) {
            int row = (move - 1) / board.getCols();
            int col = (move - 1) % board.getCols();
            buttons[row][col].setText(String.valueOf(opponentChar));

            if (winChecker.win(board)) {
                handleEndGame("Computer wins!", String.valueOf(opponentChar), stage);
            } else if (drawChecker.draw(board)) {
                handleEndGame("It's a draw!", "Draw", stage);
            } else {
                isPlayerTurn = true;
            }
        }
    }

    private void handleEndGame(String message, String result, Stage primaryStage) {
        disableAllButtons(true);
        ranking.updateRanking(result);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Game Over");
        alert.setHeaderText(message);
        alert.setContentText("Do you want to play again?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> response = alert.showAndWait();
        if (response.isPresent() && response.get() == yesButton) {
            resetGame(primaryStage);
        } else {
            displayRanking(primaryStage);
            primaryStage.close();        }
    }

    private void disableAllButtons(boolean disable) {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    private void resetGame(Stage primaryStage) {
        disableAllButtons(false);
        board.resetBoard();
        isPlayerTurn = (playerChar == X);
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText("");
            }
        }

        start(primaryStage);
    }

    private void showAlert(Stage stage, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}