package com.example.scrabble1;

import java.util.ArrayList;

/**
 * This class represents a bag of letters in the game of Scrabble
 * It has the functionality to get random letters from the bag
 *
 * @version 1.2
 * @since 2023-05-08
 * @author wuhibmezemir
 */
public class Bag {
    private ArrayList<Character> bag = new ArrayList<>(100);

    public Bag()
    {
        for(int i = 0; i < 14; i++)
        {
            bag.add('E');
        }
        for(int i = 0; i < 9; i++)
        {
            bag.add('A');
            bag.add('I');
        }
        for(int i = 0; i < 8; i++)
        {
            bag.add('O');
        }
        for(int i = 0; i < 6; i++)
        {
            bag.add('N');
            bag.add('R');
            bag.add('T');
        }
        for(int i = 0; i < 4; i++)
        {
            bag.add('L');
            bag.add('S');
            bag.add('U');
            bag.add('D');
        }
        for(int i = 0; i < 3; i++)
        {
            bag.add('G');
        }
        for(int i = 0; i < 2; i++)
        {
            bag.add('B');
            bag.add('C');
            bag.add('M');
            bag.add('P');
            bag.add('F');
            bag.add('H');
            bag.add('V');
            bag.add('W');
            bag.add('Y');
        }
        bag.add('K');
        bag.add('J');
        bag.add('X');
        bag.add('Q');
        bag.add('Z');
    }

    /**
     * This method is responsible for returning a tile into the bag
     * @param tile the tile to be returned
     */
    public void returnTile(char tile)
    {
        if(bag.size() < 100)
        {
            bag.add(tile);
        }
        else
        {
            System.err.println("Bag is full");
        }
    }

    /**
     * This method is responsible for printing the contents of the bag
     * It shows how much of each letter remains in the bag
     * @return a String representation of the contents of the bag
     */
    public String printBagContents()
    {
        String contents = "";
        contents += "Bag contents: " + "\n" + "Bag size: " + bag.size() + "\n";
        int[] frequencies = new int[26];
        for (Character character : bag) {
            try {
                frequencies[(int) character - 'A']++;
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Error in printBagContents(), all letters in the bag must be capital");
            }
        }
        for(int i = 0; i < 26; i++)
        {
            contents += (char)(i + 'A') + ": " + frequencies[i] + "  ";
        }

        return contents;
    }

    public char getTile()
    {
        int index = (int) (Math.random() * bag.size());
        char tile = bag.get(index);
        bag.remove(index);
        return tile;
    }
    public int getSize() {
        return bag.size();
    }
    public boolean isEmpty() {
        return bag.isEmpty();
    }
}
