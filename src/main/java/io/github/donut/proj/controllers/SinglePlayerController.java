package io.github.donut.proj.controllers;

import io.github.donut.proj.PlayerType.Human;
import io.github.donut.proj.PlayerType.IPlayerType;
import io.github.donut.proj.PlayerType.NPCEasyMode;
import io.github.donut.proj.PlayerType.NPCHardMode;
import io.github.donut.proj.common.Player;
import io.github.donut.proj.common.Token;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.RestrictiveTextField;
import io.github.donut.proj.utils.Util;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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

import static io.github.donut.proj.common.Token.O;
import static io.github.donut.proj.common.Token.X;

/**
 * Intermediate Screen controller class where the player can enter their name
 * @author Utsav Parajuli
 * @version 0.3
 */
public class SinglePlayerController implements Initializable, ISubject {

    @FXML
    public Label singlePlayerTitle;

    @FXML
    public Label title;

    @FXML
    public RestrictiveTextField nameEntry;

    @FXML
    public ImageView startButton;

    @FXML
    private ImageView backButton;

    @FXML
    private Label difficultyLevelTitle;

    @FXML
    private Label tokenSelectorTitle;

    @FXML
    private RadioButton tokenX;

    @FXML
    private RadioButton tokenO;

    @FXML
    private RadioButton easyMode;

    @FXML
    private RadioButton hardMode;

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
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     * @author Utsav Parajuli
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //title screen
        singlePlayerTitle.setText("Single Player Mode");
        singlePlayerTitle.setAlignment(Pos.TOP_CENTER);

        //setting the name entry title
        title.setText("Please Enter Your Name");
        title.setAlignment(Pos.CENTER);

        //name entry box
        nameEntry.setAlignment(Pos.CENTER);

        //max number of characters user can enter
        nameEntry.setMaxLength(5);

        //start button
        startButton.setId("startButton");

        //difficulty level option
        difficultyLevelTitle.setText("Difficulty: ");
        ToggleGroup difficultyGroup = new ToggleGroup();
        easyMode.setText("Easy");
        hardMode.setText("Hard");
        easyMode.setToggleGroup(difficultyGroup);
        easyMode.setSelected(true);
        hardMode.setToggleGroup(difficultyGroup);

        //token selector option
        tokenSelectorTitle.setText("    Token: ");
        ToggleGroup tokenGroup = new ToggleGroup();
        tokenX.setText("X");
        tokenO.setText("O");
        tokenX.setToggleGroup(tokenGroup);
        tokenX.setSelected(true);
        tokenO.setToggleGroup(tokenGroup);
    }

    /**
     * When the name of the user is entered and all the options are chosen this method will start the game
     *
     * @param keyEvent : press of a key
     * @author Utsav Parajuli
     */
    public void onNameEntered(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            startGame();
        }
    }

    /**
     * This method will start the game when the start button is clicked
     *
     * @param actionEvent : mouse click
     */
    public void onStartButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();
        startGame();
    }

    /**
     * Helper method that will check the options entered by the user and assign the correct token to both the
     * player and PlayerType. Will also check the difficulty level. Then will instantiate a game object and notify the observers
     *
     * @author : Utsav Parajuli
     */
    private void startGame() {
        Token userToken;
        Token cpuToken;
        String cpuLevel;
        String userName;
        IPlayerType artificialBrain;

        if (nameEntry.getText().isEmpty()) {
            userName = "Guest";
        }
        else {
            userName = nameEntry.getText();
        }

        if (tokenO.isSelected()) {
            userToken = O;
            cpuToken = X;
        } else {
            userToken = X;
            cpuToken = O;
        }

        if (easyMode.isSelected()) {
            cpuLevel = "Rook";
            artificialBrain = new NPCEasyMode();
        } else {
            cpuLevel = "Pro";
            artificialBrain = new NPCHardMode(cpuToken, userToken);
        }

        // TODO make an actual selection
        GameController game = new GameController(
                new Player(userName + " (" + userToken + ")", userToken, new Human()),
                new Player(cpuLevel + " (" + cpuToken + ")", cpuToken, artificialBrain));

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
        window.setTitle(Util.TITLE);
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