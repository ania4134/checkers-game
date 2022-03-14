package com.kodilla.checkers;

public class App {
    public static void main(String[] args) {
        Board board = new Board()                                                                                   ;
        board.initBoard();
        Pawn bPawn1 = new Pawn(FigureColor.BLACK);
        System.out.println(board);
        boolean result = board.move(0,6,1,5);
        System.out.println(board);
        System.out.println(result);
    }
}
