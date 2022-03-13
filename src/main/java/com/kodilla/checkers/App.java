package com.kodilla.checkers;

public class App {
    public static void main(String[] args) {
        Board board = new Board()                                                                                   ;
        board.initBoard();
        System.out.println(board);
        boolean result = board.move(2,2,3,3);
        System.out.println(board);
        System.out.println(result);
    }
}
