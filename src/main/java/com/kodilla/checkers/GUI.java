package com.kodilla.checkers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import  javafx.scene.image.Image;
import javafx.stage.Stage;
import java.awt.*;

public class GUI extends Application {

    private Image board = new Image("file:src/main/resources/background.png");
    private Image pawnB = new Image("file:src/main/resources/pawnB.png");
    private Image pawnW = new Image("file:src/main/resources/pawnW.png");
    private Image queenB = new Image("file:src/main/resources/queenB.png");
    private Image queenW = new Image("file:src/main/resources/queenW.png");
    private FlowPane pawns = new FlowPane(Orientation.HORIZONTAL);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(800, 800, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setBackground(background);

        Scene scene = new Scene(grid, 800, 800, Color.BLACK);

        ImageView pawnBlack = new ImageView(pawnB);
        ImageView pawnWhite = new ImageView(pawnW);
        ImageView queenBlack = new ImageView(queenB);
        ImageView queenWhite = new ImageView(queenW);
        pawns.getChildren().add(pawnWhite);
        pawns.getChildren().add(pawnBlack);
        pawns.getChildren().add(queenWhite);
        pawns.getChildren().add(queenBlack);

        for(int i=0; i<8; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(100));
            grid.getRowConstraints().add(new RowConstraints(100));
        }

        grid.add(queenBlack, 1, 0,1,1);
        grid.add(pawnWhite, 3, 0,1,1);
        grid.add(pawnBlack, 0, 1,1,1);
        grid.add(queenWhite, 2, 1,1,1);

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
