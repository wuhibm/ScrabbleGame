package com.example.scrabble1;

import javafx.scene.layout.AnchorPane;

/**
 * This class is the base for a board
 * It has all the variables a board needs
 * The code to make the board and the draggable tiles is not my own
 * I have adapted it from this https://www.youtube.com/watch?v=6PWK68RRoeI
 * I use that code in Board, BoardBase, Draggable, and Tile
 *
 * @since 16-04-2023
 * @version 1.4
 * @author wuhibmezemir
 */
public abstract class BoardBase {

    private double width;
    private double height;
    private int tilesAcross;
    private int tileAmount;
    private int gridSize;
    private int tilesDown;
    private AnchorPane anchorPane;

    public BoardBase(double width, double height, int gridSize, AnchorPane anchorPane) {
        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
        this.anchorPane = anchorPane;

        tilesAcross = (int) (width / gridSize);
        tileAmount = (int) ((width /gridSize) * (height /gridSize));
        tilesDown = tileAmount/tilesAcross;
    }

    public double getPlaneWidth() {
        return width;
    }

    public double getPlaneHeight() {
        return height;
    }

    public int getTilesAcross() {
        return tilesAcross;
    }

    public int getTileAmount() {
        return tileAmount;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getTilesDown() {
        return tilesDown;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
