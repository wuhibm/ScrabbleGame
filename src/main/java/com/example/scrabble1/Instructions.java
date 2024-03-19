package com.example.scrabble1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

/**
 * This method displays instructions to the user
 *
 * @since 27-04-2023
 * @version 1.0
 * @author wuhibmezemir
 */
public class Instructions {

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menuScreen.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    @FXML
    void onPlay(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameScreen.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Scrabble");
        stage.show();
    }

    @FXML
    void onLink(ActionEvent event) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(URI.create("https://www.scrabblepages.com/scrabble/rules/"));
    }

}
