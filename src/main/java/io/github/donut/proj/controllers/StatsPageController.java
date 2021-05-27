package io.github.donut.proj.controllers;


import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class StatsPageController extends AbstractController implements Initializable, ISubject {

    @FXML
    private Label statsPageTitle;
    @FXML
    private Label playerPortal;
    @FXML
    private Label winsLabel;
    @FXML
    private Label lossesLabel;
    @FXML
    private Label tiesLabel;
    @FXML
    private Label winLossRatioLabel;
    @FXML
    private Label totalGamesPlayedLabel;


    @FXML
    private Label winsNumber;
    @FXML
    private Label tiesNumber;
    @FXML
    private Label lossesNumber;
    @FXML
    private Label percentage;
    @FXML
    private Label total;


    @FXML
    private BorderPane statsPage;
    @FXML
    private ImageView backButton;

//    public static class getData {
//
//        public int getWins(int wins) {
//            return wins;
//        }
//
//        public int getLosses(int losses) {
//            return losses;
//        }
//
//        public int getTies(int ties) {
//            return ties;
//        }
//
//        public int winPercentage(int wins, int losses) {
//            int percentage;
//
//            percentage = wins / losses;
//            return percentage;
//        }
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        getData connect = new getData();
//        int wins = connect.getWins();
//        int losses = connect.getLosses();
//        int ties = connect.getTies();
//        int winPercentage= connect.winPercentage();

        statsPageTitle.setText       ("MY STATS");
        winsLabel.setText            ("WINS");
        lossesLabel.setText          ("TIES");
        tiesLabel.setText            ("LOSSES");
        winLossRatioLabel.setText    ("WIN PERCENTAGE");
        totalGamesPlayedLabel.setText("TOTAL GAMES");
        playerPortal.setText         ("PLAYER PORTAL");

        winsNumber.setUserData       (1);
        lossesNumber.setUserData     (0);
        tiesNumber.setUserData       (0);
        percentage.setUserData       (100);
        total.setUserData            (1);

        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);
        /*========================Action Events END=========================*/
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
 