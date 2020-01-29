package com.company;

public class Main {

    public static void main(String[] args) {
        var startChessboard = new Chessboard(100);
        var startNode = new Node<>(startChessboard);
        FirstBestAlgorithm<Chessboard> algorithm = new FirstBestAlgorithm<>(startNode, 100);
        var result = algorithm.getResult();
        System.out.println(result.evaluate());
    }
}
