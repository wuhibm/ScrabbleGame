package com.example.scrabble1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class represents a board
 * It has all the variables a board needs
 * It draws the board onto the game screen
 * Also adapted from the video, see BoardBase for more info
 * @since 16-04-2023
 * @version 1.3
 * @author wuhibmezemir
 */
public class Board extends BoardBase{

    private Color color1 = Color.PAPAYAWHIP;
    private Color color2 = Color.PEACHPUFF;

    public Board(double width, double height, int gridSize, AnchorPane anchorPane) {
        super(width, height, gridSize, anchorPane);
    }

    /**
     * This method draws the board onto the game screen
     * It draws straight onto the pane using a loop
     */
    public void draw() {
        //The star in the middle
        Image star = new Image("file:src/main/resources/com/example/scrabble1/star.png", getGridSize(), getGridSize(), true, true);
        ImageView imageView = new ImageView(star);
        //This draws the board
        for(int i = 0; i < getTileAmount(); i++){
            int x = (i % getTilesAcross());
            int y = (i / getTilesAcross());

            Rectangle rectangle = new Rectangle(x * getGridSize(),y * getGridSize(),getGridSize(),getGridSize());

            if((x + y) % 2 == 0){
                rectangle.setFill(color1);
            } else {
                rectangle.setFill(color2);
            }
            getAnchorPane().getChildren().add(rectangle);
        }
        imageView.setX(7 * getGridSize());
        imageView.setY(7 * getGridSize());
        getAnchorPane().getChildren().add(imageView);

        //This is my own code
        int x = 200;
        //This draws the rack
        for(int i = 0; i < 7; i++, x += getGridSize()){
            Rectangle rectangle = new Rectangle(x,750,getGridSize(),getGridSize());
            rectangle.setFill(Color.DARKGRAY);
            rectangle.setStroke(Color.WHITE);
            rectangle.setStrokeWidth(1);
            getAnchorPane().getChildren().add(rectangle);
        }
    }

}
