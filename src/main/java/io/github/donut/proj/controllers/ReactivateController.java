package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.UpdateData;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.callbacks.UpdatesCallback;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Controller class for the pop up window prompted when the account needs reactivation
 * @author Utsav Parajuli
 */
public class ReactivateController extends AbstractController implements ISubject {

    @FXML
    private BorderPane popUpPage;

    @FXML
    private Label reactivateLabel;

    @FXML
    private ImageView reactivateButton;

    @Setter
    @Getter
    private UpdatesCallback updateHandler;                                 //callback class

    @Setter
    @Getter
    private Stage reactivatePopUp;                                        //the stage


    /**
     * Will initialize the class
     * @author Utsav Parajuli
     */
    @FXML
    public void initialize() {
        reactivateLabel.setText("Your account needs to be re-activated!");
        reactivateButton.setOnMouseClicked(this::onReactivateClick);
        reactivateButton.setOnMouseEntered(this::onReactivateEnter);
        reactivateButton.setOnMouseExited(this::onReactivateExit);

    }

    /**
     * Event handler for reactivate button idle
     *
     * @param mouseEvent mouse event
     * @author Utsav Parajuli
     */
    private void onReactivateExit(MouseEvent mouseEvent) {
        reactivateButton.setImage(reactivateButtonIdle);
    }

    /**
     * Event handler for reactivate button hover
     *
     * @param mouseEvent mouse event
     * @author Utsav Parajuli
     */
    private void onReactivateEnter(MouseEvent mouseEvent) {
        reactivateButton.setImage(reactivateButtonHover);
    }

    /**
     * Event handler for reactivate button click
     *
     * @param mouseEvent mouse event
     */
    private void onReactivateClick(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();

        GlobalAPIManager.getInstance().swapListener(updateHandler, Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
        GlobalAPIManager.getInstance().send(new UpdateData( AppController.getUserName(),null, null,
                null, null, null, "false"), Channels.UPDATE_DELETE.toString());
    }

    //create account button idle
    private final Image reactivateButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/reactivate_button.png")
    ));

    //create account button hover
    private final Image reactivateButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/reactivate_button_hover.png")
    ));
}