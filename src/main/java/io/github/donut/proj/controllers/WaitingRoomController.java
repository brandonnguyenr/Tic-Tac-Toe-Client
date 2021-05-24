package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Getter;

/**
 * Controller class for the waiting room for multiplayer games
 */
@Getter
public class WaitingRoomController extends AbstractController implements ISubject {

    @FXML
    private Label   waitingPageTitle;
    @FXML
    private Label   waitingPageMessage;
    @FXML
    private Label   joinMessage;
    @FXML
    private Label   playerName;
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
    }

    //TODO: Maybe add a cancel button?
}
