package io.github.donut.proj.controllers;

import io.github.coreutils.proj.enginedata.Board;
import io.github.coreutils.proj.enginedata.Token;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.MoveData;
import io.github.coreutils.proj.messages.RoomData;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.common.Player;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.Getter;

import java.util.Objects;

/**
 * This class handles the game board page UI
 * @author Kord Boniadi
 */
@Getter
public class BoardPageController extends AbstractController implements IObserver, ISubject {
    @FXML
    private Label playerNameLeft;

    @FXML
    private Label playerNameRight;

    @FXML
    private ImageView backButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private Pane overlayPane;

    @FXML
    private Label winnerLabel;

    @FXML
    private Label exitPrompt;

    private final BoardUI boardUI;
    private Board board = null;
    private RoomData room;
    private final boolean isMultiplayer;
    private boolean myTurn;
    private Token myToken;

    private final GameController game;

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

    public BoardPageController(BoardUI board, RoomData room) {
        this.room = room;
        this.boardUI = board;
        this.board = new Board();
        this.game = null;
        this.isMultiplayer = true;

        if (room.getPlayer1().getPlayerUserName().equals(AppController.getPlayer().getPlayerUserName())) {
            myToken = Token.X;
            myTurn = true;
        } else {
            myToken = Token.O;
            myTurn = false;
        }

        this.boardUI.setMoveHandler((col, row) -> {
            if (this.board.getToken(col, row) == Token.BLANK && myTurn) {
                myTurn = false;
                this.board.updateToken(col, row, myToken);
                GlobalAPIManager.getInstance().send(new MoveData(room.getRoomID(),
                        AppController.getPlayer().getPlayerUserName(), col, row, System.currentTimeMillis()), Channels.ROOM_MOVE.toString());
                this.boardUI.drawBoard(this.board);
            }
        });
    }

    /**
     * Constructor
     * @param board instance of boardUI
     * @param game instance of gameController
     * @author Kord Boniadi
     */
    public BoardPageController(BoardUI board, GameController game) {
        this.boardUI = board;
        this.game = game;
        this.isMultiplayer = false;
        EventManager.register(game, this);
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     * @author Kord Boniadi
     */
    @FXML
    public void initialize() {
        if (game != null) {
            playerNameLeft.setText(game.getPlayer1().getPlayerName());
            playerNameRight.setText(game.getPlayer2().getPlayerName());
        }
        playerNameLeft.setPrefWidth(150);
        playerNameRight.setPrefWidth(150);

        ((VBox) borderPane.getCenter()).getChildren().add(boardUI);
        BorderPane.setAlignment(playerNameLeft, Pos.TOP_CENTER);
        BorderPane.setAlignment(playerNameRight, Pos.TOP_CENTER);

        borderPane.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                EventSounds.getInstance().playButtonSound4();
                if (!isMultiplayer)
                    EventManager.cleanup();
                AppController.getScenes().get(SceneName.BOARD_PAGE).clearCache();
                stage.setScene(AppController.getScenes().get(SceneName.Main).getScene(false));
            }
        });

        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        overlayPane.setVisible(false);

        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);
        /*========================Action Events END=========================*/
    }

    public void toggleTurn() {
        myTurn = true;
    }
    /**
     * Event handler for back button
     * @param actionEvent mouse event
     * @author Kord Boniadi
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        if (!isMultiplayer) {
            EventManager.removeAllObserver(game);
            EventManager.removeAllObserver(game.getPlayer1());
            EventManager.removeAllObserver(game.getPlayer2());
            EventManager.removeAllObserver(boardUI);
        }
        AppController.getScenes().get(SceneName.BOARD_PAGE).clearCache();
        stage.setScene(AppController.getScenes().get(SceneName.Main).getScene(false));
    }

    /**
     * Event handler for back button hover effect
     * @author Kord Boniadi
     */
    public void onBackButtonEnter(MouseEvent actionEvent) {
        backButton.setImage(backButtonHover);
    }

    /**
     * Event handler for back button idle effect
     * @author Kord Boniadi
     */
    public void onBackButtonExit(MouseEvent actionEvent) {
        backButton.setImage(backButtonIdle);
    }

    public void updatedBoard(Board board) {
        this.board = board;
        Platform.runLater(() -> this.boardUI.drawBoard(board));
    }
    /**
     * New info is received through this method. Object decoding is needed
     *
     * @param eventType General Object type
     * @author Kord Boniadi
     */
    @Override
    public void update(Object eventType) {
        if (eventType instanceof Player) {
            if (((Player) eventType).getPlayerToken() == game.getPlayer1().getPlayerToken()) {
                playerNameLeft.setBorder(new Border(new BorderStroke(
                        Color.GOLD,
                        BorderStrokeStyle.SOLID,
                        null,
                        BorderStroke.THIN,
                        Insets.EMPTY
                )));
                playerNameRight.setBorder(null);
            } else if (((Player) eventType).getPlayerToken() == game.getPlayer2().getPlayerToken()) {
                playerNameRight.setBorder(new Border(new BorderStroke(
                        Color.GOLD,
                        BorderStrokeStyle.SOLID,
                        null,
                        BorderStroke.THIN,
                        Insets.EMPTY
                )));
                playerNameLeft.setBorder(null);
            }
        } else if (eventType instanceof GameController.Results) {
            GameController.Results temp = (GameController.Results) eventType;

            exitPrompt.setText("Press ENTER to return to main menu...");
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.6), evt -> exitPrompt.setVisible(false)),
                    new KeyFrame(Duration.seconds(1.2), evt -> exitPrompt.setVisible(true))
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            String result = (temp.getWinner() != null) ? temp.getWinner().getPlayerName() +
                    " won!!!" : "It's a Draw";

            winnerLabel.setText(result);
            borderPane.requestFocus();
            overlayPane.setVisible(true);
        }
    }
}
