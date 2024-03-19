package com.example.scrabble1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * This class shows the menu screen
 *
 * @since 27-04-2023
 * @version 1.0
 * @author wuhibmezemir
 */
public class MenuScreen {
    @FXML
    private Button instructions;

    @FXML
    private Button newGame;

    @FXML
    private AnchorPane menuPane;

    @FXML
    void onNewGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameScreen.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Scrabble");
        stage.show();
    }

    @FXML
    void OnInstructions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("instructions.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Instructions");
        stage.show();
    }

    public void onQuit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Press OK to quit, or cancel to return to the game.");

        if(alert.showAndWait().get().getText().equals("OK"))
        {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }

    }
}
