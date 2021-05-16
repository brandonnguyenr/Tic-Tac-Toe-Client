package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Start page controller class
 * @author Kord Boniadi
 */
public class StartController extends AbstractController implements ISubject {
    @FXML
    public Pane startPage;

    @FXML
    private Label startKey;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @author Kord Boniadi
     */
    @FXML
    public void initialize() {
        startKey.setText("Press (Z) to begin");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.6), evt -> startKey.setVisible(false)),
                new KeyFrame(Duration.seconds(1.2), evt -> startKey.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Key press event handler
     * @param keyEvent  keyboard detection event
     * @throws IOException  fxml detection exception
     * @author Kord Boniadi
     */
    public void onKeyReleased(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode().equals(KeyCode.Z)) {
            EventSounds.getInstance().playButtonSound4();
            Scene scene = AppController.getScenes().get(SceneName.LOGIN_PAGE).getScene(ControllerFactory.getController(SceneName.LOGIN_PAGE), false);
            stage.setScene(scene);
        }
    }
}
