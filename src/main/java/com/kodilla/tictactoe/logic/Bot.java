package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.mechanic.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot {

    private final int maxDepth;

    public Bot(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getMove(Board board, char computerChar, char playerChar) {

        if (maxDepth == 1) {
            return autoMove(board);
        }
        return findBestMove(board, computerChar, playerChar);
    }

    public int autoMove(Board board) {
        List<Integer> possibleFields = board.getFieldList();

        if (possibleFields.isEmpty()) {
            return 0;
        }

        Random rand = new Random();
        return possibleFields.get(rand.nextInt(possibleFields.size()));
    }

    public int findBestMove(Board board, char computerChar, char playerChar) {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;

        List<Integer> possibleFields = new ArrayList<>(board.getFieldList());
        System.out.println("Possible moves: " + possibleFields);

        for (int move : possibleFields) {
            board.makeMove(move, computerChar);
            boolean isMaximizing = (board.getMoveCount() % 2 == 0);
            int moveVal = minimax(board, 0, isMaximizing, computerChar, playerChar);
            board.undoMove(move);

            if (moveVal > bestVal) {
                bestMove = move;
                bestVal = moveVal;
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, boolean isMaximizing, char computerChar, char playerChar) {
        // Sprawdź, czy gra się zakończyła
        if (new WinChecker().win(board)) {
            return isMaximizing ? -10 + depth : 10 - depth; // Kara za głębsze zwycięstwo
        }
        if (new DrawChecker().draw(board)) {
            return 0; // Remis
        }

        int bestVal;
        if (isMaximizing) {
            bestVal = Integer.MIN_VALUE;
            for (int move : new ArrayList<>(board.getFieldList())) { // Kopiuj listę, aby uniknąć modyfikacji podczas iteracji
                board.makeMove(move, computerChar); // Symulacja ruchu
                bestVal = Math.max(bestVal, minimax(board, depth + 1, false, computerChar, playerChar));
                board.undoMove(move); // Cofnij ruch
            }
        } else {
            bestVal = Integer.MAX_VALUE;
            for (int move : new ArrayList<>(board.getFieldList())) {
                board.makeMove(move, playerChar); // Symulacja ruchu
                bestVal = Math.min(bestVal, minimax(board, depth + 1, true, computerChar, playerChar));
                board.undoMove(move); // Cofnij ruch
            }
        }

        return bestVal;
    }


}