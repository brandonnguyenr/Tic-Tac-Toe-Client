package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

/**
 * Main Screen Controller class
 * @author Grant Goldsworth
 */
public class MainController extends AbstractController implements ISubject {
    @FXML
    public BorderPane mainMenuPane;

    @FXML
    private ImageView aboutUsRect;

    @FXML
    private ImageView singlePlayerButton;

    @FXML
    private ImageView multiPlayerButton;

    private final String theme = "theme_2";

    @FXML
    private MenuBar playerMenuBar;

    @FXML
    private Menu playerMenu;

    @FXML
    private MenuItem playerInfo;

    @FXML
    private MenuItem signOut;

    /**
     * Is implicitly called when the main screen is being shown, currently does nothing
     * @author Kord Boniadi
     */
    @FXML
    public void initialize() {

        initializeMenu();

        if (AppController.getUserName().equals("")) {
            multiPlayerButton.setDisable(true);
            ColorAdjust colorAdjust = new ColorAdjust();
//            colorAdjust.setSaturation(-1);
            colorAdjust.setBrightness(-0.7);
            multiPlayerButton.setEffect(colorAdjust);
            playerInfo.setDisable(true);
        } else {
            multiPlayerButton.setOnMouseClicked(this::onMultiPlayerButtonClick);
            playerInfo.setOnAction(this::onProfileClicked);
            multiPlayerButton.setOnMouseEntered(this::onMultiPlayerButtonHover);
            multiPlayerButton.setOnMouseExited(this::onMultiPlayerButtonExit);
        }

        /*========================Action Events START=========================*/
        singlePlayerButton.setOnMouseClicked(this::onSinglePlayerButtonClick);
        singlePlayerButton.setOnMouseEntered(this::onSinglePlayerButtonHover);
        singlePlayerButton.setOnMouseExited(this::onSinglePlayerButtonExit);


        aboutUsRect.setOnMouseClicked(this::onAboutButtonClicked);
        aboutUsRect.setOnMouseEntered(this::onAboutRectEnter);
        aboutUsRect.setOnMouseExited(this::onAboutRectExit);

        signOut.setOnAction(this::onSignoutClicked);
        /*========================Action Events END=========================*/
    }

    /**
     * Handles mouse click event on the single player image "button".
     * @author Grant Goldsworth
     */
    public void onSinglePlayerButtonClick(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.SINGLEPLAYER_PAGE).getScene(false));
    }

    /**
     * Handles mouse hover event on the single player image "button", changing
     * the button icon to the hovered icon status.
     * @author Grant Goldsworth
     */
    public void onSinglePlayerButtonHover(MouseEvent mouseEvent) {
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
    public void onSinglePlayerButtonExit(MouseEvent mouseEvent) {
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
    public void onMultiPlayerButtonClick(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.LOBBY_PAGE).getScene(false, false));
    }

    /**
     * Handles mouse hover exit event on the multi player image "button", changing the
     * button icon to the hover status.
     * @author Grant Goldsworth
     */
    public void onMultiPlayerButtonHover(MouseEvent mouseEvent) {
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
    public void onMultiPlayerButtonExit(MouseEvent mouseEvent) {
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
    public void onAboutButtonClicked(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.ABOUT).getScene(false, false));
    }

    /**
     * Handles mouse hover event on the about us image "button", changing
     * the button icon to the hovered icon status.
     * @author Utsav Parajuli
     */
    public void onAboutRectEnter(MouseEvent mouseEvent) {
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
    public void onAboutRectExit(MouseEvent mouseEvent) {
        aboutUsRect.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/about_button.png"))
        ));

    }

    public void onProfileClicked (ActionEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.PORTAL_PAGE).getScene(false, true));
    }

    public void onSignoutClicked(ActionEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        // TODO - Add sign out code here
        //stage.setScene(AppController.getScenes().get(SceneName.LOGIN_PAGE).getScene(false, false));
        stage.setScene(AppController.getScenes().get(SceneName.LOGIN_PAGE).getScene(ControllerFactory.getController(SceneName.LOGIN_PAGE)));

        //have to clear all the cache for controllers
        AppController.clearAllScenes();

        AppController.setPlayerDefault();

    }


    /**
     * Creates player menu in the top right by loading in images
     * @author Joey Campbell
     */
    public void initializeMenu () {
        String menuImagePath = "/io/github/donut/proj/images/common/menu_bars.png";
        String profileImagePath = "/io/github/donut/proj/images/common/profile.png";
        String signOutImagePath = "/io/github/donut/proj/images/common/sign_out.png";


        ImageView playerMenuView = new ImageView(menuImagePath);
        playerMenuView.setOnMouseExited((event)->{
            playerMenu.hide();
        });
        playerMenu.setGraphic(playerMenuView);

        ImageView playerInfoView = new ImageView(profileImagePath);
        playerInfoView.setOnMouseEntered((event)->{
//            playerMenu.show();
        });

        playerInfo.setGraphic(playerInfoView);

        ImageView signOutView = new ImageView(signOutImagePath);
        signOutView.setOnMouseEntered((event)->{
//            playerMenu.show();
        });
        signOut.setGraphic(signOutView);
    }
}
