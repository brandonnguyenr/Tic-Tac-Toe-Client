package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlayerHistoryController extends AbstractController implements Initializable, ISubject {
    @FXML
    private ImageView backButton;

    @FXML
    private BorderPane playerHistoryPage;
    // =================== TABLE RELATED ===================
//    @FXML
    private TableView<RoomHistoryData> playerHistoryTable;

    @FXML
    private ObservableList<RoomHistoryData> tvOList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);

        buildTable();
    }

    private void buildTable() {
        TableColumn<RoomHistoryData, String> roomIdCol      = new TableColumn<>("ID");
        roomIdCol.setReorderable(false);
        roomIdCol.setResizable(false);
        roomIdCol.setSortable(false);
        roomIdCol.setPrefWidth(50);
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));

        TableColumn<RoomHistoryData, String> winLossTieCol  = new TableColumn<>("W L T");
        winLossTieCol.setReorderable(false);
        winLossTieCol.setResizable(false);
        winLossTieCol.setSortable(false);
        winLossTieCol.setPrefWidth(50);
        winLossTieCol.setCellValueFactory(new PropertyValueFactory<>("winLossTie"));

        TableColumn<RoomHistoryData, String> playersCol     = new TableColumn<>("Players");
        playersCol.setReorderable(false);
        playersCol.setResizable(false);
        playersCol.setSortable(false);
        playersCol.setPrefWidth(200);
        playersCol.setCellValueFactory(new PropertyValueFactory<>("playersInvolved"));

        TableColumn<RoomHistoryData, String> startTimeCol   = new TableColumn<>("Start Time");
        startTimeCol.setReorderable(false);
        startTimeCol.setResizable(false);
        startTimeCol.setSortable(false);
        startTimeCol.setPrefWidth(75);
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<RoomHistoryData, String> endTimeCol     = new TableColumn<>("End Time");
        endTimeCol.setReorderable(false);
        endTimeCol.setResizable(false);
        endTimeCol.setSortable(false);
        endTimeCol.setPrefWidth(75);
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<RoomHistoryData, String> roomCreatorCol = new TableColumn<>("Room Creator");
        roomCreatorCol.setReorderable(false);
        roomCreatorCol.setResizable(false);
        roomCreatorCol.setSortable(false);
        roomCreatorCol.setPrefWidth(100);
        roomCreatorCol.setCellValueFactory(new PropertyValueFactory<>("startingPlayer"));

        playerHistoryTable = new TableView<>();

        fillTableWithObservableData();

        playerHistoryTable.setItems(tvOList);

        //playerHistoryTable.getColumns().addAll(roomIdCol, winLossTieCol, playersCol, startTimeCol, endTimeCol, roomCreatorCol);

        playerHistoryTable.setPadding(new Insets(0, 65.5, 30, 65.5));
        playerHistoryTable.setSelectionModel(null);

        playerHistoryTable.getColumns().add(roomIdCol);
        playerHistoryTable.getColumns().add(winLossTieCol);
        playerHistoryTable.getColumns().add(playersCol);
        playerHistoryTable.getColumns().add(startTimeCol);
        playerHistoryTable.getColumns().add(endTimeCol);
        playerHistoryTable.getColumns().add(roomCreatorCol);

        playerHistoryPage.setCenter(playerHistoryTable);

//        playerHistoryTable.setStyle("-fx-font-size: 12; -fx-font-weight: bold");

        addMovesButtonToTable();
    }

    @Getter
    @Setter
    @ToString
    public static class RoomHistoryData {
        private String roomId;
        private String playerId;
        private String winLossTie;
        private String playersInvolved;
        private String startTime;
        private String endTime;
        private String startingPlayer;
        private String[] moves;
        private Image movesImage;


        public RoomHistoryData()
        {
            this.roomId = "10";
            this.winLossTie = "TIE";
            this.playersInvolved = "JOEY" + " - " + "UTS";
            this.startTime = "8:30 AM";
            this.endTime = "8:35 AM";
            this.startingPlayer = "JOEY";
            this.moves = new String[] {"(1,1),(2,2),(3,3)"};
        }

        public RoomHistoryData(String roomId, String winnerId, String player1Id, String player1Name, String player2Id,
                               String player2Name, String startTime, String endTime, String startingPlayer,
                               String[] moves) {
            this.roomId = roomId;
            this.winLossTie = "TIE"; // TODO - determine if the client's result for the game is a win or not
            this.playersInvolved = player1Name + " - " + player2Name;
            this.startTime = startTime; // TODO - convert time from millis to actual time
            this.endTime = endTime; // TODO - convert time from millis to actual time
            this.startingPlayer = startingPlayer;
            this.moves = new String[] {"(1,1),(2,2),(3,3)"}; // TODO - get moves as an actual array (have to query)
        }


        public String getRoomId() {
            return roomId;
        }

        public String getPlayerId() {
            return playerId;
        }

        public String getWinLossTie() {
            return winLossTie;
        }

        public String getPlayersInvolved() {
            return playersInvolved;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getStartingPlayer() {
            return startingPlayer;
        }

        public String[] getMoves() {
            return moves;
        }
    }

    private void fillTableWithObservableData() {
        tvOList = FXCollections.observableArrayList();

        tvOList.addAll(new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData(),
                new RoomHistoryData("test", "test", "test", "test", "test", "test", "test", "test", "test", new String[] {"test"}),
                new RoomHistoryData("test", "test", "test", "test", "test", "test", "test", "test", "test", new String[] {"test"}),
                new RoomHistoryData("test", "test", "test", "test", "test", "test", "test", "test", "test", new String[] {"test"}),
                new RoomHistoryData("test", "test", "test", "test", "test", "test", "test", "test", "test", new String[] {"test"}));
    }

    private void addMovesButtonToTable() {
        TableColumn<RoomHistoryData, Void> movesCol = new TableColumn("Moves");
        movesCol.setResizable(false);
        movesCol.setReorderable(false);
        movesCol.setSortable(false);
        movesCol.setPrefWidth(100);
        Callback<TableColumn<RoomHistoryData, Void>, TableCell<RoomHistoryData, Void>> cellFactory =
                new Callback<TableColumn<RoomHistoryData, Void>, TableCell<RoomHistoryData, Void>>() {

            @Override
            public TableCell<RoomHistoryData, Void> call(final TableColumn<RoomHistoryData, Void> param) {
                final TableCell<RoomHistoryData, Void> cell = new TableCell<RoomHistoryData, Void>() {

                    private final Button btn = new Button("See Moves");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            RoomHistoryData data = getTableView().getItems().get(getIndex());
                            System.out.println(Arrays.toString(data.getMoves()));
                        });

                        btn.setPrefWidth(100);

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

        movesCol.setCellFactory(cellFactory);
        playerHistoryTable.getColumns().add(movesCol);
    }


    // ===================== BACK BUTTON RELATED =====================
    /**
     * Event handler for back button idle effect
     * @author Kord Boniadi
     */
    private final Image backButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow.png")
    ));

    /**
     * Event handler for back button hover effect
     * @author Kord Boniadi
     */
    private final Image backButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow_hover.png")
    ));

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
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Joey Campbell
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        stage.setScene(AppController.getScenes().get(SceneName.PORTAL_PAGE).getScene(false));
    }
}
