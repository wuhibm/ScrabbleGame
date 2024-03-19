package com.example.scrabble1;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

/**
 * This class manages the tiles on the board and rack
 * It adds tiles to the rack, resets the tiles on the rack, and refills the tiles on the rack
 * The main purpose is to abstract away the details of the tiles from the GameScreen class
 *
 * @version 1.0
 * @since 2023-05-11
 * @author wuhibmezemir
 */
public class TileManager {


    /**
     * Adds tiles to the rack
     * @param currTiles The current tiles that have not yet been played and need to be added to the rack
     * @param pane The pane that the tiles are being added to
     * @param tileSize The size of the tiles
     * @param allTiles The list of all tiles on the board and rack
     * @param draggable The draggable object which makes these tiles draggable
     */
    public void addTiles(LinkedList <Tile> currTiles, AnchorPane pane, int tileSize, LinkedList <Tile> allTiles, Draggable draggable, Bag bag)
    {
        //removes any old tiles that aren't being played
        for (Tile currTile : currTiles) {
            pane.getChildren().remove(currTile.draw());
            try {
                allTiles.remove(currTile);
            } catch (Exception ignored) {

            }
        }
        currTiles.clear();
        for(int i = 0; i < 7; i++)
        {
            if(!bag.isEmpty())
            {
                char letter = bag.getTile();
                Tile tile = new Tile(200 +(i*50), 750, tileSize, letter);
                currTiles.add(tile);
                allTiles.add(tile);
                pane.getChildren().add(tile.draw());
                draggable.makeDraggable(tile);
            }
            else
            {
                break;
            }
        }
    }

    /**
     * Resets the tiles to their original positions
     * From the board to the rack
     * Only works for tiles that haven't already been played (i.e. are still draggable)
     * @param currTiles the current tiles that have not yet been played
     */
    public void resetTiles(LinkedList <Tile> currTiles)
    {
        for (Tile currTile : currTiles) {
            Node node = currTile.draw();
            if (currTile.isDraggable() && node.getLayoutY() < 750) {
                node.setLayoutX(currTile.getStartPositionX());
                node.setLayoutY(currTile.getStartPositionY());
            }
        }
    }

    /**
     * Refills the tiles on the rack
     * Upon playing a word, the tiles that were played are removed from the rack
     * This method refills the rack with new tiles where the old ones were
     * @param currTiles The current 7 tiles in play
     * @param pane the pane that the tiles are being added to
     * @param tileSize the size of the tiles
     * @param allTiles the list of all tiles on the board and rack
     * @param draggable the draggable object which makes these tiles draggable
     */
    public void refillTiles(LinkedList <Tile> currTiles, AnchorPane pane, int tileSize, LinkedList <Tile> allTiles, Draggable draggable, Bag bag)
    {
        while (currTiles.size() < 7)
        {
            Stack<Integer> emptySpots = new Stack<>();
            for(int i = 0 ; i < 7; i++)
            {
                emptySpots.push(200+(i*50));
            }
            for (Tile currTile : currTiles) {
                Node node = currTile.draw();
                emptySpots.remove((Integer) (int) node.getLayoutX());
            }
            if(!bag.isEmpty())
            {
                char letter = bag.getTile();
                Tile tile = new Tile(emptySpots.pop(), 750, tileSize, letter);
                currTiles.add(tile);
                allTiles.add(tile);
                pane.getChildren().add(tile.draw());
                draggable.makeDraggable(tile);
            }
            else
            {
                break;
            }
        }
    }

}
