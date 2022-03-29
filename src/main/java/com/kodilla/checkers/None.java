package com.kodilla.checkers;

public class None implements Figure {

    @Override
    public FigureColor getColor() {
        return null;
    }

    @Override
    public String toString() {
        return "  ";
    }
}