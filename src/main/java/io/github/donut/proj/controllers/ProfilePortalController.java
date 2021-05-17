package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfilePortalController extends AbstractController implements Initializable, ISubject {

    @FXML
    private ImageView historyButton;

    @FXML
    private ImageView statsButton;

    @FXML
    private ImageView profileSettingsButton;

    @FXML
    private ImageView backButton;

    private final String theme = "theme_2";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onHistoryButtonClick(/*MouseEvent mouseEvent*/) {
//        EventSounds.getInstance().playButtonSound4();
//        PlayerHistoryController historyController = new PlayerHistoryController();
//        EventManager.notify(this, historyController);
    }

    /**
     * Sets the image on the history button to its hover image
     * @author Joey Campbell
     */
    public void onHistoryButtonHover(/*MouseEvent mouseEvent*/) {
        historyButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/history_button_hover.png"))
        ));
    }

    /**
     * Sets the image on the history button to its regular image
     * @author Joey Campbell
     */
    public void onHistoryButtonExit(/*MouseEvent mouseEvent*/) {
        historyButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/history_button.png"))
        ));
    }

    public void onStatsButtonClick(/*MouseEvent mouseEvent*/) {
        // TODO - Handle stats here
    }

    /**
     * Sets the image on the stats button to its hover image
     * @author Joey Campbell
     */
    public void onStatsButtonHover(/*MouseEvent mouseEvent*/) {
        statsButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/stats_button_hover.png"))
        ));
    }

    /**
     * Sets the image on the stats button to its regular image
     * @author Joey Campbell
     */
    public void onStatsButtonExit(/*MouseEvent mouseEvent*/) {
        statsButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/stats_button.png"))
        ));
    }

    public void onPlayerSettingsButtonClick(/*MouseEvent mouseEvent*/) {
        // TODO - Handle player settings here
    }

    /**
     * Sets the image on the player settings button to its hover image
     * @author Joey Campbell
     */
    public void onPlayerSettingsButtonHover(/*MouseEvent mouseEvent*/) {
        profileSettingsButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/profile_settings_button_hover.png"))
        ));
    }

    /**
     * Sets the image on the player settings button to its regular image
     * @author Joey Campbell
     */
    public void onPlayerSettingsButtonExit(/*MouseEvent mouseEvent*/) {
        profileSettingsButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/profile_settings_button.png"))
        ));
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
    public void onBackButtonEnter() {
        backButton.setImage(backButtonHover);
    }

    /**
     * Event handler for back button idle effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonExit() {
        backButton.setImage(backButtonIdle);
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
}
