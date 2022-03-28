package com.kodilla.checkers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Game {
    public static final int NOTHING_SELECTED = -1;
    private Image pawnB = new Image("file:src/main/resources/pawnB.png");
    private Image pawnW = new Image("file:src/main/resources/pawnW.png");
    private Image queenB = new Image("file:src/main/resources/queenB.png");
    private Image queenW = new Image("file:src/main/resources/queenW.png");
    private Board board;
    private GridPane grid;
    private int oldX = NOTHING_SELECTED;
    private int oldY = NOTHING_SELECTED;

    public Game(Board board, GridPane grid) {

        this.board = board;
        this.grid = grid;
    }

    public void displayOnBoard() {
        grid.getChildren().clear();
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Figure figure = board.getFigure(col, row);
                Image image = null;
                if (figure instanceof Pawn) {
                    if (figure.getColor() == FigureColor.WHITE) {
                        image = pawnW;
                    } else {
                        image = pawnB;
                    }
                } else if (figure instanceof Queen) {
                    if (figure.getColor() == FigureColor.WHITE) {
                        image = queenW;
                    } else {
                        image = queenB;
                    }
                }
                if (image != null) {
                    ImageView imageView = new ImageView(image);
                    if (col == oldX && row == oldY) {
                        grid.add(new Rectangle(75, 75, Paint.valueOf(Color.RED.toString())), col, row);
                    }else{
                        grid.add(imageView, col, row);
                    }
                }
            }
        }
    }

    public void doClick(int x, int y) {
        if (oldX == NOTHING_SELECTED) {
            oldX = x;
            oldY = y;
            displayOnBoard();
        } else {
            if (board.moveWithBeat(oldX, oldY, x, y)) {
                if(!board.isHitAvailableForPawnFrom(x, y)) {
                    board.switchPlayer();
                }
            } else if (board.moveQueenWithBeat(oldX, oldY, x, y)) {
                if(!board.isHitAvailableforQueenFrom(x, y)) {
                    board.switchPlayer();
                }
            } else if (board.move(oldX, oldY, x, y) || board.moveQueen(oldX, oldY, x, y)) {
                board.switchPlayer();
            }
            oldX = NOTHING_SELECTED;
            oldY = NOTHING_SELECTED;
            displayOnBoard();
            board.checkWinner();
        }
    }
}
