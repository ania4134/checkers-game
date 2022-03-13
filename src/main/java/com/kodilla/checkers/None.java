package com.kodilla.checkers;

public class None implements Figure {

    @Override
    public FigureColor getCOlor() {
        return null;
    }

    @Override
    public String toString() {
        return "  ";
    }
}
