package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

public class WaitingRoomController extends AbstractController implements ISubject {

    @FXML
    private Label   waitingPageTitle;

    @FXML
    private Label waitingPageMessage;

//    @FXML
//    private ProgressIndicator progressIndicator;

    @FXML
    private ImageView cancelButton;

    @FXML
    private ImageView loadingGif;

    /**
     * Initialize the class.
     * @author Utsav Parajuli
     */
    @FXML
    public void initialize() {
        //empty

        cancelButton.setOnMouseClicked(this::onCancelClick);
//        progressIndicator.setVisible(true);
//        progressIndicator.indeterminateProperty();
    }

    private void onCancelClick(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.LOBBY_PAGE).getScene(false));
    }
}
