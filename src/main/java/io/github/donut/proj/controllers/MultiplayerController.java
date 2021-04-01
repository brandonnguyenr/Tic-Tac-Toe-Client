package io.github.donut.proj.controllers;

import static io.github.donut.proj.common.Token.*;

import io.github.donut.proj.common.Player;
import io.github.donut.proj.common.Token;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.RestrictiveTextField;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Intermediate screen where player 1 and player 2 select their desired
 * name and token.
 * @author Joey Campbell
 * @version 0.1
 */
public class MultiplayerController implements Initializable, ISubject {

    @FXML
    public Label title;

    @FXML
    public Label player1Name;

    @FXML
    public RestrictiveTextField nameEntryMP1;

    @FXML
    public RestrictiveTextField nameEntryMP2;

    // Using Utsav's RestrictiveTextField makes it so you cannot
    //   access SceneBuilder. Incase you need to access sceneBuilder for
    //   the multiplayerPage.fxml, uncomment the TextField Objects below
    //   and commment the RestrictiveTextFields above. Adjust the fxml also
    //   by switching the RestrictiveTextFields to TextFields.

//    @FXML
//    public TextField nameEntryMP1;
//
//    @FXML
//    public TextField nameEntryMP2;

    @FXML
    public ImageView startButton;

    @FXML
    private ImageView backButton;

    @FXML
    public RadioButton tokenXP1;

    @FXML
    public RadioButton tokenOP1;

    @FXML
    public RadioButton tokenXP2;

    @FXML
    public RadioButton tokenOP2;

    private Token tokenP1;
    private Token tokenP2;

    private final Image backButtonIdle = new Image(Objects.requireNonNull(
            getClass().
            getClassLoader().
            getResourceAsStream("io/github/donut/proj/images/common/back_arrow.png")
    ));
    private final Image backButtonHover = new Image(Objects.requireNonNull(
            getClass().
            getClassLoader().
            getResourceAsStream("io/github/donut/proj/images/common/back_arrow_hover.png")
    ));
    private final Image startButtonIdle = new Image(Objects.requireNonNull(
            getClass().
            getClassLoader().
            getResourceAsStream("io/github/donut/proj/images/theme_2/start_button.png")
    ));
    private final Image startButtonHover = new Image(Objects.requireNonNull(
            getClass().
            getClassLoader().
            getResourceAsStream("io/github/donut/proj/images/theme_2/start_button_hover.png")
    ));

    /**
     * Initializes a MultiplayerController object after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     * @author Joey Campbell
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Allows the toggling between both player's X/O toggles
        ToggleGroup tokenGroup1 = new ToggleGroup();
        ToggleGroup tokenGroup2 = new ToggleGroup();

        // Makes it so that the radio buttons cannot be
        //   changed by arrow keys
        tokenXP1.setFocusTraversable(false);
        tokenOP1.setFocusTraversable(false);
        tokenXP2.setFocusTraversable(false);
        tokenOP2.setFocusTraversable(false);

        tokenXP1.setToggleGroup(tokenGroup1);
        tokenOP1.setToggleGroup(tokenGroup1);
        tokenXP1.setSelected(true);

        tokenXP2.setToggleGroup(tokenGroup2);
        tokenOP2.setToggleGroup(tokenGroup2);
        tokenOP2.setSelected(true);

        tokenP1 = X;
        tokenP2 = O;

        // Uses the RestrictiveTextField to limit names to 5 characters
        nameEntryMP1.setMaxLength(5);
        nameEntryMP2.setMaxLength(5);

    }

    /**
     * Allows the user to click enter instead of start once their names are
     * entered into the text fields
     * @param keyEvent The key being pressed
     * @author Utsav Parajuli
     */
    public void onNameEntered(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            startGame();
        }
    }

    /**
     * Toggles the P2 radio buttons when a P1 radio button is clicked
     * i.e. it ensures that players have different tokens
     * @param actionEvent The mouse click on the radio button
     * @author Joey Campbell
     */
    public void onToggleClick1 (MouseEvent actionEvent) {
        if (tokenXP1.isSelected()) {
            tokenOP2.setSelected(true);
            tokenP1 = X;
            tokenP2 = O;
        }
        else {
            tokenXP2.setSelected(true);
            tokenP1 = O;
            tokenP2 = X;
        }
    }


    /**
     * Toggles the P1 radio buttons when a P2 radio button is clicked
     * i.e. it ensures that players have different tokens
     * @param actionEvent The mouse click on the radio button
     * @author Joey Campbell
     */
    public void onToggleClick2 (MouseEvent actionEvent) {
        if (tokenXP2.isSelected()) {
            tokenOP1.setSelected(true);
            tokenP2 = X;
            tokenP1 = O;
        }
        else {
            tokenXP1.setSelected(true);
            tokenP2 = O;
            tokenP1 = X;
        }
    }

    /**
     * This method will start the game when the start button is clicked
     *
     * @param actionEvent : mouse click
     * @author Utsav Parajuli
     * @author Joey Campbell
     */
    public void onStartButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();
        startGame();
    }

    /**
     * Sets each player's name/token and instantiates a game object with
     * the two players
     * @author Joey Campbell
     */
    private void startGame() {
        String player1Name;
        String player2Name;

        if (nameEntryMP1.getText().isEmpty()) {
            player1Name = "P1";
        }
        else {
            player1Name = nameEntryMP1.getText();
        }

        if (nameEntryMP2.getText().isEmpty()) {
            player2Name = "P2";
        }
        else {
            player2Name = nameEntryMP2.getText();
        }

        GameController game = new GameController(
                new Player(player1Name + " (" + tokenP1 + ")", tokenP1),
                new Player(player2Name + " (" + tokenP2 + ")", tokenP2));

        EventManager.notify(this, game);
        EventManager.removeAllObserver(this);
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Kord Boniadi
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventManager.removeAllObserver(this);
        EventSounds.getInstance().playButtonSound1();
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Donut Tic Tac Toe");
        window.setScene(((AppController) window.getUserData()).mainScene);
        window.setResizable(false);
    }

    /**
     * Event handler for back button hover effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonEnter() {
        backButton.setImage(backButtonHover);
    }

    /**
     * Event handler for back button idle effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonExit() {
        backButton.setImage(backButtonIdle);
    }

    /**
     * Event handler for start button hover effect
     *
     * @author Utsav Parajuli
     */
    public void onStartButtonEnter() {
        startButton.setImage(startButtonHover);
    }

    /**
     * Event handler for start button idle effect
     *
     * @author Utsav Parajuli
     */
    public void onStartButtonExit() {
        startButton.setImage(startButtonIdle);
    }
}
