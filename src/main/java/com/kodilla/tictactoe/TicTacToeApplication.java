package com.kodilla.tictactoe;

import com.kodilla.tictactoe.game.Game;

public class TicTacToeApplication {

	public static void main(String[] args) {
		int rows = 3;
		int cols = 3;

		Game game = new Game(rows, cols);
		game.start();
	}
}
