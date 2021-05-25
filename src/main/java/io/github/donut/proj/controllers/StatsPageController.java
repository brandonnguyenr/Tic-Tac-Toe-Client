package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.donut.proj.controllers.GameController;
import io.github.donut.proj.callbacks.AuthorizationCallback;
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
import lombok.Setter;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class StatsPageController extends AbstractController implements Initializable, ISubject {

    @FXML
    private Label statsPageTitle;
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
    private BorderPane statsPage;

    @FXML
    private ImageView backButton;

    public void initialize(URL location, ResourceBundle resources) {

        statsPageTitle.setText       ("MY STATS");
        winsLabel.setText            ("WINS");
        lossesLabel.setText          ("LOSSES");
        tiesLabel.setText            ("TIES");
        winLossRatioLabel.setText    ("WIN PERCENTAGE");
        totalGamesPlayedLabel.setText("TOTAL GAMES");

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
 