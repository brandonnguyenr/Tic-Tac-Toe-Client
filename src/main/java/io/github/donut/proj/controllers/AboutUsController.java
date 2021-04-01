package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.Logger;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class that handles the About Us page UI
 *
 * @author Utsav Parajuli
 * @version 0.2
 */
public class AboutUsController implements Initializable, ISubject {

    @FXML
    private Label aboutUsTitle;

    @FXML
    private ImageView backButton;

    @FXML
    private Hyperlink githubLink;

    @FXML
    private Label contributors;

    @FXML
    private Label copyright;

    @FXML
    private Label info;

    private final Image backButtonIdle = new Image(Objects.requireNonNull(
            getClass().
            getClassLoader().
            getResourceAsStream("io/github/donut/proj/images/common/back_arrow.png")
    ));
    private final Image backButtonHover = new Image(Objects.requireNonNull(
            getClass().
            getClassLoader().
            getResourceAsStream("io/github/donut/proj/images/common/back_arrow_hover.png")
    ));

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the resource is unknown
     * @author Utsav Parajuli
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //about us page
        aboutUsTitle.setText("TEAM DONUT CS4B");

        //application info
        info.setText("""
                Welcome to our Tic Tac Toe Board Game. You will
                be able to play local multiplayer with your friends,
                as well as battle against a computer A.I. in two 
                difficulty modes. We will be adding new features
                soon. Stay tuned.
                            Have fun and happy playing!!""");

        //url link to the github repository
        githubLink.setText("GitHub Repository");
        githubLink.setOnAction(event -> {
            try {
                if (SystemUtils.IS_OS_LINUX) {
                    if (Runtime.getRuntime().exec(new String[] { "which", "xdg-open" }).getInputStream().read() != -1) {
                        Runtime.getRuntime().exec(new String[] { "xdg-open", "https://github.com/kboniadi/Tic_Tac_Toe" });
                    } else {
                        Logger.log("xdg-open not supported!");
                    }
                } else if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("https://github.com/kboniadi/Tic_Tac_Toe"));
                }
            } catch (Exception e) {
                Logger.log(e);
            }
        });
        contributors.setText( "Kord Boniadi, Brandon Nguyen, Grant Goldsworth, Utsav Parajuli, Joey Campbell, Christopher Bassar");
        copyright.setText("Copyright \u00a9 2021 Donut");
        copyright.setPadding(new Insets(0, 0, 20, 0));
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Kord Boniadi
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventManager.removeAllObserver(this);
        EventSounds.getInstance().playButtonSound1();
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Donut Tic Tac Toe");
        window.setScene(((AppController) window.getUserData()).mainScene);
        window.setResizable(false);
//        window.show();
    }

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
}

