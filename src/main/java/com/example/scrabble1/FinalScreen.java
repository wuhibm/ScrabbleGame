package com.example.scrabble1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * This is the controller for the final screen
 * This is what you see when the game has finished
 *
 * @since 14-05-2023
 * @version 1.2
 * @author wuhibmezemir
 */
public class FinalScreen {
    @FXML
    private Label computerPoints;

    @FXML
    private Label playerPoints;

    @FXML
    private TextArea winnerView;

    public void setComputerPoints(int points) {
        computerPoints.setText("Computer Points: " + points);
    }
    public void setHumanPoints(int points) {
        playerPoints.setText("Your Points: " + points);
    }

    public void setWinner(int won) {
        if(won == 0) {
            winnerView.setText("Congratulations! You Won!");
        }
        else if (won == 1){
            winnerView.setText("You Lost!");
        }
        else {
            winnerView.setText("It's a tie!");
        }
    }


}
