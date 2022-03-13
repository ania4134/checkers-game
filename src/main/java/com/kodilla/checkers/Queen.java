package com.kodilla.checkers;

public class Queen implements Figure {
    private FigureColor color;

    public Queen(FigureColor color) {
        this.color = color;
    }

    @Override
    public FigureColor getCOlor() {
        return color;
    }

    @Override
    public String toString() {
        String s = (color == FigureColor.WHITE) ? "w" : "b";
        s += "Q";
        return s;
    }
}
