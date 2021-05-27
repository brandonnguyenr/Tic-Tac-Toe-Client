package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private Label wins1;
    @FXML
    private Label ties1;
    @FXML
    private Label losses1;

    @FXML
    private ImageView backButton;

    /**
     * Initializes the stats page
     */
    @FXML
    public void initialize() {
        wins.setText  ("20");
        ties.setText  ("1");
        losses.setText("0");

        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);
        /*========================Action Events END===========================*/
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
