package gameView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameView extends BorderPane {

    private GridPane board;

    public GameView() {
        intialiseNodes();
        layoutNodes();
    }

    private void intialiseNodes() {
        board = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Rectangle rect = new Rectangle();
                rect.setHeight(100);
                rect.setWidth(100);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                board.add(rect,i,j);
            }
        }
    }

    private void layoutNodes() {
        board.setPadding(new Insets(10));
        this.setCenter(board);
    }
}
