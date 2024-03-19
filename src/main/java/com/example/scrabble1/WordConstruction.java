package com.example.scrabble1;

import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * This class creates words
 * This class contains code that carries out the math operation of combinations
 * That code is not my own, it was mostly from https://www.youtube.com/watch?v=uvk_HvqCQNc
 *
 * @since 17-05-2023
 * @author wuhibmezemir
 * @version 1
 */
public class WordConstruction {

    /**
     * This class does combinations
     * The code is not my own, but I have modified it slightly
     *
     * @param n The total number of elements
     * @param r The number of elements in each combination (i.e. nCr)
     * @param arr an Array of the total elements
     * @return Returns a LinkedList of LinkedLists of Characters with all the different combinations
     */
    public LinkedList<LinkedList<Character>> combine(int n, int r, char[] arr)
    {
        LinkedList<LinkedList<Character>> combos = new LinkedList<>();
        if(n != arr.length)
        {
            System.err.println("n must be the length of the array: combine()");
            return null;
        }
        if(r == 0)
        {
            combos.add(new LinkedList<>());
            return combos;
        }
        backTrack(1, new LinkedList<Character>(), n, r, combos, arr);
        return combos;
    }

    /**
     * This is a helper method to the combinations
     * Also not my own, also modified
     *
     * @param start The starting index
     * @param curr the current LinkedList of Characters, i.e. the current combination
     * @param n The total number of elements
     * @param r The number of elements in each combination (i.e. nCr)
     * @param combs The LinkedList of LinkedLists of Characters that will be returned
     * @param arr an Array of the total elements
     */
    private void backTrack(int start, LinkedList<Character> curr, int n, int r, LinkedList<LinkedList<Character>> combs, char[] arr)
    {
        if(curr.size() == r)
        {
            combs.add(new LinkedList<>(curr));
        }
        for(int i = start; i <= n && curr.size() < r; i++)
        {
            curr.add(arr[i-1]);
            backTrack(i+1, curr,n,r,combs,arr);
            curr.removeLast();
        }
    }

    /**
     * This is a recursive method which returns all possible permutations of a given series of characters
     * This method is my own
     *
     * @param ref the reference linked list of the letters to be arranged
     * @return A linked list of Strings with all the possible permutations
     */
    public LinkedList<String> constructWord(LinkedList<Character> ref)
    {
        LinkedList<String> foundWords = new LinkedList<>();
        if(ref.size() == 2)
        {
            foundWords.add("" + ref.get(0) + ref.get(1));
            foundWords.add("" + ref.get(1) + ref.get(0));
        }
        else if(ref.size() < 2)
        {
            System.err.println("Error: The size of the list is less than 2");
            return null;
        }
        else
        {
            for(int i = 0; i < ref.size(); i++)
            {
                LinkedList<Character> temp = new LinkedList<>(ref);
                if(i == 0)
                {
                    temp.remove(0);
                }
                else
                {
                    Character tempChar = temp.get(0);
                    temp.set(0, temp.get(i));
                    temp.set(i, tempChar);
                    ref.set(0, temp.get(0));
                    ref.set(i, tempChar);
                    temp.remove(0);
                }
                for(String word : constructWord(temp))
                {
                    foundWords.add(ref.get(0) + word);
                }
            }

        }
        return foundWords;
    }

    /**
     * This finds all the possible valid words that can be made with the given reference
     * This one gives all words that can be added to a board that already has tiles
     * This is why it takes in a char
     * This is my own code
     * @param ref The reference linked list of characters
     * @param a The character that is already on the board
     * @return A linked list of Strings with all the possible valid words that can be placed on the board
     * @throws FileNotFoundException
     */
    public LinkedList<String> validWords(LinkedList<Character> ref, char a) throws FileNotFoundException {
        Dictionary dictionary = new Dictionary(true);
        LinkedList<String> validWords = new LinkedList<>();
        LinkedList<String> allWords = makeWords(ref, a);
        String[] words = allWords.toArray(new String[0]);
        for (String word : words) {
            if (dictionary.check(word)) {
                validWords.add(word);
            }
        }
        return validWords;
    }

    //An overloaded method for when there is no character already on the board
    public LinkedList<String> validWords(LinkedList<Character>ref) throws FileNotFoundException {
        Dictionary dictionary = new Dictionary(true);
        LinkedList<String> validWords = new LinkedList<>();
        LinkedList<String> allWords = makeWords(ref);
        String[] words = allWords.toArray(new String[0]);
        for (String word : words) {
            if (dictionary.check(word)) {
                validWords.add(word);
            }
        }
        return validWords;
    }

    /**
     * This method gives all the possible words that can be made with the given reference
     * All the words are between lengths 5-2
     * Including 6-8 letter words takes very long ~ 2 minutes and isn't worth it for the number of words it finds
     * This is my own code
     * @param ref The reference linked list of characters
     * @param a The character that is already on the board
     * @return A linked list of Strings with all the possible letter permutations that can be made with the given reference
     */
    public LinkedList<String> makeWords(LinkedList<Character> ref, char a)
    {
        //TODO: find all possible 5,4,3, and 2-letter words with the given reference, should come out to 5180 possibilities
        LinkedList<String> allWords = new LinkedList<>();
        char[] bagLetters = convertToArray(ref);
        //five-letter words
        lengthCombinations(5,bagLetters,a,allWords);
        //four-letter words
        lengthCombinations(4,bagLetters,a,allWords);
        //there-letter words
        lengthCombinations(3,bagLetters,a,allWords);
        //two-letter words
        lengthCombinations(2,bagLetters,a,allWords);

        return allWords;
    }
    //Overloaded version of the same method for when there aren't already tiles on the board
    public LinkedList<String> makeWords(LinkedList<Character> ref)
    {
        LinkedList<String> allWords = new LinkedList<>();
        char[] bagLetters = convertToArray(ref);
        lengthCombinations(5,bagLetters,allWords);
        //four-letter words
        lengthCombinations(4,bagLetters,allWords);
        //there-letter words
        lengthCombinations(3,bagLetters,allWords);
        //two-letter words
        lengthCombinations(2,bagLetters,allWords);

        return allWords;
    }

    /**
     * This is a helper method for makeWords
     * It finds all the possible combinations of a given length of letters which include the given character
     * The given character is the tile already on the board
     * It adds all of them to a linked list of strings
     * @param length The length of each of the combinations
     * @param letters The letters to be combined
     * @param a The character that is already on the board
     * @param allWords The linked list of strings to add all the combinations to
     */
    private void lengthCombinations(int length, char[] letters, char a, LinkedList<String> allWords)
    {
        LinkedList<LinkedList<Character>> lengthCombinations = combine(letters.length,length-1,letters);
        for (LinkedList<Character> lengthCombination : lengthCombinations) {
            LinkedList<Character> tmp = new LinkedList<>(lengthCombination);
            tmp.add(a);
            //All possible permutations of the combination
            allWords.addAll(constructWord(tmp));
        }
    }
    //Overloaded version of the same method for when there aren't already tiles on the board
    private void lengthCombinations(int length, char[] letters, LinkedList<String> allWords)
    {
        LinkedList<LinkedList<Character>> lengthCombinations = combine(letters.length,length,letters);
        for (LinkedList<Character> lengthCombination : lengthCombinations) {
            LinkedList<Character> tmp = new LinkedList<>(lengthCombination);
            //All possible permutations of the combination
            allWords.addAll(constructWord(tmp));
        }
    }

    /**
     * This method finds the single best word to play in a given context
     * It finds the word with the highest point value that may fit on the board at a specific location
     * This is my own code
     * @param ref The reference linked list of characters
     * @param a The character that is already on the board
     * @return The best word to play in the given context
     * @throws FileNotFoundException
     */
    public String bestWord(LinkedList<Character> ref, char a) throws FileNotFoundException {
        //TODO: Handle no valid words
        LinkedList<String> validWords = validWords(ref, a);
        int point = 0;
        int index = 0;
        PointManager pointManager = new PointManager();
        for(int i = 0; i < validWords.size(); i++)
        {
            if(pointManager.getPoints(validWords.get(i)) > point)
            {
                point = pointManager.getPoints(validWords.get(i));
                index = i;
            }
        }
        if(validWords.size()>0){
            return validWords.get(index);
        }
        else {
            return null;
        }
    }

    //Overloaded version of the same method for when there aren't already tiles on the board
    public String bestWord(LinkedList<Character> ref) throws FileNotFoundException {
        LinkedList<String> validWords = validWords(ref);
        int point = 0;
        int index = 0;
        PointManager pointManager = new PointManager();
        for(int i = 0; i < validWords.size(); i++)
        {
            if(pointManager.getPoints(validWords.get(i)) > point)
            {
                point = pointManager.getPoints(validWords.get(i));
                index = i;
            }
        }
        if(validWords.size()>0){
            return validWords.get(index);
        }
        else {
            return null;
        }
    }


    //Helper method for converting Character linked lists to char arrays
    private char[] convertToArray(LinkedList<Character> ref)
    {
        Character[] tmp = ref.toArray(new Character[0]);
        char[] fnl = new char[tmp.length];
        for(int i = 0; i < tmp.length; i++)
        {
            fnl[i] = tmp[i];
        }
        return fnl;
    }


}
