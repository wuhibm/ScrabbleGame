package com.example.scrabble1;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class WordConstructionTest {

    @Test
    void testCombineTwo()
    {
        WordConstruction wordConstruction = new WordConstruction();
        char[] arr = {'A','B','C','D'};
        LinkedList<LinkedList<Character>> combs = wordConstruction.combine(4,2,arr);
        assertEquals(6, combs.size());
        LinkedList<LinkedList<Character>> trgt = new LinkedList<>();
        trgt.add(new LinkedList<>());
        trgt.add(new LinkedList<>());
        trgt.add(new LinkedList<>());
        trgt.add(new LinkedList<>());
        trgt.add(new LinkedList<>());
        trgt.add(new LinkedList<>());
        trgt.get(0).add('A');
        trgt.get(0).add('B');
        trgt.get(1).add('A');
        trgt.get(1).add('C');
        trgt.get(2).add('A');
        trgt.get(2).add('D');
        trgt.get(3).add('B');
        trgt.get(3).add('C');
        trgt.get(4).add('B');
        trgt.get(4).add('D');
        trgt.get(5).add('C');
        trgt.get(5).add('D');
        assertEquals(trgt, combs);
    }

    @Test
    void combineFive(){

        WordConstruction wordConstruction = new WordConstruction();
        char[] arr = {'A','B','C','D','E', 'F','G'};
        LinkedList<LinkedList<Character>> combs = wordConstruction.combine(7,5,arr);
        assertEquals(21, combs.size());
    }

    @Test
    void testWords()
    {
        WordConstruction wordConstruction = new WordConstruction();
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        letters.add('T');
        letters.add('G');
        letters.add('N');
        assertEquals(5180, wordConstruction.makeWords(letters, 'O').size());
    }

    @Test
    void testValidWords() throws FileNotFoundException {
        WordConstruction wordConstruction = new WordConstruction();
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        letters.add('T');
        letters.add('G');
        letters.add('N');
        assertEquals(76, wordConstruction.validWords(letters, 'O').size());

    }

    @Test
    void testBestWord() throws FileNotFoundException {
        WordConstruction wordConstruction = new WordConstruction();
        LinkedList<Character> letters = new LinkedList<>();
        letters.add('D');
        letters.add('I');
        letters.add('M');
        letters.add('E');
        letters.add('T');
        letters.add('G');
        letters.add('N');
        LinkedList<String> validWords = wordConstruction.validWords(letters, 'O');
        //assertEquals(76, validWords.size());
        System.out.println(wordConstruction.bestWord(letters, 'O'));

    }
}