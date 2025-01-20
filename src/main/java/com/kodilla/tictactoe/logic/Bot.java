package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.mechanic.Board;

import java.util.List;
import java.util.Random;

public class Bot {

    public int autoMove(Board board) {
        List<Integer> possibleFields = board.getFieldList();

        if (possibleFields.isEmpty()) {
            return 0;
        }

        Random rand = new Random();
        return possibleFields.get(rand.nextInt(possibleFields.size()));
    }
}