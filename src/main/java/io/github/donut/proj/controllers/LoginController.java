package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable, ISubject {

    public BorderPane loginPage;

    /*
     *  // By pass the need for this:
     *  FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
     *  Parent root = loader.load();
     *
     *  Controller myController = loader.getController();
     *********************************************************************/
    private static LoginController instance;

    public Label loginTitle;
    public Label usernameLabel;
    public TextField usernameEntry;
    public Label passwordLabel;
    public PasswordField passwordEntry;
    public ImageView loginButton;
    public ImageView createAccountButton;
    public ImageView guest;
    public Label errorMessage;


    private String username;
    private String password;
    /**
     * @return instance of Main screen controller
     */
    public static LoginController getInstance() {
        return instance;
    }

    /**
     * Constructor
     */
    public LoginController() {
        instance = this;
    }
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginTitle.setText("WELCOME BACK!! Please Login");
        usernameLabel.setText("Username: ");
        passwordLabel.setText("Password: ");
    }

    public void onLoginClicked (MouseEvent actionEvent) throws IOException {

        username = usernameEntry.getText();
        password = passwordEntry.getText();

        if (username.equals("admin") && password.equals("donut")) {
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("menuPage.fxml"));

            EventManager.register(MainController.getInstance(), (AppController) window.getUserData());

            Scene mainScene = new Scene(root);
            mainScene.getStylesheets().add((getClass().getResource("styles.css")).toExternalForm());

            ((AppController) window.getUserData()).mainScene = mainScene;

            // set the title of the stage
            window.setTitle("Donut Tic Tac Toe");
            window.setScene(mainScene);
            window.setResizable(false);
        }
        else {
            usernameEntry.setStyle("-fx-border-color: red");
            passwordEntry.setStyle("-fx-border-color: red");// or false to unset it
            errorMessage.setText("Incorrect username/password. Try again!");

        }

    }

    public void onGuestLoginClicked(MouseEvent mouseEvent) {
    }

    public void onCreateAccountClicked(MouseEvent mouseEvent) {
    }

    public void onEnterPressed(KeyEvent keyEvent) {
    }
}
