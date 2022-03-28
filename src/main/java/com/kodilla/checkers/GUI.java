package com.kodilla.checkers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.management.monitor.GaugeMonitor;
import java.awt.*;
import javafx.scene.control.Button;

public class GUI extends Application {

    private Image board = new Image("file:src/main/resources/background.png");

    private FlowPane pawns = new FlowPane(Orientation.HORIZONTAL);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(600, 600, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setBackground(background);

        Scene scene = new Scene(grid, 600, 600, Color.BLACK);
//        Button button = new Button();
//        Scene sceneButton = new Scene(button, 200, 75);

        for(int i=0; i<8; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(75));
            grid.getRowConstraints().add(new RowConstraints(75));
        }

        Board board = new Board();
        board.initBoard();
        Game game = new Game(board, grid);
        game.displayOnBoard();

        grid.setOnMouseClicked(e -> {
            int x = (int)e.getX()/75;
            int y = (int)e.getY()/75;
            game.doClick(x, y);
        });
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
