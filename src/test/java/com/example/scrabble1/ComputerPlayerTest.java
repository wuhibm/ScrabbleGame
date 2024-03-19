package com.example.scrabble1;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {

    @Test
    void testVerifyBoard() throws FileNotFoundException {
        char[][] board = new char[15][15];
        ArrayList<Character> letters = new ArrayList<>();
        letters.add('B');
        letters.add('K');
        letters.add('E');
        letters.add('M');
        letters.add('S');
        letters.add('G');
        letters.add('T');

        //spells fast on board vertically
        board[6][9] = 'F';
        board[7][9] = 'A';
        board[8][9] = 'S';
        board[9][9] = 'T';
        //spells smart horizontally
        board[7][7] = 'S';
        board[7][8] = 'M';
        //board[7][9] = 'A';
        board[7][10] = 'R';
        board[7][11] = 'T';
        //spells stake horizontally
        board[9][8] = 'S';
        //board[9][9] = 'T';
        board[9][10] = 'A';
        board[9][11] = 'K';
        board[9][12] = 'E';
        //spells take vertically
        //board[7][11] = 'T';
        board[8][11] = 'A';
        //board[9][11] = 'K';
        board[10][11] = 'E';
        ComputerPlayer computerPlayer = new ComputerPlayer(board, letters);
        computerPlayer.findWords();
        ArrayList<String> words = new ArrayList<>();
        words.add("FAST");
        words.add("SMART");
        words.add("TAKE");
        words.add("STAKE");
        assertEquals(words, computerPlayer.getDetectedWords());
        assertTrue(computerPlayer.getValid());
    }

    @Test
    void testPlay() throws FileNotFoundException {
        Bag bag = new Bag();
        char[][] board = new char[15][15];
        ArrayList<Character> letters = new ArrayList<>();
        letters.add('B');
        letters.add('K');
        letters.add('E');
        letters.add('M');
        letters.add('S');
        letters.add('G');
        letters.add('T');

        //spells fast on board vertically
        board[6][9] = 'F';
        board[7][9] = 'A';
        board[8][9] = 'S';
        board[9][9] = 'T';
        //spells smart horizontally
        board[7][7] = 'S';
        board[7][8] = 'M';
        //board[7][9] = 'A';
        board[7][10] = 'R';
        board[7][11] = 'T';
        //spells stake horizontally
        board[9][8] = 'S';
        //board[9][9] = 'T';
        board[9][10] = 'A';
        board[9][11] = 'K';
        board[9][12] = 'E';
        //spells take vertically
        //board[7][11] = 'T';
        board[8][11] = 'A';
        //board[9][11] = 'K';
        board[10][11] = 'E';
        ComputerPlayer computerPlayer = new ComputerPlayer(board, letters);
        computerPlayer.play(bag);
        ArrayList<String> words = new ArrayList<>();
        words.add("SMART");
        words.add("TAKE");
        words.add("STAKE");
        words.add("FAST");
        words.add("ES");


        assertEquals(words, computerPlayer.getDetectedWords());
        assertTrue(computerPlayer.getValid());
    }

    @Test
    void testVerifyOnRightEdge() throws FileNotFoundException {
        Bag bag = new Bag();
        char[][] board = new char[15][15];
        ArrayList<Character> letters = new ArrayList<>();
        letters.add('B');
        letters.add('K');
        letters.add('E');
        letters.add('M');
        letters.add('S');
        letters.add('G');
        letters.add('T');

        //spells DEL on board vertically
        board[2][6] = 'D';
        board[3][6] = 'E';
        board[4][6] = 'L';
        //spells LACE horizontally
        board[4][7] = 'A';
        board[4][8] = 'C';
        board[4][9] = 'E';
        //spells ALAS vertically
        board[5][7] = 'L';
        board[6][7] = 'A';
        board[7][7] = 'S';
        //spells SEEM horizontally
        board[7][8] = 'E';
        board[7][9] = 'E';
        board[7][10] = 'M';
        //spells MEN vertically
        board[8][10] = 'E';
        board[9][10] = 'N';
        //spells NOPE horizontally
        board[9][11] = 'O';
        board[9][12] = 'P';
        board[9][13] = 'E';
        //spells PODS vertically
        board[10][12] = 'O';
        board[11][12] = 'D';
        board[12][12] = 'S';
        //spells SUE horizontally
        board[12][13] = 'U';
        board[12][14] = 'E';
        ComputerPlayer computerPlayer = new ComputerPlayer(board, letters);
        ArrayList<String> words = new ArrayList<>();
        words.add("DEL");
        words.add("LACE");
        words.add("ALAS");
        words.add("SEEM");
        words.add("MEN");
        words.add("NOPE");
        words.add("PODS");
        words.add("SUE");
        assertTrue(computerPlayer.getValid());
        assertEquals(words, computerPlayer.getDetectedWords());
    }

    @Test
    void testVerifyOnBottomEdge() throws FileNotFoundException {
        Bag bag = new Bag();
        char[][] board = new char[15][15];
        ArrayList<Character> letters = new ArrayList<>();
        letters.add('B');
        letters.add('K');
        letters.add('E');
        letters.add('M');
        letters.add('S');
        letters.add('G');
        letters.add('T');

        board[3][7] = 'J';
        board[4][6] = 'T';
        board[4][7] = 'A';
        board[5][7] = 'W';
        board[6][7] = 'E';
        board[7][7] = 'D';
        board[7][8] = 'E';
        board[7][9] = 'L';
        board[7][10] = 'T';
        board[8][9] = 'O';
        board[9][9] = 'T';
        board[10][9] = 'S';
        board[10][10] = 'O';
        board[11][10] = 'A';
        board[12][10] = 'T';
        board[13][10] = 'E';
        board[14][10] = 'N';
        ComputerPlayer computerPlayer = new ComputerPlayer(board, letters);
        ArrayList<String> words = new ArrayList<>();
        words.add("JAWED");
        words.add("TA");
        words.add("DELT");
        words.add("LOTS");
        words.add("SO");
        words.add("OATEN");

        assertTrue(computerPlayer.getValid());
        assertEquals(words, computerPlayer.getDetectedWords());
    }

    @Test
    void testVerifyOnInvalidWords() throws FileNotFoundException {
        char[][] board = new char[15][15];
        ArrayList<Character> letters = new ArrayList<>();
        letters.add('B');
        letters.add('K');
        letters.add('E');
        letters.add('M');
        letters.add('S');
        letters.add('G');
        letters.add('T');

        //spells fast on board vertically
        board[6][9] = 'F';
        board[7][9] = 'A';
        board[8][9] = 'S';
        board[9][9] = 'T';
        //spells smart horizontally
        board[7][7] = 'S';
        board[7][8] = 'M';
        board[7][10] = 'R';
        board[7][11] = 'T';
        //spells stabe horizontally
        board[9][8] = 'S';
        //board[9][9] = 'T';
        board[9][10] = 'A';
        board[9][11] = 'B';
        board[9][12] = 'E';
        //spells take vertically
        //board[7][11] = 'T';
        board[8][11] = 'A';
        //board[9][11] = 'K';
        board[10][11] = 'E';
        ComputerPlayer computerPlayer = new ComputerPlayer(board, letters);
        computerPlayer.findWords();

        assertFalse(computerPlayer.getValid());
    }

    @Test
    void testConstructWordsLengthThree() throws FileNotFoundException {
        char[][] board = new char[15][15];
        Bag bag = new Bag();
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('A');
        letters.add('B');
        letters.add('C');
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        LinkedList<String> words = new LinkedList<>();
        words.add("ABC");
        words.add("ACB");
        words.add("BAC");
        words.add("BCA");
        words.add("CAB");
        words.add("CBA");
        assertEquals(words, computerPlayer.constructWord(letters));
        LinkedList<String> words2 = new LinkedList<>();
        words2.add("BC");
        words2.add("CB");
        words2.add("ABC");
        words2.add("ACB");
        words2.add("AC");
        words2.add("CA");
        words2.add("BAC");
        words2.add("BCA");
        words2.add("AB");
        words2.add("BA");
        words2.add("CAB");
        words2.add("CBA");
        //assertEquals(words2, computerPlayer.getAllWords());
    }

    @Test
    void testConstructWordsLengthEight() throws FileNotFoundException {
        char[][] board = new char[15][15];
        Bag bag = new Bag();
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('A');
        letters.add('B');
        letters.add('C');
        letters.add('D');
        letters.add('E');
        letters.add('F');
        letters.add('G');
        letters.add('H');
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        //int size1 = 40320;
        //This math is correct without redundancies but my method adds redundancies
        //TODO: potentially remove redundancies
        //Checking if the list contains a word before adding takes too long, about 52 seconds
        int size2 = 109592;
//        for(int i = 0; i < 21; i++)
//        {
//            assertEquals(120, computerPlayer.constructWord(letters).size());
//        }
        computerPlayer.constructWord(letters);
        //assertEquals(3612, computerPlayer.getAllWords().size());

        //assertEquals(size2, computerPlayer.getAllWords().size());
    }

    @Test
    void testValidWords() throws FileNotFoundException {
        //Takes INSANELY long to run ~ 2 minutes
        //Takes a lot shorter to check words before putting them in linked list but still long ~ 16.7s
        char[][] board = new char[15][15];
        Bag bag = new Bag();
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        letters.add('T');
        letters.add('G');
        letters.add('N');
        letters.add('O');
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        //computerPlayer.validWords(letters);
        //LinkedList<String> words = computerPlayer.validWords(letters);
        //assertEquals("NO", words.get(0));
        //177 valid words out of 282240 possible words, NOT efficient
    }

    @Test
    void spellWordVertical() throws FileNotFoundException {
        Bag bag = new Bag();
        char[][] board = new char[15][15];
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('I');
        letters.add('M');
        letters.add('E');
        letters.add('T');
        letters.add('G');
        letters.add('N');
        letters.add('O');

        board[7][7] = 'D';
        board[7][8] = 'E';
        board[7][9] = 'L';
        board[7][10] = 'T';
        board[8][9] = 'O';
        board[9][9] = 'T';
        board[10][9] = 'S';
        board[10][10] = 'O';
        board[11][10] = 'A';
        board[12][10] = 'T';
        board[13][10] = 'E';
        board[14][10] = 'N';
        ComputerPlayer computerPlayer = new ComputerPlayer(board, bag);
        WordConstruction wordConstruction = new WordConstruction();
        String word = wordConstruction.bestWord(letters, 'D');
        computerPlayer.spellVertically(word,'D', 7, 7);
        board[0][0] = '\0';
    }

}