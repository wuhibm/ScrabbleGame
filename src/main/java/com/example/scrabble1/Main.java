package com.example.scrabble1;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            e.consume();
            onQuit(stage);
        });
    }
    public void onQuit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Press OK to quit, or cancel to return to the game.");

        if(alert.showAndWait().get().getText().equals("OK"))
        {
            stage.close();
        }

    }

    public static void main(String[] args) {
        launch();
    }

}