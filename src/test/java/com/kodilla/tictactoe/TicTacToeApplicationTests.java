package com.kodilla.tictactoe;

import com.kodilla.tictactoe.logic.DrawChecker;
import com.kodilla.tictactoe.logic.WinChecker;
import com.kodilla.tictactoe.mechanic.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TicTacToeApplicationTests {

	private Board board;
	private DrawChecker drawChecker;
	private WinChecker winChecker;

	@BeforeEach
	void setUp() {
		board = new Board();
		drawChecker = new DrawChecker();
		winChecker = new WinChecker();
	}

	@Test()
	void testOccupiedSpot() {
		board.makeMove(1, 'X');

		Exception exception = assertThrows(RuntimeException.class, () -> board.makeMove(1, 'O'));
	}

	@Test
	void testBadMove() {

		Exception exception = assertThrows(RuntimeException.class, () -> board.makeMove(20, 'X'));
	}

	@Test
	void testDraw() {
		board.makeMove(1, 'X');
		board.makeMove(2, 'O');
		board.makeMove(3, 'X');
		board.makeMove(5, 'O');
		board.makeMove(4, 'X');
		board.makeMove(7, 'O');
		board.makeMove(8, 'X');
		board.makeMove(9, 'O');
		board.makeMove(6, 'X');

		assertTrue(drawChecker.draw(board));
	}

	@Test
	void testWinXRow() {
		board.makeMove(1, 'X');
		board.makeMove(2, 'X');
		board.makeMove(3, 'X');

		assertTrue(winChecker.win(board));
	}

	@Test
	void testWinXColumn() {
		board.makeMove(1, 'X');
		board.makeMove(4, 'X');
		board.makeMove(7, 'X');

		assertTrue(winChecker.win(board));
	}

	@Test
	void testWinXDiagonal() {
		board.makeMove(1, 'X');
		board.makeMove(5, 'X');
		board.makeMove(9, 'X');

		assertTrue(winChecker.win(board));
	}

	@Test
	void testWinORow() {
		board.makeMove(1, 'O');
		board.makeMove(2, 'O');
		board.makeMove(3, 'O');

		assertTrue(winChecker.win(board));
	}

	@Test
	void testWinOColumn() {
		board.makeMove(2, 'O');
		board.makeMove(5, 'O');
		board.makeMove(8, 'O');

		assertTrue(winChecker.win(board));
	}

	@Test
	void testWinODiagonal() {
		board.makeMove(3, 'O');
		board.makeMove(5, 'O');
		board.makeMove(7, 'O');

		assertTrue(winChecker.win(board));
	}

}
