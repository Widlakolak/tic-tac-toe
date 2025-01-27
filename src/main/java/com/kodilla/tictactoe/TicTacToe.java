package com.kodilla.tictactoe;

import com.kodilla.tictactoe.game.GameFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameFX gameFX = new GameFX();
        gameFX.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}