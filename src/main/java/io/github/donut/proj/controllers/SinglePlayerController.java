package io.github.donut.proj.controllers;

import io.github.donut.proj.PlayerType.Human;
import io.github.donut.proj.PlayerType.IPlayerType;
import io.github.donut.proj.PlayerType.NPCEasyMode;
import io.github.donut.proj.PlayerType.NPCHardMode;
import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.common.Player;
import io.github.donut.proj.common.Token;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.proj.utils.RestrictiveTextField;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

import static io.github.donut.proj.common.Token.O;
import static io.github.donut.proj.common.Token.X;

/**
 * Intermediate Screen controller class where the player can enter their name
 * @author Utsav Parajuli
 * @version 0.3
 */
public class SinglePlayerController extends AbstractController implements ISubject {

    @FXML
    public Label singlePlayerTitle;

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
     * @author Utsav Parajuli
     */
    @FXML
    public void initialize() {

        //title screen
        singlePlayerTitle.setText("Single Player Mode");
        singlePlayerTitle.setAlignment(Pos.TOP_CENTER);

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

        /*========================Action Events START=========================*/
        startButton.setOnMouseClicked(this::onStartButtonClick);
        startButton.setOnMouseEntered(this::onStartButtonEnter);
        startButton.setOnMouseExited(this::onStartButtonExit);

        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);
        /*========================Action Events END=========================*/
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

        if (AppController.getUserName().equals("")) {
            userName = "Guest";
        }
        else {
            userName = AppController.getUserName();
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
        easyMode.setSelected(true);
        tokenX.setSelected(true);

        // TODO make an actual selection
        GameController game = new GameController(
                new Player(userName + " (" + userToken + ")", userToken, new Human()),
                new Player(cpuLevel + " (" + cpuToken + ")", cpuToken, artificialBrain));

        BoardUI boardUI = new BoardUI();
        BoardPageController controller = new BoardPageController(boardUI, game);

        if (game.getPlayer1().getPlayerType() instanceof Human) {
            EventManager.register(boardUI, (Human) game.getPlayer1().getPlayerType());
        }

        if (game.getPlayer2().getPlayerType() instanceof Human) {
            EventManager.register(boardUI, (Human) game.getPlayer2().getPlayerType());
        }

        EventManager.register(boardUI, game.getPlayer1());
        EventManager.register(boardUI, game.getPlayer2());
        EventManager.register(game, boardUI);
        stage.setScene(AppController.getScenes().get(SceneName.BOARD_PAGE).getScene(controller, false));
        game.startGame();
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Kord Boniadi
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        easyMode.setSelected(true);
        tokenX.setSelected(true);
        stage.setScene(AppController.getScenes().get(SceneName.Main).getScene(false, false));
    }

    /**
     * Event handler for back button hover effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonEnter(MouseEvent actionEvent) {
        backButton.setImage(backButtonHover);
    }

    /**
     * Event handler for back button idle effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonExit(MouseEvent actionEvent) {
        backButton.setImage(backButtonIdle);
    }

    /**
     * Event handler for start button hover effect
     *
     * @author Utsav Parajuli
     */
    public void onStartButtonEnter(MouseEvent actionEvent) {
        startButton.setImage(startButtonHover);
    }

    /**
     * Event handler for start button idle effect
     *
     * @author Utsav Parajuli
     */
    public void onStartButtonExit(MouseEvent actionEvent) {
        startButton.setImage(startButtonIdle);
    }

}