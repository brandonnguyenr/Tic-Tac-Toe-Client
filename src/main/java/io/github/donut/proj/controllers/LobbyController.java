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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Intermediate screen where player 1 and player 2 select their desired
 * name and token.
 * @author Joey Campbell
 * @version 0.1
 */
public class LobbyController implements Initializable, ISubject {

    @FXML
    public Label title;


    // Using Utsav's RestrictiveTextField makes it so you cannot
    //   access SceneBuilder. Incase you need to access sceneBuilder for
    //   the lobbyPage.fxml, uncomment the TextField Objects below
    //   and commment the RestrictiveTextFields above. Adjust the fxml also
    //   by switching the RestrictiveTextFields to TextFields.

//    @FXML
//    public TextField nameEntryMP1;
//
//    @FXML
//    public TextField nameEntryMP2;

    @FXML
    public ImageView createLobbyButton;

    @FXML
    public ScrollPane lobbyPage;

    @FXML
    private ImageView backButton;

    @FXML
    private VBox vboxLobby;

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
    private final Image createLobbyButtonIdle = new Image(Objects.requireNonNull(
            getClass().
            getClassLoader().
            getResourceAsStream("io/github/donut/proj/images/icons/create_lobby_button.png")
    ));
    private final Image createLobbyButtonHover = new Image(Objects.requireNonNull(
            getClass().
            getClassLoader().
            getResourceAsStream("io/github/donut/proj/images/icons/create_lobby_button_hover.png")
    ));

    private final Image lobbyBackground = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/theme_1/gradient_bluegreen.png")
    ));

    private final Image joinGamePic = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/join_game.png")
    ));

    ImageView lobbyViewBg = new ImageView(lobbyBackground);


    /**
     * Initializes a LobbyController object after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     * @author Joey Campbell
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lobbyPage.setId("lobbyPage");

        createLobbyButton.setId("createLobbyButton");
//        lobbyPage.setContent(lobbyViewBg);
//        lobbyPage.setContent(vboxLobby);

        //TODO: Will need to get the list of rooms (empty or full) and display

        VBox lobbyVbox = new VBox();

//        lobbyPage.setContent(lobbyVbox);

        ArrayList<String> playerList = new ArrayList<>();
        playerList.add("utsav vs joey");
        playerList.add("kord vs grant");
        playerList.add("chris vs brandon");
        playerList.add("gang vs no");
        playerList.add("oh vs bro");
        playerList.add("hell vs no");
        playerList.add("god vs damn");
        playerList.add("kord vs grant");
        playerList.add("chris vs brandon");
        playerList.add("utsav vs joey");
        playerList.add("kord vs grant");
        playerList.add("chris vs brandon");
        playerList.add("utsav vs joey");
        playerList.add("kord vs grant");
        playerList.add("chris vs brandon");
        playerList.add("utsav vs joey");
        playerList.add("kord vs grant");
        playerList.add("chris vs brandon");

        for (int i = 0; i < playerList.size(); i++) {

            Label name = new Label(playerList.get(i));
            name.setStyle("-fx-font-family: \"Chalkduster\"");
            name.setStyle("-fx-font-size: 12");

            ImageView joinGame = new ImageView(joinGamePic);
            joinGame.setFitHeight(20);
            joinGame.setFitWidth(20);


            HBox game = new HBox(30, joinGame, name);

            lobbyVbox.setSpacing(10);
            lobbyVbox.getChildren().add(game);
        }

        lobbyPage.setContent(lobbyVbox);
    }



    /**
     * This method will start the game when the start button is clicked
     *
     * @param actionEvent : mouse click
     * @author Utsav Parajuli
     * @author Joey Campbell
     */
    public void onCreateLobbyButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();
//        GameController game = new GameController(
//                new Player(player1Name + " (" + tokenP1 + ")", tokenP1),
//                new Player(player2Name + " (" + tokenP2 + ")", tokenP2));
//
//        EventManager.notify(this, game);
//        EventManager.removeAllObserver(this);


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
    public void onCreateLobbyButtonEnter() {
        createLobbyButton.setImage(createLobbyButtonHover);
    }

    /**
     * Event handler for start button idle effect
     *
     * @author Utsav Parajuli
     */
    public void onCreateLobbyButtonExit() {
        createLobbyButton.setImage(createLobbyButtonIdle);
    }
}