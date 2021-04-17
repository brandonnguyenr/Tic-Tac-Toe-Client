package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.RestrictiveTextField;
import javafx.css.PseudoClass;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
    public ImageView guestButton;
    public Label errorMessage;
    public ImageView resetButton;
    public Label guestLabel;


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

    private final Image loginButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/login_button.png")
    ));

    private final Image loginButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/login_button_hover.png")
    ));

    private final Image createAccountButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/create_account_button.png")
    ));

    private final Image createAccountButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/create_account_button_hover.png")
    ));

    private final Image resetButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/reset_button.png")
    ));

    private final Image resetButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/reset_button_hover.png")
    ));

    private final Image guestButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/account_button.png")
    ));

    private final Image guestButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/account_button_hover.png")
    ));
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
        guestLabel.setText("Press to Login as Guest");
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
        else if (usernameEntry.getText().trim().isEmpty() && passwordEntry.getText().trim().isEmpty()){
            usernameEntry.setStyle("-fx-border-color: red");
            passwordEntry.setStyle("-fx-border-color: red");// or false to unset it
            errorMessage.setText("Incorrect username/password. Try again!");
        }

        else {
            usernameEntry.setStyle("-fx-border-color: red");
            passwordEntry.setStyle("-fx-border-color: red");// or false to unset it
            errorMessage.setText("Incorrect username/password. Try again!");
        }

    }

    public void onEnterPressed(KeyEvent keyEvent) throws IOException {

        if(keyEvent.getCode() == KeyCode.ENTER) {
            username = usernameEntry.getText();
            password = passwordEntry.getText();

            if (username.equals("admin") && password.equals("donut")) {
                Stage window = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();

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
            else if (usernameEntry.getText().trim().isEmpty() && passwordEntry.getText().trim().isEmpty()) {
                usernameEntry.setStyle("-fx-border-color: red");
                passwordEntry.setStyle("-fx-border-color: red");// or false to unset it
                errorMessage.setText("Incorrect username/password. Try again!");
            }
            else {
                usernameEntry.setStyle("-fx-border-color: red");
                passwordEntry.setStyle("-fx-border-color: red");// or false to unset it
                errorMessage.setText("Incorrect username/password. Try again!");
            }
        }
    }

    public void onGuestLoginClicked(MouseEvent actionEvent) throws IOException {
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

    public void onCreateAccountClicked(MouseEvent actionEvent) throws IOException {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("createAccountPage.fxml"));

        EventManager.register(CreateAccountController.getInstance(), (AppController) window.getUserData());

        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add((getClass().getResource("styles.css")).toExternalForm());

        ((AppController) window.getUserData()).mainScene = mainScene;

        // set the title of the stage
        window.setTitle("Donut Tic Tac Toe");
        window.setScene(mainScene);
        window.setResizable(false);
    }

    public void onResetClicked(MouseEvent mouseEvent) {
        errorMessage.setText("");
        usernameEntry.setStyle("-fx-border-color: khaki");
        usernameEntry.setText("");
        passwordEntry.setStyle("-fx-border-color: khaki");
        passwordEntry.setText("");
    }

    public void onLoginButtonEnter(MouseEvent mouseEvent) {
        loginButton.setImage(loginButtonHover);
    }

    public void onLoginButtonExit(MouseEvent mouseEvent) {
        loginButton.setImage(loginButtonIdle);
    }

    public void onCreateAccountEnter(MouseEvent mouseEvent) {
        createAccountButton.setImage(createAccountButtonHover);
    }

    public void onCreateAccountExit(MouseEvent mouseEvent) {
        createAccountButton.setImage(createAccountButtonIdle);
    }

    public void onResetEnter(MouseEvent mouseEvent) {
        resetButton.setImage(resetButtonHover);
    }

    public void onResetExit(MouseEvent mouseEvent) {
        resetButton.setImage(resetButtonIdle);
    }

    public void onGuestButtonEnter(MouseEvent mouseEvent) {
        guestButton.setImage(guestButtonHover);
    }

    public void onGuestButtonExit(MouseEvent mouseEvent) {
        guestButton.setImage(guestButtonIdle);
    }
}
