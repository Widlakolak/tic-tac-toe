package com.kodilla.tictactoe;

import com.kodilla.tictactoe.game.Game;
import com.kodilla.tictactoe.logic.DrawChecker;
import com.kodilla.tictactoe.logic.WinChecker;
import com.kodilla.tictactoe.mechanic.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


class TicTacToeApplicationTests {

    private Board board;
    private DrawChecker drawChecker;
    private WinChecker winChecker;

    @BeforeEach
    void setUp() {
        board = new Board(3, 3);
        drawChecker = new DrawChecker();
        winChecker = new WinChecker();
    }

    @Test
    void testStringInput() {
        String input = "abc\n3\n";
        InputStream stream = new ByteArrayInputStream(input.getBytes());
        System.setIn(stream);

        OutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Assertions.assertDoesNotThrow(() -> {
            Game game = new Game();
            game.askInput();
        });

        String output = outputStream.toString();
        assertTrue(output.contains("You entered : abc"));

        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test()
    void testOccupiedSpot() {
        board.makeMove(1, 'X');

        assertFalse(board.makeMove(1, 'O'));
    }

    @Test
    void testBadMove() {
        assertFalse(board.makeMove(12, 'X'));
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
