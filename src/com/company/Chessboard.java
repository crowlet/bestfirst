package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Chessboard implements EvaluableNodeValue<Chessboard> {

    final private int size;
    final private boolean[][] board;
    final private List<Queen> queens;
    final private static Random generator = new Random();

    public Chessboard(int size) {
        this.size = size;
        board = new boolean[size][size];
        queens = new LinkedList<>();
    }

    public Chessboard(Chessboard chessboard) {
        this.size = chessboard.size;
        this.board = new boolean[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                this.board[i][j] = chessboard.board[i][j];
            }
        }
        this.queens = chessboard.queens.stream().map(Queen::new).collect(Collectors.toList());
    }

    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                stringBuilder.append(board[i][j] ? "  X  " : "  -  ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private Optional<Chessboard> addQueen() {
        var chessboard = new Chessboard(this);
        var freeFields = chessboard.freeFields();
        int randomPlaceIndex = 0;
        if (freeFields == 0) {
            return Optional.empty();
        }
        synchronized (Chessboard.class) {
            randomPlaceIndex = generator.nextInt(freeFields);
        }
        var position = chessboard.getPosition(randomPlaceIndex);
        assert position != null;
        var queen = new Queen(position);
        chessboard.queens.add(queen);
        chessboard.takePlace(queen);
        return Optional.of(chessboard);
    }

    private void takePlace(Queen queen) {
        var x = queen.getxPos();
        var y = queen.getyPos();

        for (int i = x; i < size; i++) {
            board[i][y] = true;
        }

        for (int i = x; i >= 0; i--) {
            board[i][y] = true;
        }

        for (int i = y; i < size; i++) {
            board[x][i] = true;
        }

        for (int i = y; i >= 0; i--) {
            board[x][i] = true;
        }

        for (int i = x, j = y; (i < size) && (j < size); i++, j++) {
            board[i][j] = true;
        }

        for (int i = x, j = y; (i >= 0) && (j >= 0); i--, j--) {
            board[i][j] = true;
        }

    }

    public int freeFields() {
        var space = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!board[i][j]) {
                    space++;
                }
            }
        }
        return space;
    }

    private Position getPosition(int index) {
        int iterator = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!board[i][j]) {
                    if (iterator == index) {
                        return new Position(i, j);
                    }
                    iterator++;
                }
            }
        }
        return null;
    }


    @Override
    public int evaluate() {
        var freeFields = freeFields();
        return (freeFields*2)/size + size*queens.size()*10 ;
    }

    @Override
    public Optional<Chessboard> generateNext() {
        return addQueen();
    }

    @Override
    public String toString() {
        return print();
    }
}
