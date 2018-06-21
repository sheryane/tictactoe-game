package pl.sheryane.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(300, 300);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 100);
                tile.setTranslateY(i * 100);

                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }

        //Horizontal lines
        for(int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }

        //Vertical lines
        for(int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }

        //Diagonal lines
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                break;
            }
        }
    }

    private class Combo {
        private Tile[] tiles;

        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty()) {
                return false;
            }

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    private class Tile extends StackPane {

        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(100, 100);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(48));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            // This code will be executed, when the mouse is clicked on the tile.

            setOnMouseClicked(event -> {
                if (!playable) {
                    return;
                }
                //PRIMARY is the left and SECONDARY is the right mouse button.
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX) {
                        return;
                    }
                    drawX();
                    turnX = false;
                    checkState();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX) {
                        return;
                    }
                    drawO();
                    turnX = true;
                    checkState();
                }
            });
        }

        public String getValue() {
            return text.getText();
        }

        private void drawX() {
            text.setText("X");
        }

        private void drawO() {
            text.setText("O");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
