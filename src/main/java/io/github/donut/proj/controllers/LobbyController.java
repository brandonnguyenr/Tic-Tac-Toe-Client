package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Lobby Screen in works
 * @author Utsav Parajuli
 * @version 0.1
 */
public class LobbyController extends AbstractController implements ISubject {

    @FXML
    public Label title;

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

    @FXML
    ImageView lobbyViewBg = new ImageView(lobbyBackground);


    /**
     * Initializes a LobbyController object after its root element has been
     * completely processed.
     *
     * @author Utsav Parajuli
     */
    @FXML
    public void initialize() {

        lobbyPage.setId("lobbyPage");

        createLobbyButton.setId("createLobbyButton");

        //TODO: Will need to get the list of rooms (empty or full) and display

        VBox lobbyVbox = new VBox();

        ArrayList<String> playerList = new ArrayList<>();

        for (int j = 0; j < 50; j++) {
            playerList.add("Currently Under Development!");
        }

        for (String s : playerList) {
            Label name = new Label(s);
            name.setStyle("-fx-text-fill: rgba(255,255,255,0.69)");
            name.setStyle("-fx-font-size: 12");

            ImageView joinGame = new ImageView(joinGamePic);
            joinGame.setFitHeight(20);
            joinGame.setFitWidth(20);


            HBox game = new HBox(30, joinGame, name);

            lobbyVbox.setSpacing(10);
            lobbyVbox.getChildren().add(game);
        }

        lobbyPage.setContent(lobbyVbox);

        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);

        createLobbyButton.setOnMouseClicked(this::onCreateLobbyButtonClick);
        createLobbyButton.setOnMouseEntered(this::onCreateLobbyButtonEnter);
        createLobbyButton.setOnMouseExited(this::onCreateLobbyButtonExit);
        /*========================Action Events END=========================*/
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
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Kord Boniadi
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
//        EventManager.removeAllObserver(this);
        EventSounds.getInstance().playButtonSound1();
        stage.setScene(AppController.getScenes().get(SceneName.Main).getScene(false));
//        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        window.setTitle(Util.TITLE);
//        window.setScene(((AppController) window.getUserData()).mainScene);
//        window.setResizable(false);
    }

    /**
     * Event handler for back button hover effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonEnter(MouseEvent mouseEvent) {
        backButton.setImage(backButtonHover);
    }

    /**
     * Event handler for back button idle effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonExit(MouseEvent mouseEvent) {
        backButton.setImage(backButtonIdle);
    }

    /**
     * Event handler for start button hover effect
     *
     * @author Utsav Parajuli
     */
    public void onCreateLobbyButtonEnter(MouseEvent mouseEvent) {
        createLobbyButton.setImage(createLobbyButtonHover);
    }

    /**
     * Event handler for start button idle effect
     *
     * @author Utsav Parajuli
     */
    public void onCreateLobbyButtonExit(MouseEvent mouseEvent) {
        createLobbyButton.setImage(createLobbyButtonIdle);
    }
}