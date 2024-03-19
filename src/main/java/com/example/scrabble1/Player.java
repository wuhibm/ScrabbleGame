package com.example.scrabble1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Abstract class for the player, inherited by HumanPlayer and ComputerPlayer
 * Human player is not used in the final version of the game, it ended up being redundant
 * This handles many of a player's functions, mostly relating to the board
 *
 * @version 1.7
 * @since 2023-04-07
 * @author wuhibmezemir
 */
public abstract class Player {

    protected char[][] board = new char[15][15];
    private boolean[][] verfier = new boolean[15][15];
    protected ArrayList<Character> letters = new ArrayList<>(7);
    protected LinkedList<String> detectedWords = new LinkedList<>();

    protected int points;
    protected final int[][] pointMultiplier = new int[15][15];


    /**
     * This method refills the player's letters
     * This one was used for tested
     * the bag version is used in the game
     */
    void refillCharacters()
    {

        if(letters.size() >= 7)
        {
            System.err.println("Cannot call refillCharacters(), already have 7 characters");
        }
        else
        {
            while(letters.size()<= 7)
            {
                Random gen = new Random();
                char letter = (char) (gen.nextInt(26) + 'A');
                letters.add(letter);
            }
        }
    }

    //This method does the same but from the bag
    void refillCharacters(Bag bag)
    {
        if(letters.size() >= 7)
        {
            System.err.println("Cannot call refillCharacters(), already have 7 characters");
        }
        else
        {
            while(letters.size()< 7)
            {
                if(bag.getSize() > 0)
                {
                    letters.add(bag.getTile());
                }
                else
                {
                    break;
                }
            }
        }
    }

    /**
     * This method scans the board and makes sure that all the words are valid
     * @return a boolean value, true if all the words are valid, false if not
     * @throws FileNotFoundException
     */
    boolean verifyWord() throws FileNotFoundException {
        detectedWords.clear();
        Dictionary dict = new Dictionary();
        dict.populate();
        findWords();
        boolean valid = true;
        LinkedList <String> validWords = new LinkedList<>();

        //for loop for the length of detectedWords
        for (String detectedWord : detectedWords) {

            if (dict.check(detectedWord)) {
                //System.err.println("Invalid word detected: " + detectedWord + " verifyBoard()");
                validWords.add(detectedWord);

            } else {
                valid = false;
            }
        }
        detectedWords = validWords;
        return valid;
    }

    /**
     * This method finds all potential words on the board
     */
    void findWords() {
        if(board == null)
        {
            System.err.println("Cannot verify empty board: verifyBoard()");
            return;
        }
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                verfier[i][j] = false;
            }
        }

        //a nested for loop where both i and j are less than 15 and i and j are incremented by 1
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                if(board[i][j] != '\0' && !verfier[i][j])
                {

                    //if the string isn't empty and isn't already in the arraylist
                    if(!findWordHorizontal(i,j).isEmpty() && !detectedWords.contains(findWordHorizontal(i,j)))
                    {
                        detectedWords.add(findWordHorizontal(i,j));
                    }
                    if(!findWordVertical(i,j).isEmpty()&& !detectedWords.contains(findWordVertical(i,j)))
                    {
                        detectedWords.add(findWordVertical(i,j));
                    }
                }
            }
        }


    }

    abstract void play(Bag bag) throws FileNotFoundException;

    /**
     * This is a helper method for findWords()
     * it searched for words vertically
     * @param row the row of the board to start searching from
     * @param col the column of the board to start searching from
     * @return a string of the word found
     */
    String findWordVertical(int row, int col)
    {
        if(verfier[row][col])
        {
            return "";
        }
        if(board[row][col] == '\0')
        {
            System.err.println("Cannot find word, invalid coordinates: findWord()");
            return "";
        }
        while (row != 0 &&board[row-1][col] != '\0')
        {
            row--;
        }
        StringBuilder fnl = new StringBuilder();
        if(row != 14 && board[row+1][col] != '\0')
        {
            fnl.append(board[row][col]);
            int rowIncrement = row+1;
            //fixed out of bounds error post submission
            while(rowIncrement!= 15&&board[rowIncrement][col] != '\0' && col!= 0 && col!= 14)
            {
                fnl.append(board[rowIncrement][col]);
                if(board[rowIncrement][col+1] == '\0' && board[rowIncrement][col-1] == '\0')
                {
                    verfier[rowIncrement][col] = true;
                }
                rowIncrement++;

            }
        }

        return fnl.toString();
    }
    //same thing but horizontally
    String findWordHorizontal(int row, int col)
    {
        if(verfier[row][col])
        {
            return "";
        }
        if(board[row][col] == '\0')
        {
            System.err.println("Cannot find word, invalid coordinates: findWord()");
            return "";
        }
        while (col!= 0 && board[row][col-1] != '\0')
        {
            col--;
        }
        StringBuilder fnl = new StringBuilder();
        if(col != 14 && board[row][col+1] != '\0')
        {
            fnl.append(board[row][col]);
            int colIncrement = col+1;
            //Fixed out of bounds error
            while(colIncrement != 15 && board[row][colIncrement] != '\0' && row!= 0 && row != 14)
            {
                fnl.append(board[row][colIncrement]);
                if(board[row+1][colIncrement] == '\0' && board[row-1][colIncrement] == '\0')
                {
                    verfier[row][colIncrement] = true;
                }
                colIncrement++;

            }
        }
        return fnl.toString();
    }
}
