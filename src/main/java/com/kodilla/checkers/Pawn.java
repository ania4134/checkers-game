package com.kodilla.checkers;

public class Pawn implements Figure {
    private FigureColor color;

    public Pawn(FigureColor color) {
        this.color = color;
    }

    @Override
    public FigureColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        String s = (color == FigureColor.WHITE) ? "w" : "b";
        s += "P";
        return s;
    }
}
