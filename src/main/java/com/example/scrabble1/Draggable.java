package com.example.scrabble1;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * This class makes tiles draggable
 * Much of this is also adapted from the video, see: BoardBase
 *
 * @since 16-04-2023
 * @version 1.6
 * @author wuhibmezemir
 */
public class Draggable extends BoardBase{

    private double mouseX;
    private double mouseY;
    public Draggable(double width, double height, int gridSize, AnchorPane anchorPane) {
        super(width, height, gridSize, anchorPane);
    }

    /**
     * This method is mostly from the video, see: BoardBase
     * This method makes the tiles draggable
     * @param tile the tile to be made draggable
     */
    public void makeDraggable(Tile tile){
        tile.setDraggable(true);
        Node node = tile.draw();
        node.setOnMouseDragged(mouseEvent -> {
            mouseX = mouseEvent.getSceneX();
            mouseY = mouseEvent.getSceneY();
            if(mouseY >= 750)
            {
                mouseY = 700;
            }
            int x = (int) ((mouseX/getGridSize()) % getTilesAcross()) * getGridSize();
            int y = (int) ((mouseY/getGridSize()) % getTilesDown()) * getGridSize();

            node.setLayoutX(x);
            node.setLayoutY(y);
        });
        //This is my own code
        //This makes it so tiles can be replaced to the rack individually by double-clicking on them
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        node.setLayoutX(tile.getStartPositionX());
                        node.setLayoutY(tile.getStartPositionY());
                    }
                }
            }
        });
    }

    /**
     * This method is my own
     * It is used for making tiles no longer Draggable
     * @param tile the tile to be made static
     * @param x X position of the tile
     * @param y Y position of the tile
     */
    public void makeStatic(Tile tile, double x, double y)
    {
        tile.setDraggable(false);
        Node node = tile.draw();
        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(x);
            node.setLayoutY(y);
        });
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setLayoutX(x);
                node.setLayoutY(y);
            }
        });
    }
}
