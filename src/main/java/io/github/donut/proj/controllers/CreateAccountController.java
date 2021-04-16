package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable, ISubject {

    public Label createAccountTitle;
    public ImageView backButton;
    private static CreateAccountController instance;

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

    public BorderPane createAccountPage;

    public Label firstNameLabel;
    public TextField firstNameEntry;

    public Label lastNameLabel;
    public TextField lastNameEntry;

    public Label usernameLabel;
    public TextField usernameEntry;

    public Label passwordLabel1;
    public PasswordField passwordEntry1;

    public Label passwordLabel2;
    public PasswordField passwordEntry2;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createAccountTitle.setText("CREATE YOUR ACCOUNT");

        firstNameLabel.setText("First Name: ");
        lastNameLabel.setText ("Last Name:  ");
        usernameLabel.setText ("Username:   ");
        passwordLabel1.setText("Password:   ");
        passwordLabel2.setText("Confirm:    ");

    }
    /**
     * @return instance of Main screen controller
     */
    public static CreateAccountController getInstance() {
        return instance;
    }

    /**
     * Constructor
     */
    public CreateAccountController() {
        instance = this;
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Kord Boniadi
     */
    public void onBackButtonClick(MouseEvent actionEvent) throws IOException {

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));

        EventManager.register(LoginController.getInstance(), (AppController) window.getUserData());

        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add((getClass().getResource("styles.css")).toExternalForm());

        ((AppController) window.getUserData()).mainScene = mainScene;

        // set the title of the stage
        window.setTitle("Donut Tic Tac Toe");
        window.setScene(mainScene);
        window.setResizable(false);
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

    public void onEnterPressed(KeyEvent keyEvent) {
    }
}
