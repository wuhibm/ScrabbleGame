package com.example.scrabble1;


import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Many of the building blocks of this code came from the video, see: BoardBase
 * It has been heavily modified to fit the purposes of my program
 * It represents a tile on the board, it has a letter, a rectangle, a number for points, and a stackpane to hold them
 *
 * @since 16-04-2023
 * @version 1.5
 * @author wuhibmezemir
 */
public class Tile {
    private Rectangle rectangle;
    private final int startPositionX;
    private final int startPositionY;
    private final int size;
    private final char letter;
    private StackPane stackPane;
    private PointManager pointManager = new PointManager();
    private boolean draggable;

    //size will be 50
    public Tile(int startPositionX, int startPositionY, int size, char letter) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.size = size;
        this.letter = letter;
        rectangle = new Rectangle(0, 0, size, size);
        rectangle.setFill(Color.SANDYBROWN);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1.5);
        stackPane = new StackPane();
        Text text = new Text(""+ letter);
        double fontSize = size*(2.0/5);
        text.setFont(new Font(fontSize));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.BLACK);
        int points = pointManager.getPoints(letter);
        Text pointsText = new Text(""+ '\n'+ '\n'+ '\n' +points);
        pointsText.setFont(new Font(fontSize/2));
        pointsText.setLayoutX(startPositionX + rectangle.getWidth() - 10);
        pointsText.setLayoutY(startPositionY + rectangle.getHeight() - 10);
        stackPane.setLayoutX(startPositionX);
        stackPane.setLayoutY(startPositionY);
        stackPane.getChildren().addAll(rectangle, text, pointsText);
    }

    public Node draw()
    {
        return stackPane;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

    public char getLetter() {
        return letter;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }
    public boolean isDraggable() {
        return draggable;
    }
}

