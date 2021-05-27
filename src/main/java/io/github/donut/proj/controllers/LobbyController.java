package io.github.donut.proj.controllers;

import io.github.coreutils.proj.enginedata.Token;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.PlayerData;
import io.github.coreutils.proj.messages.RoomData;
import io.github.coreutils.proj.messages.RoomFactory;
import io.github.donut.proj.callbacks.GameCallback;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.callbacks.RoomListCallback;
import io.github.donut.proj.callbacks.RoomRequestCallback;
import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.*;

/**
 * Lobby Screen contains the lobby viewer table view which shows the active games.
 * Will have functionality to join games.
 * @author Utsav Parajuli
 * @author Joey Campbell
 * @version 0.2
 */
public class LobbyController extends AbstractController implements ISubject {
    @FXML
    public Label title;

    @FXML
    private BorderPane multiplayerPage;

    @FXML
    public ImageView createLobbyButton;

    @FXML
    public ScrollPane lobbyPage;

    private TableView<RoomData> lobbyTableView;

    @FXML
    private ObservableList<RoomData> tvOList;

    @FXML
    private ImageView backButton;


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

    /**
     * Initializes a LobbyController object after its root element has been
     * completely processed. It also loads the tableview object
     *
     * @author Utsav Parajuli
     * @author Joey Campbell
     */
    @FXML
    public void initialize() {

        TableColumn<RoomData, String> lobbyNameCol = new TableColumn<>("Lobby");
        lobbyNameCol.setReorderable(false);
        lobbyNameCol.setResizable(false);
        lobbyNameCol.setSortable(false);
        lobbyNameCol.setPrefWidth(150);
        lobbyNameCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<RoomData, String> playersCol = new TableColumn<>("Creator");
        playersCol.setReorderable(false);
        playersCol.setResizable(false);
        playersCol.setSortable(false);
        playersCol.setPrefWidth(200);
        playersCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayer1().getPlayerUserName()));

        TableColumn<RoomData, Integer> playerCountCol = new TableColumn<>("Count");
        playerCountCol.setReorderable(false);
        playerCountCol.setResizable(false);
        playerCountCol.setSortable(false);
        playerCountCol.setPrefWidth(80);
        playerCountCol.setCellValueFactory(new PropertyValueFactory<>("playerCount"));

        lobbyTableView = new TableView<>();

        lobbyTableView.setItems(tvOList);

        lobbyTableView.getColumns().add(lobbyNameCol);
        lobbyTableView.getColumns().add(playersCol);
        lobbyTableView.getColumns().add(playerCountCol);

        multiplayerPage.setCenter(lobbyTableView);

        lobbyTableView.setPadding(new Insets(0, 115, 30, 115));
        lobbyTableView.setSelectionModel(null);
        lobbyTableView.setId("lobbyTableView");

        addJoinButtonToTable();

        GlobalAPIManager.getInstance().swapListener(new RoomListCallback(this::setLobbyListAsync),
                Channels.REQUEST + Channels.ROOM_LIST.toString(),
                Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);

        createLobbyButton.setOnMouseClicked(this::onCreateLobbyButtonClick);
        createLobbyButton.setOnMouseEntered(this::onCreateLobbyButtonEnter);
        createLobbyButton.setOnMouseExited(this::onCreateLobbyButtonExit);
        /*========================Action Events END=========================*/
    }

    public void setLobbyListAsync(List<RoomData> rooms) {
        Platform.runLater(() -> {
            setLobbyList(rooms);
        });
    }

    public void setLobbyList(List<RoomData> rooms) {
        ObservableList<RoomData> list = FXCollections.observableArrayList(rooms);
        lobbyTableView.setItems(list);

    }

    /**
     * Fills the last column in the table with a clickable button to join games.
     * @author Joey Campbell
     */
    private void addJoinButtonToTable() {
        TableColumn<RoomData, Void> joinCol = new TableColumn<>("Join Game");
        joinCol.setResizable(false);
        joinCol.setReorderable(false);
        joinCol.setSortable(false);
        joinCol.setPrefWidth(120);

        Callback<TableColumn<RoomData, Void>, TableCell<RoomData, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<RoomData, Void> call(final TableColumn<RoomData, Void> param) {
                        final TableCell<RoomData, Void> cell = new TableCell<>() {

                            private final Button btn = new Button("Join");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    joinRoomWorker(getTableView().getItems().get(getIndex()));
                                });

                                btn.setPrefWidth(120);

                                btn.styleProperty().bind(Bindings.when(btn.hoverProperty())
                                        .then("-fx-background-color: grey; " +
                                                "-fx-text-fill: khaki;" +
                                                "-fx-font-weight: bold")
                                        .otherwise("-fx-background-color: black; " +
                                                "-fx-text-fill: khaki;" +
                                                "-fx-font-weight: bold"));
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                        return cell;
                    }
                };

        joinCol.setCellFactory(cellFactory);
        lobbyTableView.getColumns().add(joinCol);
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
        createRoomDialog();
    }

    /**
     * This method will ask the player to enter the name of the room
     * @author Kord Boniadi
     */
    private void createRoomDialog() {
        Dialog<String> dialog = new Dialog<>();

        dialog.setTitle("Room Setup");
        dialog.initStyle(StageStyle.UTILITY);

        ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField field = new TextField();
        field.setPromptText("Lobby name..");

        grid.add(field, 0, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(param -> {
            if (param == createButton)
                return field.getText();
            return null;
        });
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(this::createRoomWorker);
    }

    private void matchWorker(RoomData room, PlayerData player) {
        BoardPageController game = new BoardPageController(new BoardUI(), room);
        Platform.runLater(() -> {
            stage.setScene(AppController.getScenes().get(SceneName.BOARD_PAGE).getScene(game, false));
            game.getPlayerNameLeft().setText(room.getPlayer1().getPlayerUserName());
            game.getPlayerNameRight().setText(room.getPlayer2().getPlayerUserName());
        });

        GameCallback gameCallback = new GameCallback(room);
        GlobalAPIManager.getInstance().swapListener(gameCallback, room.getRoomChannel());
        gameCallback.setBoardHandler((moveRequestData) -> {
            game.updatedBoard(moveRequestData.getBoard());
            if (moveRequestData.getCurrentPlayer() == null) {
                String results;
                if (moveRequestData.getWinningToken() == Token.X) {
                    results = moveRequestData.getRoomData().getPlayer1().getPlayerUserName() + " won!!";
                } else if (moveRequestData.getWinningToken() == Token.O) {
                    results = moveRequestData.getRoomData().getPlayer2().getPlayerUserName() + " won!!";
                } else {
                    results = "It's a Draw";
                }

                GlobalAPIManager.getInstance().send(RoomFactory.makeDisconnectRoom(room.getPlayer1()), Channels.ROOM_REQUEST.toString());
                Platform.runLater(() -> {
                    game.getExitPrompt().setText("Press ENTER to return to main menu...");
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(0.6), evt -> game.getExitPrompt().setVisible(false)),
                            new KeyFrame(Duration.seconds(1.2), evt -> game.getExitPrompt().setVisible(true))
                    );
                    timeline.setCycleCount(Animation.INDEFINITE);
                    timeline.play();

                    game.getWinnerLabel().setText(results);
                    game.getBorderPane().requestFocus();
                    game.getOverlayPane().setVisible(true);
                });
            } else if (moveRequestData.getCurrentPlayer().equals(player.getPlayerUserName())) {
                game.toggleTurn();
                if (moveRequestData.getRoomData().getPlayer1().getPlayerUserName().equals(player.getPlayerUserName())) {
                    Platform.runLater(() -> {
                        game.getPlayerNameLeft().setBorder(new Border(new BorderStroke(
                                Color.GOLD,
                                BorderStrokeStyle.SOLID,
                                null,
                                BorderStroke.THIN,
                                Insets.EMPTY
                        )));
                        game.getPlayerNameRight().setBorder(null);
                    });
                } else if (moveRequestData.getRoomData().getPlayer2().getPlayerUserName().equals(player.getPlayerUserName())) {
                    Platform.runLater(() -> {
                        game.getPlayerNameRight().setBorder(new Border(new BorderStroke(
                                Color.GOLD,
                                BorderStrokeStyle.SOLID,
                                null,
                                BorderStroke.THIN,
                                Insets.EMPTY
                        )));
                        game.getPlayerNameLeft().setBorder(null);
                    });
                }
            } else {
                if (moveRequestData.getRoomData().getPlayer1().getPlayerUserName().equals(player.getPlayerUserName())) {
                    Platform.runLater(() -> {
                        game.getPlayerNameRight().setBorder(new Border(new BorderStroke(
                                Color.GOLD,
                                BorderStrokeStyle.SOLID,
                                null,
                                BorderStroke.THIN,
                                Insets.EMPTY
                        )));
                        game.getPlayerNameLeft().setBorder(null);
                    });
                } else if (moveRequestData.getRoomData().getPlayer2().getPlayerUserName().equals(player.getPlayerUserName())) {
                    Platform.runLater(() -> {
                        game.getPlayerNameLeft().setBorder(new Border(new BorderStroke(
                                Color.GOLD,
                                BorderStrokeStyle.SOLID,
                                null,
                                BorderStroke.THIN,
                                Insets.EMPTY
                        )));
                        game.getPlayerNameRight().setBorder(null);
                    });
                }
            }
        });
    }

    /**
     * This is a helper method that will take the player to the waiting room from where the game will start
     * @param data: The Room data
     * @author Kord Boniadi
     * @author Utsav Parajuli
     */
    private void joinRoomWorker(RoomData data) {
        //gets the data of the player and adds them to the room
        AppController.setPlayerChannel(Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());

        PlayerData player = AppController.getPlayer();
        data.addPlayer(player);

        //creates an instance of the waiting room
        WaitingRoomController waitingRoom = new WaitingRoomController();

        //creates an instance of the room request callback where the room data and player is passed
        RoomRequestCallback callback = new RoomRequestCallback(data, player);

        //executes after the callback was resolved
        callback.setResolved((roomData) -> {
            //the message is displayed to the wait room
            Platform.runLater(() -> {
                waitingRoom.getPlayerName().setText("");
                waitingRoom.getPlayerName().setText("You've joined " + roomData.getPlayer1().getPlayerUserName() + "'s lobby");
                waitingRoom.getWaitingPageMessage().setText("");
                waitingRoom.getJoinMessage().setText("Game will start shortly...");
            });

            //creating an instance of the timer
            Timer timer = new Timer();
            //creating a task playerJoin which will execute after the player stays in the lobby for certain time
            TimerTask playerJoin = new TimerTask() {
                @Override
                public void run() {
                    matchWorker(roomData, player);
                    Platform.runLater(() -> {
                        waitingRoom.getJoinMessage().setText("");
                        waitingRoom.getWaitingPageMessage().setText("Waiting for another player to join your lobby");
                    });
                    //freeing up the thread
                    timer.cancel();
                }
            };
            //scheduling the wait time to be 8 seconds
            timer.schedule(playerJoin, 3000L);
        });

        //executes in the case that there was error joining the room
        callback.setRejected((roomData) -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("There was a problem joining that room... It may be full.");
                alert.show();
            });
        });
        GlobalAPIManager.getInstance().swapListener(callback, Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());

        //launching the waiting page
        stage.setScene(AppController.getScenes().get(SceneName.WAITING_PAGE).getScene(waitingRoom, false));

    }

    /**
     * This is a helper method that will take create the room and take the creator to a waiting page
     * @param title: the name of the room
     * @author Utsav Parajuli
     * @author Kord Boniadi
     */
    private void createRoomWorker(String title) {
        //gets the data of the player and adds them to the room
        AppController.setPlayerChannel(Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
        PlayerData player = AppController.getPlayer();

        RoomData room = RoomFactory.makeCreateRoom(title, player);

        //creates an instance of callback
        RoomRequestCallback callback = new RoomRequestCallback(room, player);

        //instance of the waiting room
        WaitingRoomController waitingRoom = new WaitingRoomController();

        //will execute after someone has joined the room
        callback.setResolved((roomData) -> {
            //will alert the creator that someone has joined the room
            Platform.runLater(() -> {
                waitingRoom.getPlayerName().setText("");
                waitingRoom.getPlayerName().setText(roomData.getPlayer2().getPlayerUserName() + " has joined!");
                waitingRoom.getWaitingPageMessage().setText("");
                waitingRoom.getJoinMessage().setText("Game will start shortly...");
            });

            //instance of a timer
            Timer timer = new Timer();

            //timer task that will execute after some wait
            TimerTask playerCreate = new TimerTask() {
                @Override
                public void run() {
                    matchWorker(roomData, player);
                    //freeing up the thread
                    timer.cancel();
                }
            };
            //schedule the players to wait 8 seconds after join
            timer.schedule(playerCreate, 3000L);
        });

        //will run if there was problem creating a room
        callback.setRejected((roomData) -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("There was a problem creating the room... Please try again later.");
                alert.show();
            });
        });
        GlobalAPIManager.getInstance().swapListener(callback, Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());

        //setting the scene for waiting room
        stage.setScene(AppController.getScenes().get(SceneName.WAITING_PAGE).getScene(waitingRoom, false));
        waitingRoom.getJoinMessage().setText("");
        waitingRoom.getWaitingPageMessage().setText("Waiting for another player to join your lobby");
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Kord Boniadi
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        stage.setScene(AppController.getScenes().get(SceneName.Main).getScene(false));
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