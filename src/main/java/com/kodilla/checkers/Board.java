package com.kodilla.checkers;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.checkers.FigureColor.*;

public class Board {
    private FigureColor whoseMove=WHITE;
    private List<BoardRow> rows = new ArrayList<>();

    public Board() {
        for(int row=0; row<8; row++)
            rows.add(new BoardRow());
    }

    public Figure getFigure(int col, int row) {
        return rows.get(row).getCols().get(col);
    }

    public void setFigure(int col, int row, Figure figure) {
        rows.get(row).getCols().set(col, figure);
    }

    public void initBoard() {
        setFigure(0,0, new Pawn(WHITE));
        setFigure(1,1, new Pawn(WHITE));
        setFigure(2,0, new Pawn(WHITE));
        setFigure(3,1, new Pawn(WHITE));
        setFigure(4,0, new Pawn(WHITE));
        setFigure(5,1, new Pawn(WHITE));
        setFigure(6,0, new Pawn(WHITE));
        setFigure(7,1, new Pawn(WHITE));

        setFigure(0,6, new Pawn(FigureColor.BLACK));
        setFigure(1,7, new Pawn(FigureColor.BLACK));
        setFigure(2,6, new Pawn(FigureColor.BLACK));
        setFigure(3,7, new Pawn(FigureColor.BLACK));
        setFigure(4,6, new Pawn(FigureColor.BLACK));
        setFigure(5,7, new Pawn(FigureColor.BLACK));
        setFigure(6,6, new Pawn(FigureColor.BLACK));
        setFigure(7,7, new Pawn(FigureColor.BLACK));
    }

    @Override
    public String toString() {
        String s = "|--|--|--|--|--|--|--|--|\n";
        for(int row=0; row<8; row++)
            s += rows.get(row).toString();
        s += "|--|--|--|--|--|--|--|--|\n";
        return s;
    }

    public boolean move(int col1, int row1, int col2, int row2) {
        System.out.println(col1 + "," + row1 + "-" + col2 + "," + row2);
        Figure figure = getFigure(col1, row1);
        boolean result = true;
        boolean goingToBeQueen = false;

        result = result && isRightDirection(figure.getColor(), row1, row2);
        result = result && isOneStepper(col1, row1, col2, row2);
        result = result && isRightColor(figure.getColor());
        result = result && isEmptyDirectionField(col2, row2);
        result = result && isInBounds(col2, row2);
        goingToBeQueen = isGoingToBeQueen(figure.getColor(), row2);

        if(result && goingToBeQueen) {
            setFigure(col2, row2, new Queen(whoseMove));
            setFigure(col1, row1, new None());
        }else if(result) {
            setFigure(col2, row2, figure);
            setFigure(col1, row1, new None());
        }
        return  result;
    }

    public boolean moveWithBeat(int col1, int row1, int col2, int row2) {
        System.out.println(col1 + "," + row1 + "-" + col2 + "," + row2);
        Figure figure = getFigure(col1, row1);
        int colEnemy = col1-(col1-col2)/2;
        int rowEnemy = row1-(row1-row2)/2;
        boolean result = true;
        boolean goingToBeQueen = false;

        result = result && isRightDirection(figure.getColor(), row1, row2);
        result = result && isRightColor(figure.getColor());
        result = result && isEmptyDirectionField(col2, row2);
        result = result && isEnemyOnNextField(col1, row1, colEnemy, rowEnemy);
        result = result && isInBounds(col2, row2);
        result = result && isTwoStepper(col1, row1, col2, row2);
        goingToBeQueen = isGoingToBeQueen(figure.getColor(), row2);

        if(result && goingToBeQueen) {
            setFigure(col2, row2, new Queen(whoseMove));
            setFigure(col1, row1, new None());
            setFigure(colEnemy, rowEnemy, new None());
        }else if(result) {
            setFigure(col2, row2, figure);
            setFigure(col1, row1, new None());
            setFigure(colEnemy, rowEnemy, new None());
        }
        return  result;
    }

    private boolean isEnemyOnNextField(int col1, int row1, int colEnemy, int rowEnemy) {
        return getFigure(col1, row1).getColor()==WHITE ? getFigure(colEnemy, rowEnemy).getColor()==BLACK :
                getFigure(colEnemy, rowEnemy).getColor()==WHITE;
    }

    private boolean isTwoStepper(int col1, int row1, int col2, int row2) {
        return Math.abs(col2-col1)==2 && Math.abs(row2-row1)==2;
    }

    private boolean isInBounds(int col2, int row2) {
        return  col2<8 && col2>=0 && row2<8 && row2 >=0;
    }

    private boolean isGoingToBeQueen(FigureColor color, int row2) {
        return (color==WHITE) ? row2==7 : row2==0;
    }

    private boolean isEmptyDirectionField(int col2, int row2) {
        return getFigure(col2, row2).getColor()==null;
    }

    private boolean isRightColor(FigureColor color) {
        return color==whoseMove;
    }

    private boolean isOneStepper(int col1, int row1, int col2, int row2) {
        return Math.abs(col2-col1)==1 && Math.abs(row2-row1)==1;
    }

    private boolean isRightDirection(FigureColor color, int row1, int row2) {
        return (color==WHITE) ? row2>row1 : row1>row2 ;
    }

    public void switchPlayer() {
        whoseMove=(whoseMove==WHITE) ? BLACK : WHITE;
    }
}
