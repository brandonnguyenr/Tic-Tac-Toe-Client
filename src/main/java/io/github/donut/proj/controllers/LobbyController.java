package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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
    private BorderPane multiplayerPage;

    @FXML
    public ImageView createLobbyButton;

    @FXML
    public ScrollPane lobbyPage;

    private TableView<LobbyData> lobbyTableView;

    @FXML
    private ObservableList<LobbyData> tvOList;

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

    public static class LobbyData {
        private String lobbyName;
        private String playersInvolved;
        private Integer numOfPlayers;

        public LobbyData() {
            this.lobbyName = "My Lobby";
            this.playersInvolved = "Player1 - Player2";
            this.numOfPlayers = 1;
        }

        public LobbyData(String lobbyName, String player1Name, String player2Name, Integer numOfPlayers) {
            this.lobbyName = lobbyName;
            this.playersInvolved = player1Name + " - " + player2Name;
            this.numOfPlayers = numOfPlayers;
        }

        public String getLobbyName() {
            return lobbyName;
        }

        public String getPlayersInvolved() {
            return playersInvolved;
        }

        public Integer getNumOfPlayers() {
            return numOfPlayers;
        }
    }

    /**
     * Initializes a LobbyController object after its root element has been
     * completely processed.
     *
     * @author Utsav Parajuli
     */
    @FXML
    public void initialize() {

        TableColumn<LobbyData, String> lobbyNameCol = new TableColumn<>("Lobby");
        lobbyNameCol.setReorderable(false);
        lobbyNameCol.setResizable(false);
        lobbyNameCol.setSortable(false);
        lobbyNameCol.setPrefWidth(150);
        lobbyNameCol.setCellValueFactory(new PropertyValueFactory<>("lobbyName"));

        TableColumn<LobbyData, String> playersCol = new TableColumn<>("Players");
        playersCol.setReorderable(false);
        playersCol.setResizable(false);
        playersCol.setSortable(false);
        playersCol.setPrefWidth(200);
        playersCol.setCellValueFactory(new PropertyValueFactory<>("playersInvolved"));

        TableColumn<LobbyData, Integer> playerCountCol = new TableColumn<>("Count");
        playerCountCol.setReorderable(false);
        playerCountCol.setResizable(false);
        playerCountCol.setSortable(false);
        playerCountCol.setPrefWidth(80);
        playerCountCol.setCellValueFactory(new PropertyValueFactory<>("numOfPlayers"));

        lobbyTableView = new TableView<>();

        fillTableWithObservableData();

        lobbyTableView.setItems(tvOList);

        lobbyTableView.getColumns().add(lobbyNameCol);
        lobbyTableView.getColumns().add(playersCol);
        lobbyTableView.getColumns().add(playerCountCol);

        multiplayerPage.setCenter(lobbyTableView);

        lobbyTableView.setPadding(new Insets(0, 116, 30, 116));
        lobbyTableView.setSelectionModel(null);
        lobbyTableView.setId("lobbyTableView");

        addJoinButtonToTable();


        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);

        createLobbyButton.setOnMouseClicked(this::onCreateLobbyButtonClick);
        createLobbyButton.setOnMouseEntered(this::onCreateLobbyButtonEnter);
        createLobbyButton.setOnMouseExited(this::onCreateLobbyButtonExit);
        /*========================Action Events END=========================*/
    }

    private void fillTableWithObservableData() {
        tvOList = FXCollections.observableArrayList();

        tvOList.addAll(new LobbyData(),
                new LobbyData(),
                new LobbyData(),
//                new LobbyData(),
//                new LobbyData(),
//                new LobbyData(),
//                new LobbyData(),
//                new LobbyData(),
//                new LobbyData(),
//                new LobbyData(),
                new LobbyData("The Cool Lobby", "joe", "test", 2),
                new LobbyData("The Cool Lobby", "test", "test", 2),
                new LobbyData("The Cool Lobby", "test", "test", 2),
                new LobbyData("The Cool Lobby", "test", "test", 2));
    }

    private void addJoinButtonToTable() {
        TableColumn<LobbyData, Void> joinCol = new TableColumn("Join Game");
        joinCol.setResizable(false);
        joinCol.setReorderable(false);
        joinCol.setSortable(false);
        joinCol.setPrefWidth(120);

        Callback<TableColumn<LobbyData, Void>, TableCell<LobbyData, Void>> cellFactory =
                new Callback<TableColumn<LobbyData, Void>, TableCell<LobbyData, Void>>() {

                    @Override
                    public TableCell<LobbyData, Void> call(final TableColumn<LobbyData, Void> param) {
                        final TableCell<LobbyData, Void> cell = new TableCell<LobbyData, Void>() {

                            private final Button btn = new Button("Join");
                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    LobbyData data = getTableView().getItems().get(getIndex());
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