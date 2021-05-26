package io.github.donut.proj.controllers;

import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MoveHistoryController  extends AbstractController implements Initializable, ISubject {

    @FXML
    private ImageView backButton;

//    private final BoardUI boardUI;

    private int id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);
    }

    public MoveHistoryController() {
    }

    public MoveHistoryController(int id) {

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
        stage.setScene(AppController.getScenes().get(SceneName.HISTORY_PAGE).getScene(false));
    }
}
