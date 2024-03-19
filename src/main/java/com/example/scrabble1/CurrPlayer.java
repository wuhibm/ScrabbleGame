package com.example.scrabble1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class is responsible for keeping track of the current player
 * It makes sure both players play when the play button is pressed
 *
 * @version 2.1
 * @since 2023-04-20
 * @author wuhibmezemir
 */
public class CurrPlayer {
    private ComputerPlayer computerPlayer;
    private HumanPlayer humanPlayer;
    private GameScreen gameScreen;
    private boolean isHumanTurn;
    private char[][] newBoard;
    private int[][] addedWords;
    private ArrayList <Character> letters;
    private LinkedList <String> humanWords;
    private LinkedList<String> computerWords;


    public CurrPlayer(char[][] board, Bag bag) throws FileNotFoundException {
        letters = new ArrayList<>();
        computerPlayer = new ComputerPlayer(board, bag);
        humanPlayer = new HumanPlayer();
        isHumanTurn = true;
        humanWords = new LinkedList<>();
        computerWords = new LinkedList<>();
    }
    public char[][] getPlayerBoard()
    {
        return computerPlayer.getBoard();
    }

    /**
     * This method is responsible for making both players play when the play button is pressed
     *
     * @param currBoard the current state of the board when the user presses play
     * @return an int depending on the success
     * 0 if successful
     * 1 if the player doesn't start in the middle
     * 2 if the word the player makes is invalid
     * 3 if the new word doesn't touch existing tiles
     * 4 if the player doesn't add any new tiles
     * 5 if the computer is out of letters
     * @throws FileNotFoundException needed when checking if the words are valid
     */
    public int play(char[][] currBoard, Bag bag) throws FileNotFoundException {

        char[][] prevBoard = computerPlayer.getBoard();
        computerPlayer.getValid();

        LinkedList<String> prevWords = copyList(computerPlayer.getDetectedWords());
        //if first turn
        if(isEmpty(prevBoard))
        {
            //makes sure they start in the middle
            if(currBoard[7][7] == 0)
            {
                return 1;
            }
        }
        else
        {
            if(!validateArrangement(currBoard))
            {
                return 3;
            }

        }
        if(computerPlayer.getValid(currBoard))
        {
            LinkedList<String> currWords = copyList(computerPlayer.getDetectedWords());
            humanWords.addAll(getNewWords(prevWords, currWords));
            computerPlayer.play(bag);
            if(computerPlayer.getFinishedLetters())
            {
                return 5;
            }
            computerWords.addAll(getNewWords(currWords, computerPlayer.getDetectedWords()));
        }
        else {
            return 2;
        }
        if(Arrays.deepEquals(prevBoard, currBoard))
        {
            return 4;
        }
        return 0;
    }

    /**
     * This method makes the computer play on its own without a play from the human first
     * It's used when the player forfeits a turn
     * @param currBoard The current state of the board
     * @param bag the Bag
     * @throws FileNotFoundException
     */
    public void cPlay(char[][] currBoard, Bag bag) throws FileNotFoundException {
        LinkedList<String> currWords = copyList(computerPlayer.getDetectedWords());
        computerPlayer.play(bag);
        computerWords.addAll(getNewWords(currWords, computerPlayer.getDetectedWords()));
    }

    public boolean getEmpty()
    {
        return computerPlayer.getBoard() == null;
    }

    /**
     * This method makes sure that the player's new words touch previous words on the board
     * @param currBoard
     * @return
     */
    boolean validateArrangement(char[][] currBoard)
    {
        boolean valid = false;
        setupNewBoard(currBoard);
        setupIntBoard();
        for(int i = 0; i <15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                if(addedWords[i][j] == 2)
                {
                    if(checkVicinity(i,j,addedWords))
                    {
                        return true;
                    }
                }
            }
        }

        return valid;
    }
    //helper method for validateArrangement
    void setupNewBoard(char[][] tmpBoard)
    {
        newBoard = new char[15][15];
        for(int i = 0; i < 15; i++)
        {
            System.arraycopy(tmpBoard[i], 0, newBoard[i], 0, 15);
        }
    }
    //helper method for validateArrangement, 1 represents the previous letters, 2 represents the new ones the human adds
    void setupIntBoard()
    {
        addedWords = new int[15][15];
        char[][] prevBoard = computerPlayer.getBoard();
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                if(prevBoard[i][j] != '\0')
                {
                    addedWords[i][j] = 1;
                }
            }
        }

        for(int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                if(newBoard[i][j] != 0 && addedWords[i][j] != 1)
                {
                    addedWords[i][j] = 2;
                }
            }
        }
    }
    //Another helper method for validateArrangement, checks the vicinity in a 2d array
    boolean checkVicinity(int i, int j, int[][] arr)
    {
        //Checks the top left corner
        if(i == 0 && j == 0 )
        {
            return arr[i][j + 1] == 1 || arr[i + 1][j] == 1;

        }
        //Checks the top right corner
        else if(i == 0 && j == 14  )
        {
            return arr[i][j - 1] == 1 || arr[i + 1][j] == 1;
        }
        //Checks the bottom left corner
        else if(i == 14 && j == 0 )
        {
            return arr[i][j + 1] == 1 || arr[i - 1][j] == 1;
        }
        //Checks the bottom right corner
        else if(i == 14 && j == 14)
        {
            return arr[i][j - 1] == 1 || arr[i - 1][j] == 1;

        }
        //Checks the top row
        else if(i == 0 )
        {
            return arr[i][j + 1] == 1 || arr[i][j - 1] == 1 || arr[i + 1][j] == 1;

        }
        //Checks the bottom row
        else if(i == 14)
        {
            return arr[i][j + 1] == 1 || arr[i][j - 1] == 1 || arr[i - 1][j] == 1;
        }
        //Checks the left column
        else if(j == 0 )
        {
            return arr[i][j + 1] == 1 || arr[i + 1][j] == 1 || arr[i - 1][j] == 1;
        }
        //Checks the right column
        else if(j == 14 )
        {
            return arr[i][j - 1] == 1 || arr[i + 1][j] == 1 || arr[i - 1][j] == 1;
        }
        //Checks the rest of the board
        else
        {
            return arr[i][j + 1] == 1 || arr[i][j - 1] == 1 || arr[i + 1][j] == 1 || arr[i - 1][j] == 1;
        }
    }
    private boolean isEmpty(char[][] arr)
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

    private LinkedList<String> getNewWords(LinkedList<String> prevWords, LinkedList<String> currWords)
    {
        LinkedList<String> newWords = new LinkedList<>();
        for(String word : currWords)
        {
            if(!prevWords.contains(word))
            {
                newWords.add(word);
            }
        }
        return newWords;
    }

    public LinkedList<String> getComputerWords() {
        return computerWords;
    }
    public LinkedList<String> getHumanWords() {
        return humanWords;
    }
    public LinkedList<String> copyList(LinkedList<String> list)
    {
        return new LinkedList<>(list);
    }
}
