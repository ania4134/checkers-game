package com.kodilla.checkers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Game {
    private Image pawnB = new Image("file:src/main/resources/pawnB.png");
    private Image pawnW = new Image("file:src/main/resources/pawnW.png");
    private Image queenB = new Image("file:src/main/resources/queenB.png");
    private Image queenW = new Image("file:src/main/resources/queenW.png");
    private Board board;
    private GridPane grid;
    private int oldX=-1;
    private int oldY=-1;

    public Game(Board board, GridPane grid) {

        this.board = board;
        this.grid = grid;
    }

    public void displayOnBoard() {
        grid.getChildren().clear();
        for(int col=0; col<8; col++) {
            for(int row=0; row<8; row++) {
                Figure figure = board.getFigure(col, row);
                Image image=null;
                if(figure instanceof Pawn) {
                    if(figure.getColor()==FigureColor.WHITE) {
                        image=pawnW;
                    }else {
                        image=pawnB;
                    }
                } else if(figure instanceof Queen) {
                    if(figure.getColor()==FigureColor.WHITE) {
                        image=queenW;
                    } else {
                        image=queenB;
                    }
                }
                if(image!=null) {
                    ImageView imageView = new ImageView(image);
                    grid.add(imageView, col, row);
                }
            }
        }
    }

    public void doClick(int x, int y) {
        if(oldX == -1) {
            oldX=x;
            oldY=y;
        } else {
            if(board.move(oldX, oldY, x, y) || board.moveWithBeat(oldX, oldY, x, y)){
                board.switchPlayer();
            }
            oldX = -1;
            oldY = -1;
            displayOnBoard();
        }
    }
}
