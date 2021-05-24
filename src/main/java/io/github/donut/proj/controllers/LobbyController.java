package io.github.donut.proj.controllers;

import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.PlayerData;
import io.github.coreutils.proj.messages.RoomData;
import io.github.coreutils.proj.messages.RoomFactory;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.callbacks.RoomListCallback;
import io.github.donut.proj.callbacks.RoomRequestCallback;
import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        TableColumn<RoomData, String> playersCol = new TableColumn<>("Players");
        playersCol.setReorderable(false);
        playersCol.setResizable(false);
        playersCol.setSortable(false);
        playersCol.setPrefWidth(200);
        playersCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));

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

        GlobalAPIManager.getInstance().swapListener(new RoomListCallback(this::setLobbyList),
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
                new Callback<TableColumn<RoomData, Void>, TableCell<RoomData, Void>>() {

                    @Override
                    public TableCell<RoomData, Void> call(final TableColumn<RoomData, Void> param) {
                        final TableCell<RoomData, Void> cell = new TableCell<RoomData, Void>() {

                            private final Button btn = new Button("Join");
                            {
                                btn.setOnAction((ActionEvent event) -> {
//                                    RoomData data = getTableView().getItems().get(getIndex());
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

//                  "io/github/donut/proj/images/icons/join_game.png"

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

    private void joinRoomWorker(RoomData data) {
        PlayerData player = AppController.getPlayer(Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
        data.addPlayer(player);
        RoomRequestCallback callback = new RoomRequestCallback(data, player);
        callback.setResolved((event) -> {
            Platform.runLater(() -> {
                BoardPageController game = new BoardPageController(new BoardUI());
                stage.setScene(AppController.getScenes().get(SceneName.BOARD_PAGE).getScene(game));
                game.getPlayerNameLeft().setText(event.getPlayer1().getPlayerUserName());
                game.getPlayerNameRight().setText(event.getPlayer2().getPlayerUserName());
            });
        });

        callback.setRejected((event) -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("There was a problem that room... It may be full.");
                alert.show();
            });
        });
        GlobalAPIManager.getInstance().swapListener(callback, Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
        // TODO: launch waiting room page here
    }

    private void createRoomWorker(String title) {
        PlayerData player = AppController.getPlayer(Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
        RoomData room = RoomFactory.makeCreateRoom(title, player);
        RoomRequestCallback callback = new RoomRequestCallback(room, player);
        callback.setResolved((event) -> {
            Platform.runLater(() -> {
                BoardPageController game = new BoardPageController(new BoardUI());
                stage.setScene(AppController.getScenes().get(SceneName.BOARD_PAGE).getScene(game));
                game.getPlayerNameLeft().setText(event.getPlayer1().getPlayerUserName());
                game.getPlayerNameRight().setText(event.getPlayer2().getPlayerUserName());
            });
        });

        callback.setRejected((event) -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("There was a problem creating the room... Please try again later.");
                alert.show();
            });
        });
        GlobalAPIManager.getInstance().swapListener(callback, Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
        // TODO: launch waiting room page here
        stage.setScene(AppController.getScenes().get(SceneName.WAITING_PAGE).getScene(false, false));
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