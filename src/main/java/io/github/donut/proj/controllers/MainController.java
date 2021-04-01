package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Main Screen Controller class
 * @author Grant Goldsworth
 */
public class MainController implements Initializable, ISubject {
    @FXML
    public BorderPane mainMenuPane;

    @FXML
    private ImageView aboutUsRect;

    @FXML
    private ImageView singlePlayerButton;

    @FXML
    private ImageView multiPlayerButton;

    private final String theme = "theme_2";

    /*
     *  // By pass the need for this:
     *  FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
     *  Parent root = loader.load();
     *
     *  Controller myController = loader.getController();
     *********************************************************************/
    private static MainController instance;

    /**
     * @return instance of Main screen controller
     */
    public static MainController getInstance() {
        return instance;
    }

    /**
     * Constructor
     */
    public MainController() {
        instance = this;
    }
    /*end*****************************************************************/

    /**
     * Is implicitly called when the main screen is being shown, currently does nothing
     * @param url javafx specific
     * @param rb javafx specific
     * @author Kord Boniadi
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Handles mouse click event on the single player image "button".
     * @author Grant Goldsworth
     */
    public void onSinglePlayerButtonClick(/*MouseEvent mouseEvent*/) {
        EventSounds.getInstance().playButtonSound4();
        SinglePlayerController name = new SinglePlayerController();
        EventManager.notify(this, name);
    }

    /**
     * Handles mouse hover event on the single player image "button", changing
     * the button icon to the hovered icon status.
     * @author Grant Goldsworth
     */
    public void onSinglePlayerButtonHover(/*MouseEvent mouseEvent*/) {
        singlePlayerButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/singleplayer_button_hover.png"))
        ));
    }

    /**
     * Handles mouse hover exit event on the single player image "button", changing the
     * button icon to the normal status.
     * @author Grant Goldsworth
     */
    public void onSinglePlayerButtonExit(/*MouseEvent mouseEvent*/) {
        singlePlayerButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/singleplayer_button.png"))
        ));
    }

    /**
     * Handles mouse hover exit event on the multiplayer image "button".
     * Currently does nothing as MP is not implemented yet.
     * @author Grant Goldsworth
     */
    public void onMultiPlayerButtonClick(/*MouseEvent mouseEvent*/) {
        EventSounds.getInstance().playButtonSound4();
        MultiplayerController multiplayer = new MultiplayerController();
        EventManager.notify(this, multiplayer);
    }

    /**
     * Handles mouse hover exit event on the multi player image "button", changing the
     * button icon to the hover status.
     * @author Grant Goldsworth
     */
    public void onMultiPlayerButtonHover(/*MouseEvent mouseEvent*/) {
        multiPlayerButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/multiplayer_button_hover.png"))
        ));
    }


    /**
     * Handles mouse hover exit event on the multi player image "button", changing the
     * button icon to the normal status.
     * @author Grant Goldsworth
     */
    public void onMultiPlayerButtonExit(/*MouseEvent mouseEvent*/) {
        multiPlayerButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/multiplayer_button.png"))
        ));
    }

    /**
     * Handles mouse click event on the about us image "button".
     * @author Utsav Parajuli
     */
    public void onAboutButtonClicked(/*MouseEvent mouseEvent*/) {
        EventSounds.getInstance().playButtonSound4();
        AboutUsController aboutUs = new AboutUsController();
        EventManager.notify(this, aboutUs);
    }

    /**
     * Handles mouse hover event on the about us image "button", changing
     * the button icon to the hovered icon status.
     * @author Utsav Parajuli
     */
    public void onAboutRectEnter(/*MouseEvent mouseEvent*/) {
        aboutUsRect.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/about_button_hover.png"))
        ));

    }

    /**
     * Handles mouse hover exit event on the about us image "button", changing the
     * button icon to the normal status.
     * @author Utsav Parajuli
     */
    public void onAboutRectExit(/*MouseEvent mouseEvent*/) {
        aboutUsRect.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/about_button.png"))
        ));

    }
}
