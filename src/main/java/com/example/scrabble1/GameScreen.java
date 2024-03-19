package com.example.scrabble1;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * This class is the controller for the game screen
 * It manages the tiles on the board and rack
 * It also manages the players' turns
 *
 * @version 2.9
 * @since 2023-04-19
 * @author wuhibmezemir
 */
public class GameScreen implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView refresh;
    @FXML
    private Label pointsViewer;
    @FXML
    private TextArea bagViewer;
    @FXML
    private Label compPointsViewer;
    @FXML
    private TextArea humanHistoryViewer;
    @FXML
    private TextArea computerHistoryViewer;
    @FXML
    private TextField searchPrompt;
    private Board board;
    private Draggable draggable;
    private final int TILE_SIZE = 50;
    private final LinkedList <Tile> currTiles = new LinkedList<>();
    private final LinkedList <Tile> placedTiles = new LinkedList<>();
    private final LinkedList <Tile> allTiles = new LinkedList<>();
    private CurrPlayer currPlayer;
    private final TileManager tileManager = new TileManager();
    private final BoardManager boardManager = new BoardManager();
    private final Bag bag = new Bag();
    private final PointManager pointManager = new PointManager();


    /**
     * This method is called when the user clicks on a tile
     * It initializes the board and fills the rack with 7 tiles
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draggable = new Draggable(750, 750, TILE_SIZE, pane);
        board = new Board(750, 750, TILE_SIZE, pane);
        board.draw();
        tileManager.addTiles(currTiles, pane, TILE_SIZE, allTiles, draggable, bag);
        try {
            currPlayer = new CurrPlayer(scanBoard(), bag);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        bagViewer.setEditable(false);
        humanHistoryViewer.setEditable(false);
        computerHistoryViewer.setEditable(false);
        bagViewer.setText(bag.printBagContents());

    }

    /**
     * This method is called when the player quits the game
     * It takes the player back to the menu screen
     *
     * @param event The event that triggers this method, pressing the button
     * @throws IOException If the FXML file is not found
     */
    @FXML
    void onQuit(ActionEvent event) throws IOException {
        endGame();
    }

    /**
     * This method enables the user to check if a word is in the Scrabble dictionary
     * They must have at least 2 points to use this feature
     * @param event Clicking on the link
     * @throws IOException
     */
    @FXML
    void onLink(ActionEvent event) throws IOException {
        if(pointManager.getHumanPoints() >= 2)
        {
            pointManager.deductPoints();
            pointsViewer.setText("Your Points: " + pointManager.getHumanPoints(currPlayer.getHumanWords()));
            String word = searchPrompt.getText();
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(URI.create("https://wordfind.com/word/" + word + "/"));
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not Enough Points");
            alert.setHeaderText("You do not have enough points to use this feature");
            alert.setContentText("You need at least 2 points to search the Scrabble dictionary");
            alert.show();
        }
        searchPrompt.clear();
    }

    //This method scans the board, see boardManager class for more details
    public char[][] scanBoard()
    {
        return boardManager.scanBoard(allTiles);
    }
    //This method puts the current tiles in play back onto the rack, see tileManager class for more details
    @FXML
    void onReset(ActionEvent event) {
        tileManager.resetTiles(currTiles);
    }

    //This method skips one of the player's turns
    @FXML
    void onSkip(ActionEvent event) throws FileNotFoundException {
        tileManager.resetTiles(currTiles);
        forfeitTurn();
    }
    //This method gives the player a new set of 7 letters
    @FXML
    void onRefresh(MouseEvent event) throws FileNotFoundException {

        //Reset the tiles on the board back to the rack
        tileManager.resetTiles(currTiles);
        //return the old tiles to the bag
        for (Tile currTile : currTiles) {
            bag.returnTile(currTile.getLetter());
        }
        //add 7 new tiles from the bag to the rack
        tileManager.addTiles(currTiles, pane, TILE_SIZE, allTiles, draggable, bag);
        //refresh the bag information
        bagViewer.setText(bag.printBagContents());
        forfeitTurn();
        //rotate the refresh button
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(refresh);
        rotate.setByAngle(360);
        rotate.setDuration(Duration.millis(1000));
        rotate.play();
    }

    /**
     * This method is called when the player clicks on the play button
     * It checks if the player's move is valid then calls on the computer to play
     * It updates the board and the rack accordingly
     * It also updates the history and the points
     *
     * @param event The event that triggers this method, pressing the button
     * @throws FileNotFoundException If the dictionary file is not found
     */
    @FXML
    void onPlay(ActionEvent event) throws FileNotFoundException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int played = currPlayer.play(scanBoard(), bag);
        switch (played) {
            case 0 -> {
                //If the player's move is valid, this is executed
                char[][] newBoard = boardManager.copyBoard(currPlayer.getPlayerBoard());
                boardManager.updateBoard(newBoard, allTiles, 50, pane);
                for (Tile currTile : currTiles) {
                    Node node = currTile.draw();
                    if (node.getLayoutY() < 750) {
                        draggable.makeStatic(currTile, node.getLayoutX(), node.getLayoutY());
                        placedTiles.add(currTile);
                    }
                }
                removePlacedTiles();
                tileManager.refillTiles(currTiles, pane, TILE_SIZE, allTiles, draggable, bag);
                int humanPoints = pointManager.getHumanPoints(currPlayer.getHumanWords());
                pointsViewer.setText("Your Points: " + humanPoints);
                int computerPoints = pointManager.getComputerPoints(currPlayer.getComputerWords());
                compPointsViewer.setText("Computer Points: " + computerPoints);
                updateHistory();
                bagViewer.setText(bag.printBagContents());
                if (currTiles.isEmpty()) {
                    endGame();
                }
            }
            case 1 -> {
                alert.setTitle("Error 1");
                alert.setHeaderText("You must start on the star");
                alert.setContentText("The first tile of the game must be placed in the middle of the board, on the star.");
                alert.show();
                tileManager.resetTiles(currTiles);
            }
            case 2 -> {
                alert.setTitle("Error 2");
                alert.setHeaderText("You must play a valid word");
                alert.setContentText("The word you played is not in the dictionary. Please try again with a valid word.");
                alert.show();
                forfeitTurn();
                tileManager.resetTiles(currTiles);
            }
            case 3 -> {
                alert.setTitle("Error 3");
                alert.setHeaderText("New tiles must touch existing tiles");
                alert.setContentText("The word you played does not touch any existing tiles. Please make sure when placing new tiles that they are next to at least on that is already on the board.");
                alert.show();
                tileManager.resetTiles(currTiles);
            }
            case 4 -> {
                alert.setTitle("Error 4");
                alert.setHeaderText("You must place tiles on the board");
                alert.setContentText("During your turn, you must place at least one tile on the board. Please try again.");
                alert.show();
                tileManager.resetTiles(currTiles);
            }
            case 5 -> {
                endGame();
            }
        }
    }
    //removes the tiles the player played from the current tiles
    private void removePlacedTiles()
    {
        for(Tile tile : placedTiles)
        {
            currTiles.remove(tile);
        }
    }

    //updates the history of the player
    private void updateHistory()
    {
        humanHistoryViewer.setText("Your History: ");
        computerHistoryViewer.setText("Computer History: ");
        for(int i = 0; i < currPlayer.getHumanWords().size(); i++)
        {
            humanHistoryViewer.appendText("\n"+ currPlayer.getHumanWords().get(i) + ": " + pointManager.getPoints(currPlayer.getHumanWords().get(i)));
        }
        for(int i = 0; i < currPlayer.getComputerWords().size(); i++)
        {
            computerHistoryViewer.appendText("\n"+ currPlayer.getComputerWords().get(i) + ": " + pointManager.getPoints(currPlayer.getComputerWords().get(i)));
        }
    }
    /**
     * This method ends the game and displays the final screen
     * From it you can close the game or return to the menu screen
     */
    private void endGame()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("finalScreen.fxml"));
        try {
            DialogPane finalPane = loader.load();
            //Sets up the dialog box
            FinalScreen finalScreen = loader.getController();
            finalScreen.setComputerPoints(pointManager.getComputerPoints());
            finalScreen.setHumanPoints(pointManager.getHumanPoints());
            if(pointManager.getComputerPoints() < pointManager.getHumanPoints())
            {
                finalScreen.setWinner(0);
            }
            else if(pointManager.getComputerPoints() > pointManager.getHumanPoints())
            {
                finalScreen.setWinner(1);
            }
            else {
                finalScreen.setWinner(2);
            }
            //shows the dialog box
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(finalPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menuScreen.fxml")));
                Stage stage = (Stage) ( pane.getScene().getWindow());
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Menu");
                stage.show();
            }
            else if (result.isPresent() && result.get() == ButtonType.CLOSE)
            {
                var alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit");
                alert.setHeaderText("Are you sure you want to quit?");
                alert.setContentText("Press OK to quit, or cancel to return to the game.");

                if(alert.showAndWait().get().getText().equals("OK"))
                {
                    Stage stage = (Stage) ( pane.getScene().getWindow());
                    stage.close();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called when the player forfeits their turn
     * @throws FileNotFoundException
     */
    void forfeitTurn() throws FileNotFoundException {
        currPlayer.cPlay(scanBoard(), bag);
        char[][] newBoard = boardManager.copyBoard(currPlayer.getPlayerBoard());
        boardManager.updateBoard(newBoard, allTiles,50, pane);
        pointsViewer.setText("Your Points: " + pointManager.getHumanPoints());
        compPointsViewer.setText("Computer Points: " + pointManager.getComputerPoints(currPlayer.getComputerWords()));
        updateHistory();
        bagViewer.setText(bag.printBagContents());
    }
}