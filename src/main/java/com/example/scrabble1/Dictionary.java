package com.example.scrabble1;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

/**
 * This class is used to check if a word is in the dictionary
 * It uses a hash table to store the words
 *
 * @version 1.4
 * @since 2023-04-07
 * @author wuhibmezemir
 */
public class Dictionary {

    public Dictionary()
    {}

    public Dictionary(boolean a) throws FileNotFoundException {
        populate();
    }

    //TODO: change to HashSet
    private final LinkedList<String> [] table = new LinkedList[2000];
    private final Set<String> tmp = new HashSet<>();
    /**
     * This is a pretty simple multiplicative hash function
     * The lengths of the linked lists it produces range from about 200-600
     * @param word the word to be hashed
     * @return an index for the hash table where the word should/would be stored
     */
    int hash(String word)
    {
        word = word.toUpperCase();
        int fnl = 1;

        for(int i = 0; i < word.length(); i++)
        {
            fnl += word.charAt(i) * 2;
            fnl *= 2;
        }
        fnl = fnl%2000;
        return fnl;
    }

    /**
     * This method populates the hash table with the words from the dictionary
     * @throws FileNotFoundException if the dictionary file is not found
     */
    void populate() throws FileNotFoundException {
        String filename = "ScrabbleDictionary.txt";
        File file = new File(filename);
        Scanner scan = new Scanner(file);
        while(scan.hasNextLine())
        {
            String word = scan.nextLine();
            word = word.toUpperCase();
            tmp.add(word);
//            int index = hash(word);
//            if(table[index] == null)
//            {
//                table[index] = new LinkedList<>();
//                table[index].add(word);
//            }
//            else
//            {
//                table[index].add(word);
//            }
        }
    }

    /**
     * This method checks if a word is in the dictionary
     * @param word The word being searched for
     * @return true if the word is in the dictionary, false if it is not
     */
    boolean check(String word)
    {

        word = word.toUpperCase();
        return tmp.contains(word);
//        if(table == null)
//        {
//            System.err.println("Can't check for a word in the dictionary before populating");
//        }
//        else
//        {
//            int index = hash(word);
//            if(table[index] == null)
//            {
//                return false;
//            }
//            for(int i = 0; i < table[index].size(); i++)
//            {
//                if(table[index].get(i).equals(word.toUpperCase()))
//                {
//                    return true;
//                }
//            }
//        }
//        return false;
    }

}
