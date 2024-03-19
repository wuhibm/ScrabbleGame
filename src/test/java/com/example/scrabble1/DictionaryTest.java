package com.example.scrabble1;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    @Test
    void checkWordHundredThousandTimes() throws FileNotFoundException {
        //Takes 1.124 seconds
        Dictionary dict = new Dictionary();
        dict.populate();
        String word = "HELLO";
        for(int i = 0; i < 100000; i++)
        {
            assertTrue(dict.check(word));
        }
    }

    @Test
    void checkHundredThousandWords() throws FileNotFoundException {
        //Takes 1.675 seconds
        String filename = "ScrabbleDictionary.txt";
        File file = new File(filename);
        Scanner scan = new Scanner(file);
        String[] words = new String[100000];
        Dictionary dict = new Dictionary();
        dict.populate();
        for(int i = 0; i < 100000; i++)
        {
            String word = scan.nextLine();
            words[i] = word;
        }

        for (String word : words) {
            assertTrue(dict.check(word));
        }
    }

    @Test
    void checkEfficiencyForEightLetters() throws FileNotFoundException {
        char[][] board = new char[15][15];
        Bag bag = new Bag();
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        letters.add('T');
        letters.add('G');
        letters.add('N');
        letters.add('O');
        String[] words = computerPlayer.constructWord(letters).toArray(new String[0]);
         //computerPlayer.getAllWords().toArray(new String[0]);
        Dictionary dict = new Dictionary();
        dict.populate();
        int numValid = 0;
        for (String word : words) {
            if(dict.check(word))
            {
                numValid++;
            }
        }
        assertEquals(1, numValid);
        // 1/40320 rate of validity, not efficient
    }

    @Test
    void checkEfficiencyForSevenLetters() throws FileNotFoundException {
        char[][] board = new char[15][15];
        Bag bag = new Bag();
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        letters.add('T');
        letters.add('G');
        letters.add('N');
        String[] words = computerPlayer.constructWord(letters).toArray(new String[0]);
        //computerPlayer.getAllWords().toArray(new String[0]);
        Dictionary dict = new Dictionary();
        dict.populate();
        int numValid = 0;
        for (String word : words) {
            if(dict.check(word))
            {
                numValid++;
            }
        }
        assertEquals(0, numValid);
        // 0/5040 rate of validity, not efficient
    }

    @Test
    void checkEfficiencyForSixLetters() throws FileNotFoundException {
        char[][] board = new char[15][15];
        Bag bag = new Bag();
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        letters.add('T');
        letters.add('N');
        String[] words = computerPlayer.constructWord(letters).toArray(new String[0]);
        //computerPlayer.getAllWords().toArray(new String[0]);
        Dictionary dict = new Dictionary();
        dict.populate();
        int numValid = 0;
        for (String word : words) {
            if(dict.check(word))
            {
                numValid++;
            }
        }
        assertEquals(1, numValid);
        // 1/720 rate of validity, not efficient
    }

    @Test
    void checkEfficiencyForFiveLetters() throws FileNotFoundException {
        char[][] board = new char[15][15];
        Bag bag = new Bag();
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        //letters.add('T');
        letters.add('N');
        String[] words = computerPlayer.constructWord(letters).toArray(new String[0]);
        //computerPlayer.getAllWords().toArray(new String[0]);
        Dictionary dict = new Dictionary();
        dict.populate();
        int numValid = 0;
        for (String word : words) {
            if(dict.check(word))
            {
                numValid++;
            }
        }
        assertEquals(1, numValid);
        // 2/120 rate of validity, efficient enough
    }
    @Test
    void checkEfficiencyForFourLetters() throws FileNotFoundException {
        char[][] board = new char[15][15];
        Bag bag = new Bag();
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        //letters.add('T');
        //letters.add('N');
        String[] words = computerPlayer.constructWord(letters).toArray(new String[0]);
        //computerPlayer.getAllWords().toArray(new String[0]);
        Dictionary dict = new Dictionary();
        dict.populate();
        int numValid = 0;
        for (String word : words) {
            if(dict.check(word))
            {
                numValid++;
            }
        }
        assertEquals(1, numValid);
        // 2/24 rate of validity, efficient enough
    }
}