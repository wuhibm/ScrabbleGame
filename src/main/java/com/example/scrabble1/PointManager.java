package com.example.scrabble1;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class represents a point manager for the game of Scrabble
 *
 * @version 1.0
 * @since 2023-05-04
 * @author wuhibmezemir
 */
public class PointManager {

    private final HashMap<Character, Integer> pointStorage = new HashMap<>();
    private int playerPoints;
    private int computerPoints;
    private int spentPoints;
    public PointManager()
    {
        pointStorage.put('A', 1);
        pointStorage.put('B', 3);
        pointStorage.put('C', 3);
        pointStorage.put('D', 2);
        pointStorage.put('E', 1);
        pointStorage.put('F', 4);
        pointStorage.put('G', 2);
        pointStorage.put('H', 4);
        pointStorage.put('I', 1);
        pointStorage.put('J', 8);
        pointStorage.put('K', 5);
        pointStorage.put('L', 1);
        pointStorage.put('M', 3);
        pointStorage.put('N', 1);
        pointStorage.put('O', 1);
        pointStorage.put('P', 3);
        pointStorage.put('Q', 10);
        pointStorage.put('R', 1);
        pointStorage.put('S', 1);
        pointStorage.put('T', 1);
        pointStorage.put('U', 1);
        pointStorage.put('V', 4);
        pointStorage.put('W', 4);
        pointStorage.put('X', 8);
        pointStorage.put('Y', 4);
        pointStorage.put('Z', 10);
        spentPoints = 0;
    }

    public int getPoints(char letter)
    {
        return pointStorage.get(letter);
    }
    public int getPoints(String word)
    {
        int points = 0;
        for(int i = 0; i < word.length(); i++)
        {
            points += getPoints(word.charAt(i));
        }
        return points;
    }

    public int getHumanPoints(LinkedList<String> words)
    {
        int points = 0;
        for (String word : words) {
            points += getPoints(word);
        }
        points -= spentPoints;
        if(points <0)
        {
            System.err.println("Can not deduct points, PointManager.getHumanPoints()");
        }
        playerPoints = points;
        return points;
    }
    public int getComputerPoints(LinkedList<String> words)
    {
        int points = 0;
        for (String word : words) {
            points += getPoints(word);
        }
        computerPoints = points;
        return points;
    }

    public int getHumanPoints() {
        return playerPoints;
    }
    public int getComputerPoints() {
        return computerPoints;
    }

    public void deductPoints()
    {
        spentPoints +=2;
    }
}
