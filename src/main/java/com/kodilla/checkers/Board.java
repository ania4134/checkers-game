package com.kodilla.checkers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.checkers.FigureColor.*;

public class Board {
    private FigureColor whoseMove = WHITE;
    private List<BoardRow> rows = new ArrayList<>();

    public Board() {
        for (int row = 0; row < 8; row++)
            rows.add(new BoardRow());
    }

    public Figure getFigure(int col, int row) {
        return rows.get(row).getCols().get(col);
    }

    public void setFigure(int col, int row, Figure figure) {
        rows.get(row).getCols().set(col, figure);
    }

    public void initBoard() {
        setFigure(0, 0, new Pawn(WHITE));
        setFigure(1, 1, new Pawn(WHITE));
        setFigure(2, 0, new Pawn(WHITE));
        setFigure(3, 1, new Pawn(WHITE));
        setFigure(4, 0, new Pawn(WHITE));
        setFigure(5, 1, new Pawn(WHITE));
        setFigure(6, 0, new Pawn(WHITE));
        setFigure(7, 1, new Pawn(WHITE));

        setFigure(0, 6, new Pawn(FigureColor.BLACK));
        setFigure(1, 7, new Pawn(FigureColor.BLACK));
        setFigure(2, 6, new Pawn(FigureColor.BLACK));
        setFigure(3, 7, new Pawn(FigureColor.BLACK));
        setFigure(4, 6, new Pawn(FigureColor.BLACK));
        setFigure(5, 7, new Pawn(FigureColor.BLACK));
        setFigure(6, 6, new Pawn(FigureColor.BLACK));
        setFigure(7, 7, new Pawn(FigureColor.BLACK));
    }

    @Override
    public String toString() {
        String s = "|--|--|--|--|--|--|--|--|\n";
        for (int row = 0; row < 8; row++)
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

        if (result && goingToBeQueen) {
            setFigure(col2, row2, new Queen(whoseMove));
            setFigure(col1, row1, new None());
        } else if (result) {
            setFigure(col2, row2, figure);
            setFigure(col1, row1, new None());
        }
        return result;
    }

    public boolean moveWithBeat(int col1, int row1, int col2, int row2) {
        System.out.println(col1 + "," + row1 + "-" + col2 + "," + row2);
        Figure figure = getFigure(col1, row1);
        int colEnemy = col1 - (col1 - col2) / 2;
        int rowEnemy = row1 - (row1 - row2) / 2;
        boolean result = true;
        boolean goingToBeQueen = false;

        result = result && isRightDirection(figure.getColor(), row1, row2);
        result = result && isRightColor(figure.getColor());
        result = result && isEmptyDirectionField(col2, row2);
        result = result && isEnemyOnNextFieldAndEmptyFieldBehindHim(col1, row1, col2, row2);
        result = result && isInBounds(col2, row2);
        result = result && isTwoStepper(col1, row1, col2, row2);
        goingToBeQueen = isGoingToBeQueen(figure.getColor(), row2);

        if (result && goingToBeQueen) {
            setFigure(col2, row2, new Queen(whoseMove));
            setFigure(col1, row1, new None());
            setFigure(colEnemy, rowEnemy, new None());
        } else if (result) {
            setFigure(col2, row2, figure);
            setFigure(col1, row1, new None());
            setFigure(colEnemy, rowEnemy, new None());
        }
        return result;
    }

    public boolean moveQueen(int col1, int row1, int col2, int row2) {
        System.out.println(col1 + "," + row1 + "-" + col2 + "," + row2);
        Figure figure = getFigure(col1, row1);
        boolean result = true;
        boolean isEnemyDiagonally = false;

        result = result && isQueenWithRightColor(col1, row1);
        result = result && isEmptyDirectionField(col2, row2);
        result = result && isInBounds(col2, row2);
        result = result && isDiagonally(col1, row1, col2, row2);
        result = result && (isEmptyFieldsDiagonally(col1, row1, col2, row2) || isOneStepper(col1, row1, col2, row2));

        if (result) {
            setFigure(col2, row2, figure);
            setFigure(col1, row1, new None());
        }
        return result;
    }

    public boolean moveQueenWithBeat(int col1, int row1, int col2, int row2) {
        System.out.println(col1 + "," + row1 + "-" + col2 + "," + row2);
        Figure figure = getFigure(col1, row1);
        boolean result = true;
        boolean isEnemyDiagonally = false;

        result = result && isQueenWithRightColor(col1, row1);
        result = result && isEmptyDirectionField(col2, row2);
        result = result && isInBounds(col2, row2);
        result = result && isDiagonally(col1, row1, col2, row2);
        result = result && noColleagueDiagonally(col1, row1, col2, row2);

        if (result) {
            setFigure(col2, row2, figure);
            queenBeatEnemies(col1, row1, col2, row2);
            setFigure(col1, row1, new None());
        }
        return result;
    }

    private boolean isEmptyFieldsDiagonally(int col1, int row1, int col2, int row2) {
        boolean result = false;
        if ((col2 - col1) > 0) {
            if ((row2 - row1) > 0) {
                for (int i = col1 + 1; i < col2; i++) {
                    for (int j = row1 + 1; j < row2; j++) {
                        result = getFigure(i, j).getColor() == null;
                    }
                }
            } else {
                for (int i = col1 + 1; i < col2; i++) {
                    for (int j = row1 - 1; j > row2; j--) {
                        result = getFigure(i, j).getColor() == null;
                    }
                }
            }
        } else {
            if ((row2 - row1) > 0) {
                for (int i = col1 - 1; i > col2; i--) {
                    for (int j = row1 + 1; j < row2; j++) {
                        result = getFigure(i, j).getColor() == null;
                    }
                }

            } else {
                for (int i = col1 - 1; i > col2; i--) {
                    for (int j = row1 - 1; j > row2; j--) {
                        result = getFigure(i, j).getColor() == null;
                    }
                }
            }
        }
        return result;
    }

    private void queenBeatEnemies(int col1, int row1, int col2, int row2) {
        if ((col2 - col1) > 0) {
            if ((row2 - row1) > 0) {
                for (int i = col1 + 1; i < col2; i++) {
                    for (int j = row1 + 1; j < row2; j++) {
                        setFigure(i, j, new None());
                    }
                }
            } else {
                for (int i = col1 + 1; i < col2; i++) {
                    for (int j = row1 - 1; j > row2; j--) {
                        setFigure(i, j, new None());
                    }
                }
            }
        } else {
            if ((row2 - row1) > 0) {
                for (int i = col1 - 1; i > col2; i--) {
                    for (int j = row1 + 1; j < row2; j++) {
                        setFigure(i, j, new None());
                    }
                }

            } else {
                for (int i = col1 - 1; i > col2; i--) {
                    for (int j = row1 - 1; j > row2; j--) {
                        setFigure(i, j, new None());
                    }
                }
            }
        }
    }

    public boolean noColleagueDiagonally(int col1, int row1, int col2, int row2) {
        boolean result = false;
        if ((col2 - col1) > 0) {
            if ((row2 - row1) > 0) {
                for (int i = col1 + 1; i < col2; i++) {
                    for (int j = row1 + 1; j < row2; j++) {
                        result = isEnemyOrNone(col1, row1, i, j);
                    }
                }
            } else {
                for (int i = col1 + 1; i < col2; i++) {
                    for (int j = row1 - 1; j > row2; j--) {
                        result = isEnemyOrNone(col1, row1, i, j);
                    }
                }
            }
        } else {
            if ((row2 - row1) > 0) {
                for (int i = col1 - 1; i > col2; i--) {
                    for (int j = row1 + 1; j < row2; j++) {
                        result = isEnemyOrNone(col1, row1, i, j);
                    }
                }
            } else {
                for (int i = col1 - 1; i > col2; i--) {
                    for (int j = row1 - 1; j > row2; j--) {
                        result = isEnemyOrNone(col1, row1, i, j);
                    }
                }
            }
        }
        return result;
    }

    private boolean isEnemyOrNone(int col1, int row1, int i, int j) {
        boolean result = false;
        if (getFigure(col1, row1).getColor() == WHITE && ((getFigure(i, j).getColor() == BLACK || getFigure(i, j).getColor() == null)))
            result = true;
        else if (getFigure(col1, row1).getColor() == BLACK && ((getFigure(i, j).getColor() == WHITE || getFigure(i, j).getColor() == null)))
            result = true;
        else result = false;
        return result;
    }

    private boolean isQueenWithRightColor(int col1, int row1) {
        return getFigure(col1, row1) instanceof Queen;
    }

    private boolean isDiagonally(int col1, int row1, int col2, int row2) {
        return Math.abs(col2 - col1) == Math.abs(row2 - row1);
    }

    public boolean isEnemyOnNextFieldAndEmptyFieldBehindHim(int col1, int row1, int col2, int row2) {
        int colEnemy = col1 - (col1 - col2) / 2;
        int rowEnemy = row1 - (row1 - row2) / 2;

        return getFigure(col1, row1).getColor() == WHITE ? getFigure(colEnemy, rowEnemy).getColor() == BLACK && getFigure(col2, row2).getColor() == null :
                getFigure(colEnemy, rowEnemy).getColor() == WHITE && getFigure(col2, row2).getColor() == null;
    }

    private boolean isTwoStepper(int col1, int row1, int col2, int row2) {
        return Math.abs(col2 - col1) == 2 && Math.abs(row2 - row1) == 2;
    }

    private boolean isInBounds(int col2, int row2) {
        return col2 < 8 && col2 >= 0 && row2 < 8 && row2 >= 0;
    }

    private boolean isGoingToBeQueen(FigureColor color, int row2) {
        return (color == WHITE) ? row2 == 7 : row2 == 0;
    }

    private boolean isEmptyDirectionField(int col2, int row2) {
        return getFigure(col2, row2).getColor() == null;
    }

    private boolean isRightColor(FigureColor color) {
        return color == whoseMove;
    }

    private boolean isOneStepper(int col1, int row1, int col2, int row2) {
        return Math.abs(col2 - col1) == 1 && Math.abs(row2 - row1) == 1;
    }

    private boolean isRightDirection(FigureColor color, int row1, int row2) {
        return (color == WHITE) ? row2 > row1 : row1 > row2;
    }

    public void switchPlayer() {
        whoseMove = (whoseMove == WHITE) ? BLACK : WHITE;
    }

    public boolean isHitAvailableForPawnFrom(int col, int row) {
        boolean result = false;

        if (getFigure(col, row).getColor() == WHITE) {
            if ((getFigure(col - 1, row + 1).getColor() == BLACK && getFigure(col - 2, row + 2).getColor() == null && col - 2 >= 0 && col + 2 < 8)
                    || (getFigure(col + 1, row + 1).getColor() == BLACK && getFigure(col + 2, row + 2).getColor() == null && col + 2 < 8))
                result = true;

        } else if (getFigure(col, row).getColor() == BLACK)
            if ((getFigure(col - 1, row - 1).getColor() == WHITE && getFigure(col - 2, row - 2).getColor() == null && col - 2 >= 0)
                    || (getFigure(col + 1, row - 1).getColor() == WHITE && getFigure(col + 2, row - 2).getColor() == null && col - 2 >= 0 && col + 2 < 8))
                result = true;
        return result;
    }

    public boolean isHitAvailableforQueenFrom(int col1, int row1) {
        boolean result = false;
        boolean result2 = false;

        for (int i = col1 + 1; i < 7; i++) {
            for (int j = row1 + 1; j < 7; j++) {
                result = isEnemy(col1, row1, i, j);
                result2 = result && getFigure(i+1, j+1).getColor()==null;
            }
        }
        if (result == false) {
            for (int i = col1 + 1; i < 7; i++) {
                for (int j = row1 - 1; j > 0; j--) {
                    result = isEnemy(col1, row1, i, j);
                    result2 = result && getFigure(i+1, j-1).getColor()==null;
                }
            }
        } else if(result == false) {
            for (int i = col1 - 1; i > 0; i--) {
                for (int j = row1 + 1; j < 7; j++) {
                    result = isEnemy(col1, row1, i, j);
                    result2 = result && getFigure(i-1, j+1).getColor()==null;
                }
            }
        }else if(result == false) {
            for (int i = col1 - 1; i > 0; i--) {
                for (int j = row1 - 1; j > 0; j--) {
                    result = isEnemy(col1, row1, i, j);
                    result2 = result && getFigure(i-1, j-1).getColor()==null;
                }
            }
        }
        return result;
    }

    private boolean isEnemy(int col1, int row1, int i, int j) {
        boolean result = false;
        if (getFigure(col1, row1).getColor() == WHITE && getFigure(i, j).getColor() == BLACK) {
            result = true;
        } else if (getFigure(col1, row1).getColor() == BLACK && getFigure(i, j).getColor() == WHITE)
            result = true;

        return result;
    }

    public void checkWinner() {
        int whitePawnsNumber = 0;
        int blackPawnsNumber = 0;
        boolean whiteWon = false;
        boolean blackWon = false;

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (getFigure(col, row).getColor() == WHITE) {
                    whitePawnsNumber++;
                } else if (getFigure(col, row).getColor() == BLACK) {
                    blackPawnsNumber++;
                }
            }
        }
        if (whitePawnsNumber==0) {
            showGameOver(BLACK);
        } else if (blackPawnsNumber==0) {
            showGameOver(WHITE);
        }
    }

    private void showGameOver(FigureColor whoseWon) {
        Stage primaryStage = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(gridPane, 250, 250);
        primaryStage.setScene(scene);
        String text = null;

        if(whoseWon==WHITE) {
            text = "Koniec gry \nWygraly biale!";
        } else {
            text = "Koniec gry \nWygraly czarne!";
        }

        Text sceneTitle = new Text(text);
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        Button btn = new Button("Zamknij");
        btn.setOnAction((e) -> {
            primaryStage.close();
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(btn);
        gridPane.add(hbBtn, 1, 4);

        primaryStage.setTitle("Game over");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
