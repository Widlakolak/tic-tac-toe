package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.mechanic.Board;

public class DrawChecker {

    public boolean draw(Board board) {

        return board.getMoveCount() == board.getBoardSize();
    }
}
