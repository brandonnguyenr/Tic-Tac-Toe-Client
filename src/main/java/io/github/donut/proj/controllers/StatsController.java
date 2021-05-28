package io.github.donut.proj.controllers;

import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.RoomResponse;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.callbacks.RoomHistoryCallback;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class handles the total stats for the user
 * @author Utsav Parajuli
 */
public class StatsController extends AbstractController implements ISubject {
    @FXML
    private Label wins;
    @FXML
    private Label ties;
    @FXML
    private Label losses;
    @FXML
    private Label winPercentageTitle;

    @FXML
    private Label total;

    @FXML
    private Label wins1;
    @FXML
    private Label ties1;
    @FXML
    private Label losses1;

    @FXML
    private Label winPercentage;

    @FXML
    private ImageView backButton;

    private List<RoomResponse> rooms;

    /**
     * Initializes the stats page
     */
    @FXML
    public void initialize() {

        GlobalAPIManager.getInstance().swapListener(new RoomHistoryCallback(this::setRoomsListAsync),
                Channels.REQUEST + Channels.GET_ROOMS_DATA.toString(),
                Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());

        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);
        /*========================Action Events END===========================*/
    }

    public void setRoomsListAsync(List<RoomResponse> rooms) {
        Platform.runLater(() -> {
            getStats(rooms);
        });
    }

    public void getStats(List<RoomResponse> rooms) {
        this.rooms = rooms;

        int winCount = 0;
        int lossCount = 0;
        int tieCount = 0;

        for(RoomResponse room : rooms) {
            switch(room.getResult()) {
                case "WIN":
                    winCount++;
                    break;
                case "TIE":
                    tieCount++;
                    break;
                default:
                    lossCount++;
            }
        }

        wins.setText("" + winCount);
        ties.setText("" + tieCount);
        losses.setText("" + lossCount);

        int totalGames = winCount + tieCount + lossCount;

        System.out.println(totalGames);
        total.setText("" + totalGames);

        if (totalGames == 0) {
            winPercentage.setText("N/A");
        } else {
            double percentage = (winCount + (tieCount * 0.5 )) / totalGames;
            System.out.println(percentage);
            winPercentage.setText("" + String.format("%.2f", percentage));
        }
    }
    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Utsav Parajuli
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        stage.setScene(AppController.getScenes().get(SceneName.PORTAL_PAGE).getScene(false));
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


    //back button idle image
    private final Image backButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow.png")
    ));

    //back button hover image
    private final Image backButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow_hover.png")
    ));

}
