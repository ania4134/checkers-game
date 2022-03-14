package com.kodilla.checkers;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.checkers.FigureColor.BLACK;
import static com.kodilla.checkers.FigureColor.WHITE;

public class Board {
    private List<BoardRow> rows = new ArrayList<>();

    public Board() {
        for(int row=0; row<8; row++)
            rows.add(new BoardRow());
    }

    public Figure getFigure(int row, int col) {
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
        Figure figure = getFigure(col1, row1);
        boolean result = false;

        if(figure.equals(new Pawn(WHITE)) && (row2+col2)%2==0 && (row1+col1)%2==0) {
            if(col1 == 0 && row1 >= 0 && row1<6 && col2 == 1 && row2 == row1+1 && row2 < 7){
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                result = true;
            } else if(col1 == 7 && row1 >= 0 && row1<6 && col2 == 6 && row2 == row1+1 && row2 < 7){
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                result = true;
            } else if( col1 > 0 && col1 < 7 && row1 >= 0 && row1<6 && (col2 == col1-1 || col2==col1+1) && row2 == row1+1 && row2 < 7) {
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                result = true;
            }else if(row1==6 && row2==7) {
                setFigure(col2, row2, new Queen(WHITE));
                setFigure(col1, row1, new None());
                result = true;
            } else {
                System.out.println("Niemożliwy ruch!");
                result = false;
            }
        } else if(figure.equals(new Pawn(BLACK)) && (row2+col2)%2==0 && (row1+col1)%2==0) {
            if(col1 == 0 && (row1==2 || row1==4 || row1==6) && col2==1 && row2==row1-1){
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                result = true;
            } else if(col1 == 7 && (row1==3 || row1==5 ||row1==7) && col2 == 6 && row2 == row1-1){
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                result = true;
            } else if( col1 > 0 && col1 < 7 && row1>1 && row1<8 && (col2 == col1-1 || col2==col1+1) && row2 == row1-1 && row2 > 0) {
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                result = true;
            }else if(row1==1 && row2==0){
                setFigure(col2, row2, new Queen(BLACK));
                setFigure(col1, row1, new None());
                result = true;
            } else {
                System.out.println("Niemożliwy ruch!");
                result = false;
            }
        } else if((figure.equals(new Queen(WHITE)) || figure.equals(new Queen(BLACK))) && (row2+col2)%2==0 && (row1+col1)%2==0) {
            if( col1 >= 0 && col1 < 8 && row1 >= 0 && row1<8 && col2 >=0 && col2 <8) {
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                result = true;
            } else {
                System.out.println("Niemożliwy ruch!");
                result = false;
            }
        } else {
            System.out.println("Niemożliwy ruch!");
            result = false;
        }
        return result;
    }

    public boolean moveWithBeat(int col1, int row1, int col2, int row2, int colEnemy, int rowEnemy) {
        boolean result = true;
        Figure figure = getFigure(col1, row1);
        int whitePawnNumber = 8;
        int blackPawnNumber = 8;

        if(figure.equals(new Pawn(WHITE)) && (row2+col2)%2==0 && (row1+col1)%2==0) {
            if(col1 == 0 && row1 >= 0 && row1<5 && col2 == 2 && row2 == row1+2 && colEnemy == 1 && rowEnemy == row1+1){
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                setFigure(colEnemy, rowEnemy, new None());
                result = true;
                whitePawnNumber--;
            } else if(col1==7 && (row1==1 || row1==3) && col2==5 && row2==row1+2 && colEnemy == 6 && rowEnemy==row1+1){
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                setFigure(colEnemy, rowEnemy, new None());
                result = true;
                whitePawnNumber--;
            } else if( col1 > 0 && col1 < 7 && row1 >= 0 && row1<5 && (col2 == col1-2 || col2==col1+2) && row2==row1+2 &&
                    (colEnemy==col1+1 || colEnemy==col1-1) && rowEnemy==row1+1) {
                setFigure(col2, row2, figure);
                setFigure(col1, row1, new None());
                setFigure(colEnemy, rowEnemy, new None());
                result = true;
                whitePawnNumber--;
            } else if(row1==5 && row2==7 && rowEnemy==6) {
                setFigure(col2, row2, new Queen(WHITE));
                setFigure(col1, row1, new None());
                setFigure(colEnemy, rowEnemy, new None());
                result = true;
            } else {
                System.out.println("Niemożliwy ruch!");
                result = false;
            }
        } else if(figure.equals(new Pawn(BLACK)) && (row2+col2)%2==0 && (row1+col1)%2==0) {
            //dokonczyc
        } else {
            System.out.println("Niemożliwy ruch!");
            result = false;
        }
        return result;
    }
}
