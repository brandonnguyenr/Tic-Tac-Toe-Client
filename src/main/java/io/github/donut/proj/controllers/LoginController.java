package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.LoginData;
import io.github.donut.proj.callbacks.AuthorizationCallback;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.sounds.EventSounds;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

/**
 * Class that handles the Login Page
 * @author  : Utsav Parajuli
 * @version : 0.2
 */
public class LoginController implements Initializable, ISubject, IObserver {

    private static LoginController instance = new LoginController();    //instance of login controller

    public BorderPane loginPage;

    public Label loginTitle;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label errorMessage;
    public Label guestLabel;

    public TextField usernameEntry;
    public ImageView loginButton;
    public ImageView createAccountButton;
    public ImageView guestButton;
    public ImageView resetButton;

    public PasswordField passwordEntry;

    private AuthorizationCallback.LoginMessage messageList;     //message list that is replied for login related stuff

    private AppController appController;                        //instance of app controller
    private MessagingAPI api;                                   //instance of api
    private Stage windowUpdate;                                 //contains the window

    /**
     * @return instance of Login screen controller
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

    //login button idle
    private final Image loginButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/login_button.png")
    ));

    //login button hover
    private final Image loginButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/login_button_hover.png")
    ));

    //create account button idle
    private final Image createAccountButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/create_account_button.png")
    ));

    //create account button hover
    private final Image createAccountButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/create_account_button_hover.png")
    ));

    //reset button idle
    private final Image resetButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/reset_button2.png")
    ));

    //reset button hover
    private final Image resetButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/reset_button_hover2.png")
    ));

    //guest button idle
    private final Image guestButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/account_button.png")
    ));

    //guest button hover
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
     *
     * @author Utsav Parajuli
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginTitle.setText   ("WELCOME BACK!! Please Login");
        usernameLabel.setText("Username: ");
        passwordLabel.setText("Password: ");
        guestLabel.setText   ("Press to Login as Guest");
    }

    /**
     * Method that will log you in when the login button is pressed
     * @param actionEvent mouse click
     * @author Utsav Parajuli
     */
    public void onLoginClicked (MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();

        //checking if fields are empty
        if (usernameEntry.getText().trim().isEmpty() && passwordEntry.getText().trim().isEmpty()) {
            //if the fields are empty
            usernameEntry.setStyle("-fx-border-color: red");
            passwordEntry.setStyle("-fx-border-color: red");
            errorMessage.setText("Incorrect username/password. Try again!");
        }

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();     //gets window
        windowUpdate = window;                                                              //save the instance of window
        appController = (AppController) window.getUserData();                               //getting app controller
        api = appController.getApi();                                                       //getting api

        //sending the message through the api
        api.publish()
                .message(new LoginData(usernameEntry.getText(), null, null, passwordEntry.getText()))
                .channel(Channels.AUTHOR_VALIDATE.toString())
                .execute();
    }

    /**
     * Method that will log you in when the enter key is pressed
     * @param keyEvent Enter key pressed
     * @throws IOException Exception
     * @author Utsav Parajuli
     */
    public void onEnterPressed(KeyEvent keyEvent) throws IOException {

        //if the key pressed was an enter then process the code inside
        if(keyEvent.getCode() == KeyCode.ENTER) {

            EventSounds.getInstance().playButtonSound4();

            if (usernameEntry.getText().trim().isEmpty() && passwordEntry.getText().trim().isEmpty()) {
                //if the fields are empty
                usernameEntry.setStyle("-fx-border-color: red");
                passwordEntry.setStyle("-fx-border-color: red");// or false to unset it
                errorMessage.setText("Incorrect username/password. Try again!");
            }

            Stage window = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();        //getting the stage
            windowUpdate = window;                                                              //updating window
            appController = (AppController) window.getUserData();                               //getting AppController
            api = appController.getApi();                                                       //getting api

            //using the api to send message
            api.publish()
                    .message(new LoginData(usernameEntry.getText(), null, null, passwordEntry.getText()))
                    .channel(Channels.AUTHOR_VALIDATE.toString())
                    .execute();
        }
    }

    /**
     * This method will take you to the main menu page without logging in. This if for
     * guest login
     * @param actionEvent: mouse click
     * @author Utsav Parajuli
     */
    public void onGuestLoginClicked(MouseEvent actionEvent) {

        EventSounds.getInstance().playButtonSound4();

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();     //getting window
        EventManager.register(MainController.getInstance(), (AppController) window.getUserData());
        EventManager.notify(MainController.getInstance(), MainController.getInstance());
        EventManager.removeAllObserver(this);
    }

    /**
     * Handles the event for create account button pressed. Directs user to create Account page
     * @param actionEvent: mouse click
     * @author Utsav Parajuli
     */
    public void onCreateAccountClicked(MouseEvent actionEvent) {

        EventSounds.getInstance().playButtonSound4();
        EventManager.notify(this, CreateAccountController.getInstance());
        EventManager.removeAllObserver(this);

    }

    /**
     * This method will reset the text fields and error message for new entry to be entered.
     * @param mouseEvent: mouse click
     * @author Utsav Parajuli
     */
    public void onResetClicked(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound3();
        errorMessage.setText("");
        usernameEntry.setStyle("-fx-border-color: khaki");
        usernameEntry.setText("");
        passwordEntry.setStyle("-fx-border-color: khaki");
        passwordEntry.setText("");
    }

    /**
     * Event handler for login enter
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onLoginButtonEnter(MouseEvent mouseEvent) {
        loginButton.setImage(loginButtonHover);
    }

    /**
     * Event handler for login exit
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onLoginButtonExit(MouseEvent mouseEvent) {
        loginButton.setImage(loginButtonIdle);
    }

    /**
     * Event handler for create account enter
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onCreateAccountEnter(MouseEvent mouseEvent) {
        createAccountButton.setImage(createAccountButtonHover);
    }

    /**
     * Event handler for create account exit
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onCreateAccountExit(MouseEvent mouseEvent) {
        createAccountButton.setImage(createAccountButtonIdle);
    }

    /**
     * Event handler for reset enter
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onResetEnter(MouseEvent mouseEvent) {
        resetButton.setImage(resetButtonHover);
    }

    /**
     * Event handler for reset exit
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onResetExit(MouseEvent mouseEvent) {
        resetButton.setImage(resetButtonIdle);
    }

    /**
     * Event handler for guest button enter
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onGuestButtonEnter(MouseEvent mouseEvent) {
        guestButton.setImage(guestButtonHover);
    }

    /**
     * Event handler for guest button exit
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onGuestButtonExit(MouseEvent mouseEvent) {
        guestButton.setImage(guestButtonIdle);
    }

    /**
     * New info is received through this method. Object decoding is needed
     *
     * @param eventType General Object type
     * @author Kord Boniadi
     */

    /**
     * New info is received through this method. For the login controller
     *
     * @param eventType General Object type
     * @author Utsav Parajuli
     */
    @Override
    public void update(Object eventType) {
        if (eventType instanceof AuthorizationCallback.LoginMessage) {          //checking if the loginMessage was sent
            messageList = (AuthorizationCallback.LoginMessage) eventType;       //getting message

            //updates the UI
            Platform.runLater(()-> {
                //if the username and password match then allow the user to login/take them to login page
                if (messageList.isLoginValidation()) {
                    EventManager.register(MainController.getInstance(), (AppController) windowUpdate.getUserData());
                    EventManager.notify(MainController.getInstance(), MainController.getInstance());
                    EventManager.removeAllObserver(this);
                } else {    //if the above methods don't pass then incorrect/username password was entered
                    usernameEntry.setStyle("-fx-border-color: red");
                    passwordEntry.setStyle("-fx-border-color: red");// or false to unset it
                    errorMessage.setText("Incorrect username/password. Try again!");
                }
            });
            //clears fields
            usernameEntry.clear();
            passwordEntry.clear();
        }
    }
}