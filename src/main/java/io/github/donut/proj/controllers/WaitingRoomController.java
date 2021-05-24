package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class WaitingRoomController extends AbstractController implements ISubject {

    @FXML

    private Label   waitingPageTitle;

    @FXML
    private Label waitingPageMessage;

    @Setter
    private static String waitingMsg = "Waiting for another player to join your lobby";

//    @FXML
//    private ProgressIndicator progressIndicator;

    @FXML
    private Label playerName;

    @FXML
    private ImageView cancelButton;

    @FXML
    private ImageView loadingGif;

    @Setter
    private static String player;

    /**
     * Initialize the class.
     * @author Utsav Parajuli
     */
    @FXML
    public void initialize() {
        //empty

        playerName.setText(player);
        waitingPageMessage.setText(waitingMsg);
        cancelButton.setOnMouseClicked(this::onCancelClick);
//        progressIndicator.setVisible(true);
//        progressIndicator.indeterminateProperty();
    }

    private void onCancelClick(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.LOBBY_PAGE).getScene(false));
    }

    //delete account button hover
    private final Image countDown = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/counter.gif")
    ));
}
