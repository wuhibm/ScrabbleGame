package com.example.scrabble1;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;

/**
 * This class manages the board
 * It scans and updates the board
 * The main purpose is to abstract away the details of the board from the GameScreen class
 *
 * @version 1.0
 * @since 2023-05-11
 * @author wuhibmezemir
 */
public class BoardManager {

    //  TODO:Edge words

    /**
     * This method scans the board and returns a 2D array of the board
     * @param allTiles is a LinkedList of all the tiles on the board and rack
     * @return a 2D array of the board with chars representing the letters on the board
     */
    public char[][] scanBoard(LinkedList <Tile> allTiles)
    {
        char[][] board = new char[15][15];

        for (Tile tile : allTiles) {
            char letter = tile.getLetter();
            Node node = tile.draw();
            int xPos = (int) node.getLayoutX() / 50;
            int yPos = (int) node.getLayoutY() / 50;
            if(yPos < 15)
            {
                board[yPos][xPos] = letter;
            }
        }
        return board;
    }

    /**
     * This method removes repeats between 2 arrays
     * Both must be char arrays
     * This method will remove any char that is the same in both arrays
     * This removes redundancy when updating the board after the Computer's turn
     * @param arr1 the first array which will represent the board after the Computer's turn
     * @param arr2 the second array which will represent the board after the Human's turn
     */
    public void removeRepeats(char[][] arr1, char[][]arr2)
    {
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                if(arr1[i][j] == arr2[i][j])
                {
                    arr1[i][j] = '\0';
                }
            }
        }
    }

    /**
     * This method updates the board
     * @param board the board after the Computer's turn represented by a 2D char array
     * @param allTiles all the tiles on the board and rack
     * @param tileSize the size of the tiles
     * @param pane the pane that the tiles are being added to
     */
    public void updateBoard(char[][] board, LinkedList <Tile> allTiles, int tileSize, AnchorPane pane)
    {
        removeRepeats(board, scanBoard(allTiles));
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                if(board[i][j] != '\0')
                {
                    Tile tile = new Tile(j*50, i*50, tileSize, board[i][j]);
                    pane.getChildren().add(tile.draw());
                    allTiles.add(tile);
                }
            }
        }
    }

    //Helper method for copying an array
    public char[][] copyBoard(char[][] arr1)
    {
        char[][] arr2 = new char[15][15];
        for(int i = 0; i < 15; i++)
        {
            System.arraycopy(arr1[i], 0, arr2[i], 0, 15);
        }
        return arr2;
    }
}
