package com.example.scrabble1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class represents a computer player in the game of Scrabble
 * It is a subclass of Player
 * It has a method to play a word on the board
 *
 * @version 2.6
 * @since 2023-04-07
 * @author wuhibmezemir
 */
public class ComputerPlayer extends Player{


    private boolean finishedLetters = false;
    private final boolean[][] playable = new boolean[15][15];
    //This temporary board is used to check to store the old board when the board is being changed
    //It's used to change the board back if the changes are invalid
    private final char [][] tmpBoard = new char[15][15];

    Dictionary dictionary = new Dictionary(true);

    public ComputerPlayer(char[][] board, ArrayList<Character> letters) throws FileNotFoundException {
        //dictionary.populate();
        int row = board.length;
        int col = board[0].length;
        if(row == 15 && col == 15 && letters.size() <= 7)
        {
            this.board = board;
            this.letters = letters;
            if(letters.size() < 7)
            {
                refillCharacters();
            }
        }
        else if (letters.size() > 7)
        {
            System.err.println("Error constructing ComputerPlayer, there must be 7 or fewer characters");
        }
        else
        {
            System.err.println("Error constructing ComputerPlayer, Board must be 15x15");
        }
    }
    public ComputerPlayer(char[][] board, Bag bag) throws FileNotFoundException {
        int row = board.length;
        int col = board[0].length;
        if(row == 15 && col == 15 && letters.size() <= 7)
        {
            this.board = board;
        }
        else
        {
            System.err.println("Error constructing ComputerPlayer, Board must be 15x15");
        }
        refillCharacters(bag);
    }

    public LinkedList<String> getDetectedWords()
    {
        return detectedWords;
    }


    public boolean getValid() throws FileNotFoundException {
        return verifyWord();
    }
    public boolean getValid(char[][] trgtBoard) throws FileNotFoundException {

        setUpTmpBoard();
        board = trgtBoard;
        if(verifyWord())
        {
            return true;
        }
        else
        {
            resetBoard();
            return false;
        }
    }

    //This sets up the temporary board
    void setUpTmpBoard()
    {
        for(int i = 0; i < 15; i++)
        {
            System.arraycopy(board[i], 0, tmpBoard[i], 0, 15);
        }
    }
    //This resets the board to the temporary board, what it was before the changes
    void resetBoard()
    {
        if(tmpBoard == null)
        {
            return;
        }
        for(int i = 0; i < 15; i++)
        {
            System.arraycopy(tmpBoard[i], 0, board[i], 0, 15);
        }
    }

    /**
     * This method checks where on the board letters can legally be placed
     * This was used for when my program only placed one letter to make sure it was placed correctly
     * Not really used anymore after I implemented the method to place better words
     */
    void checkPlayable()
    {
        resetPlayable();
        //nest for loop with 15 iterations for both i and j
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                if(board[i][j] == '\0' && i == 0 && j == 0 && (board[i][j+1] != '\0' || board[i+1][j] != '\0'))
                {
                    playable[i][j] = true;
                }
                else if (board[i][j] == '\0' && i == 0 && j > 0 && j < 14 &&(board[i][j+1] != '\0' || board[i+1][j] != '\0' || board[i][j-1] != '\0'))
                {
                    playable[i][j] = true;
                }
                else if (board[i][j] == '\0' && i == 0 && j == 14 && (board[i][j-1] != '\0' || board[i+1][j] != '\0'))
                {
                    playable[i][j] = true;
                }
                else if (board[i][j] == '\0' && j == 0 && i > 0 && i < 14 &&(board[i][j+1] != '\0' || board[i+1][j] != '\0' || board[i-1][j] != '\0'))
                {
                    playable[i][j] = true;
                }
                else if (board[i][j] == '\0' && j == 14 && i > 0 && i < 14 &&(board[i][j-1] != '\0' || board[i+1][j] != '\0' || board[i-1][j] != '\0'))
                {
                    playable[i][j] = true;
                }
                else if (board[i][j] == '\0' && i == 14 && j == 0 && (board[i][j+1] != '\0' || board[i-1][j] != '\0'))
                {
                    playable[i][j] = true;
                }
                else if (board[i][j] == '\0' && i == 14 && j > 0 && j < 14 &&(board[i][j+1] != '\0' || board[i][j-1] != '\0' || board[i-1][j] != '\0'))
                {
                    playable[i][j] = true;
                }
                else if (board[i][j] == '\0' && i == 14 && j == 14 && (board[i][j-1] != '\0' || board[i-1][j] != '\0'))
                {
                    playable[i][j] = true;
                }
                else if (board[i][j] == '\0' && i > 0 && i < 14 && j > 0 && j < 14 &&(board[i][j+1] != '\0' || board[i][j-1] != '\0' || board[i+1][j] != '\0' || board[i-1][j] != '\0'))
                {
                    playable[i][j] = true;
                }
            }
        }
    }

    /**
     * This method is responsible for making the computer play a turn
     *
     * @param bag the bag of letters
     * @throws FileNotFoundException needed when checking if the words are valid
     */
    @Override
    public void play(Bag bag) throws FileNotFoundException {
        //This handles if the board is empty
        if(letters.size() <= 0)
        {
            finishedLetters = true;
            return;
        }
        if(isEmpty(board))
        {
            WordConstruction wordConstruction = new WordConstruction();
            LinkedList<Character> currLetters = new LinkedList<>(letters);
            String word = wordConstruction.bestWord(currLetters);
            if(word == null)
            {
                return;
            }
            for(int i = 0; i < word.length(); i++)
            {
                board[7][7+i] = word.charAt(i);
            }
            return;
        }

        setUpTmpBoard();
        checkPlayable();
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                //If the board has a letter in it, it will check if it can be played
                if(board[i][j] != '\0')
                {
                    //This will try to play a word including the letter
                    if(speller(letters, board[i][j], i, j, bag))
                    {
                        //if it can play a word, it will return
                        if(getValid())
                        {
                            return;
                        }
                    }
                    else
                    {
                        //if it can't play a word, it will reset the board
                        resetBoard();
                    }
                }
                //This is the old code which only played one letter at a time
//                if(playable[i][j])
//                {
//
//                    for(int k = 0; k < 7; k++)
//                    {
//                        board[i][j] = letters.get(k);
//                        if(getValid())
//                        {
//                            letters.remove(k);
//                            if(letters.size() < 7)
//                            {
//                                refillCharacters(bag);
//                            }
//                            //validWords(letters);
//                            return;
//                        }
//                        else
//                        {
//                            //restores the board to what it was originally if the new word is invalid
//                            resetBoard();
//                        }
//                    }
//                }
            }
        }
    }

    /**
     * This method is responsible for placing a valid word onto the board in a valid area
     *
     * @param ref The letters available to the computer
     * @param boardLetter The letter on the board which the computer is trying to spell off of
     * @param row the row of the specific letter on the board
     * @param col the column of the specific letter on the board
     * @param bag the bag of letters
     * @return true if the word was able to be played, false otherwise
     * @throws FileNotFoundException
     */
    boolean speller(ArrayList<Character> ref, char boardLetter, int row, int col, Bag bag) throws FileNotFoundException {
        boolean spelled = false;
        LinkedList<Character> words = new LinkedList<>(ref);
        WordConstruction wordConstruction = new WordConstruction();
        String word = wordConstruction.bestWord(words, boardLetter);
        if(word == null)
        {
            System.out.println(letters.toString() + boardLetter);
            return false;
        }
        //Tries to spell something vertically
        if(spellVertically(word, boardLetter, row, col))
        {
            spelled = true;
        }
        //Tries to spell something horizontally
        else if (spellHorizontally(word,boardLetter,row,col))
        {
            spelled = true;
        }
        if(spelled)
        {
            //TODO: make sure it doesn't remove the character from the board
            for(int i = 0; i < word.length(); i++)
            {
                letters.remove((Character) word.charAt(i));
            }
            if(letters.size() < 7)
            {
                refillCharacters(bag);
            }
        }
        return spelled;
    }

    /**
     * This method tries to spell a word onto the board vertically
     * @param word the word the computer is trying to spell
     * @param boardLetter the letter on the board which the computer is trying to spell off of
     * @param row the row of the specific letter on the board
     * @param col the column of the specific letter on the board
     * @return true if the word was able to be played, false otherwise
     * @throws FileNotFoundException
     */
    boolean spellVertically(String word, char boardLetter, int row, int col) throws FileNotFoundException {
        if(!word.contains(String.valueOf(boardLetter)))
        {
            System.err.println("The word does not contain the letter on the board: spellVertically()");
        }
        else
        {
            //The location of the letter relative to the word
            int letterLocation = word.indexOf(boardLetter);
            int rowNum = row - letterLocation;
            if(rowNum < 0)
            {
                return false;
            }
            setUpTmpBoard();
            //When spelling the letter I move backwards through the board to accommodate for where the letter is in the word
            for(int i = 0; i < word.length(); i++)
            {
                //fixed out of bounds error post submission
                if(rowNum!= 14 && (board[rowNum][col] == '\0' || rowNum == row))
                {
                    board[rowNum][col] = word.charAt(i);
                    rowNum++;
                }
                else
                {
                    resetBoard();
                    return false;
                }
            }
            if(!getValid())
            {
                resetBoard();
                return false;
            }
        }
        return true;
    }

    //Same thing but horizontally
    boolean spellHorizontally(String word, char boardLetter, int row, int col) throws FileNotFoundException {
        if(!word.contains(String.valueOf(boardLetter)))
        {
            System.err.println("The word does not contain the letter on the board: spellHorizontally()");
        }
        else
        {
            var letterLocation = word.indexOf(boardLetter);
            var colNum = col - letterLocation;
            if(colNum < 0)
            {
                return false;
            }
            setUpTmpBoard();
            for(int i = 0; i < word.length(); i++)
            {
                //fixed out of bounds error post submission
                if(colNum!= 14 && (board[row][colNum] == '\0' || colNum == col))
                {
                    board[row][colNum] = word.charAt(i);
                    colNum++;
                }
                else
                {
                    resetBoard();
                    return false;
                }
            }
            if(!getValid())
            {
                resetBoard();
                return false;
            }
        }
        return true;
    }

    public char[][] getBoard()
    {
        return board;
    }
    private void resetPlayable()
    {
        for(int i = 0; i < playable.length; i++)
        {
            for(int j = 0; j < playable[0].length; j++)
            {
                playable[i][j] = false;
            }
        }
    }

    /**
     * This method constructs all permutations of the letters available to the computer
     * I only really used this for testing purposes
     * @param ref the letters available to the computer
     * @return a list of all permutations of the letters available to the computer
     */
    public LinkedList<String> constructWord(LinkedList<Character> ref)
    {
        WordConstruction wordConstruction = new WordConstruction();
        return wordConstruction.constructWord(ref);
    }

    /**
     * This method is responsible for checking if computer has run out of words
     * If it has the game must be ended
     * @return a boolean for whether it's finished
     */
    public boolean getFinishedLetters()
    {
        return finishedLetters;
    }

    //Helper method to check if an array is empty
    boolean isEmpty(char[][] arr)
    {
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j< 15; j++)
            {
                if(arr[i][j] != 0)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
