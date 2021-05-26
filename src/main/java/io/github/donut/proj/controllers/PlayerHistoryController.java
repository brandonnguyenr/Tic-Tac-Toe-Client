 package io.github.donut.proj.controllers;

import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.MoveData;
import io.github.coreutils.proj.messages.RoomData;
import io.github.coreutils.proj.messages.RoomResponse;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.callbacks.MoveHistoryCallback;
import io.github.donut.proj.callbacks.RoomHistoryCallback;
import io.github.donut.proj.common.Board;
import io.github.donut.proj.common.Token;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

 /**
  * PlayerHistoryController contains the table view object for the player to see their past games.
  * @author Joey Campbell
  * @version 0.1
  */
public class PlayerHistoryController extends AbstractController implements Initializable, ISubject {
    @FXML
    private ImageView backButton;

    @FXML
    private BorderPane playerHistoryPage;

    private TableView<RoomResponse> playerHistoryTable;

    @FXML
    private ObservableList<RoomResponse> tvOList;

    private List<MoveData> roomMoves;

    private Board board;

    /**
     * Sets all action events and loads the table view object
     * @param location location
     * @param resources resources
     * @author Joey Campbell
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);

        buildTable();
    }

    /**
     * Populates each column of the table and sets their spacing
     * @author Joey Campbell
     */
    private void buildTable() {
        TableColumn<RoomResponse, String> roomIdCol      = new TableColumn<>("ID");
        roomIdCol.setReorderable(false);
        roomIdCol.setResizable(false);
        roomIdCol.setSortable(false);
        roomIdCol.setPrefWidth(50);
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<RoomResponse, String> resultCol  = new TableColumn<>("W L T");
        resultCol.setReorderable(false);
        resultCol.setResizable(false);
        resultCol.setSortable(false);
        resultCol.setPrefWidth(50);
        resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));

        TableColumn<RoomResponse, String> player1Col     = new TableColumn<>("Me");
        player1Col.setReorderable(false);
        player1Col.setResizable(false);
        player1Col.setSortable(false);
        player1Col.setPrefWidth(100);
        player1Col.setCellValueFactory(new PropertyValueFactory<>("perspectivePlayer"));

        TableColumn<RoomResponse, String> player2Col     = new TableColumn<>("Other");
        player2Col.setReorderable(false);
        player2Col.setResizable(false);
        player2Col.setSortable(false);
        player2Col.setPrefWidth(100);
        player2Col.setCellValueFactory(new PropertyValueFactory<>("otherPlayer"));

        TableColumn<RoomResponse, String> startTimeCol   = new TableColumn<>("Start Time");
        startTimeCol.setReorderable(false);
        startTimeCol.setResizable(false);
        startTimeCol.setSortable(false);
        startTimeCol.setPrefWidth(75);
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<RoomResponse, String> endTimeCol     = new TableColumn<>("End Time");
        endTimeCol.setReorderable(false);
        endTimeCol.setResizable(false);
        endTimeCol.setSortable(false);
        endTimeCol.setPrefWidth(75);
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<RoomResponse, String> roomCreatorCol = new TableColumn<>("Room Creator");
        roomCreatorCol.setReorderable(false);
        roomCreatorCol.setResizable(false);
        roomCreatorCol.setSortable(false);
        roomCreatorCol.setPrefWidth(100);
        roomCreatorCol.setCellValueFactory(new PropertyValueFactory<>("startingPlayer"));

        playerHistoryTable = new TableView<>();

        fillTableWithObservableData();

        playerHistoryTable.setItems(tvOList);

        playerHistoryTable.setPadding(new Insets(0, 10, 30, 10));
        playerHistoryTable.setSelectionModel(null);

        playerHistoryTable.getColumns().add(roomIdCol);
        playerHistoryTable.getColumns().add(resultCol);
        playerHistoryTable.getColumns().add(player1Col);
        playerHistoryTable.getColumns().add(player2Col);
        playerHistoryTable.getColumns().add(startTimeCol);
        playerHistoryTable.getColumns().add(endTimeCol);
        playerHistoryTable.getColumns().add(roomCreatorCol);

        playerHistoryPage.setCenter(playerHistoryTable);
        addMovesButtonToTable();

        GlobalAPIManager.getInstance().swapListener(new RoomHistoryCallback(this::setLobbyListAsync),
                Channels.REQUEST + Channels.GET_ROOMS_DATA.toString(),
                Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
    }

    public void setLobbyListAsync(List<RoomResponse> rooms) {
        Platform.runLater(() -> {
            setLobbyList(rooms);
        });
    }

    public void setLobbyList(List<RoomResponse> rooms) {
        ObservableList<RoomResponse> list = FXCollections.observableArrayList(rooms);
        playerHistoryTable.setItems(list);
    }

    /**
     * Loads the observable array list with RoomHistoryData objects
     * @author Joey Campbell
     */
    private void fillTableWithObservableData() {
        tvOList = FXCollections.observableArrayList();
    }

    public void createBoardObject(List<MoveData> roomMoves) {
        this.roomMoves = roomMoves;
        this.board = new Board();

        String id = roomMoves.get(0).getPlayerID();

        Token x = Token.X;
        Token o = Token.O;

        for (MoveData m : roomMoves) {
            board.updateToken(m.getX(), m.getY(), (m.getPlayerID().equals(id)) ? x : o );
        }

        stage.setScene(AppController.getScenes().get(SceneName.MOVE_HISTORY_PAGE).getScene(new MoveHistoryController(board), false));
    }

//    private void showMoves() {
//        StringBuilder movesSB = new StringBuilder();
//
//        DateFormat df = new SimpleDateFormat();
//        Date tempDate;
//
//        for (MoveData m : roomMoves) {
//            tempDate = new Date(m.getTime());
//            movesSB.append("[" + m.getX() + ", " + m.getY() + "] -> " + df.format(tempDate) + "\n");
//        }
////
////        System.out.println(movesSB);
////
////        Dialog<String> dialog = new Dialog<String>();
//////        dialog.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
//////        dialog.getDialogPane().getStyleClass().add("myDialog");
////
////        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
////
//////        dialog.getDialogPane().getButtonTypes().add(closeButton);
////        dialog.getDialogPane().getButtonTypes().add(closeButton);
////
////        dialog.setContentText(movesSB.toString());
////
////        dialog.show();
//
////        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
////        alert.setTitle("Confirmation Dialog");
////        alert.setHeaderText("This is a Custom Confirmation Dialog");
////        alert.setContentText(movesSB.toString());
////        DialogPane dialogPane = alert.getDialogPane();
////        dialogPane.getStylesheets().add(
////                getClass().getResource("styles.css").toExternalForm());
////        dialogPane.getStyleClass().add("myDialog");
//
//    }

    /**
     * Fills the last column in the table with a clickable button to join games.
     * @author Joey Campbell
     */
    private void addMovesButtonToTable() {
        TableColumn<RoomResponse, Void> movesCol = new TableColumn<>("Moves");
        movesCol.setResizable(false);
        movesCol.setReorderable(false);
        movesCol.setSortable(false);
        movesCol.setPrefWidth(110);
        Callback<TableColumn<RoomResponse, Void>, TableCell<RoomResponse, Void>> cellFactory =
                new Callback<TableColumn<RoomResponse, Void>, TableCell<RoomResponse, Void>>() {

            @Override
            public TableCell<RoomResponse, Void> call(final TableColumn<RoomResponse, Void> param) {
                final TableCell<RoomResponse, Void> cell = new TableCell<RoomResponse, Void>() {

                    private final Button btn = new Button("See Moves");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            RoomResponse data = getTableView().getItems().get(getIndex());
                            RoomData tempData = new RoomData();
                            tempData.setRoomID(Integer.parseInt(data.getRoomID()));
                            GlobalAPIManager.getInstance().swapListener(new MoveHistoryCallback(this::setRoomMovesAsync, tempData),
                                    Channels.REQUEST + Channels.GET_MOVES_DATA.toString(),
                                    Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());

                            EventSounds.getInstance().playButtonSound4();
//                            stage.setScene(AppController.getScenes().get(SceneName.MOVE_HISTORY_PAGE).getScene(false, false));
                        });

                        btn.setPrefWidth(110);

                        btn.styleProperty().bind(Bindings.when(btn.hoverProperty())
                                .then("-fx-background-color: grey; " +
                                        "-fx-text-fill: khaki;" +
                                        "-fx-font-weight: bold")
                                .otherwise("-fx-background-color: black; " +
                                        "-fx-text-fill: khaki;" +
                                        "-fx-font-weight: bold"));
                    }

                    // HAD TO PUT THIS METHOD IN HERE
                    private void setRoomMovesAsync(List<MoveData> moveData) {
                        Platform.runLater(() -> {
                            createBoardObject(moveData);
                        });
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
