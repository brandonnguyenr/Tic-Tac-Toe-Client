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
 * Class that handles the CreateAccount page
 * @author  : Utsav Parajuli
 * @version : 0.3
 */
public class CreateAccountController implements Initializable, ISubject, IObserver {

    public Label createAccountTitle;
    public Label firstNameLabel;
    public Label lastNameLabel;
    public Label usernameLabel;
    public Label passwordLabel1;
    public Label passwordLabel2;
    public Label registrationMessage;
    public Label emptyMessage;
    public Label passwordMessage;

    public ImageView backButton;
    public ImageView signUpButton;

    public BorderPane createAccountPage;
    public TextField  firstNameEntry;
    public TextField  lastNameEntry;
    public TextField  usernameEntry;

    public PasswordField passwordEntry1;
    public PasswordField passwordEntry2;

    private AuthorizationCallback.CreateMessage messageList;    //message list for the appropriate message to display

    private MessagingAPI api;                                   //instance of api
    private AppController appController;                        //instance of app controller

    //creates an instance of the controller
    private static CreateAccountController instance = new CreateAccountController();

    //back button idle image
    private final Image backButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow.png")
    ));

    //back button hover image
    private final Image backButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow_hover.png")
    ));

    //create account button idle
    private final Image signUpButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/signup.png")
    ));

    //create account button hover
    private final Image signUpButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/signup_hover.png")
    ));


    /**
     * Initialize the class.
     * @author Utsav Parajuli
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createAccountTitle.setText("CREATE YOUR ACCOUNT");
        firstNameLabel.setText    ("First Name  ");
        lastNameLabel.setText     ("Last Name   ");
        usernameLabel.setText     ("Username    ");
        passwordLabel1.setText    ("Password    ");
        passwordLabel2.setText    ("Confirm     \nPassword");
    }

    /**
     * @return instance of Create account screen controller
     */
    public static CreateAccountController getInstance() {
        return instance;
    }

    /**
     * Constructor
     */
    private CreateAccountController() {
        instance = this;
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Utsav Parajuli
     */
    public void onBackButtonClick(MouseEvent actionEvent) throws IOException {

        EventSounds.getInstance().playButtonSound1();

        //Removes all observers to free up memory
        EventManager.removeAllObserver(this);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //Registers the instance of login controller and notifies the event manager from which the
        //method in AppController is called
        EventManager.register(LoginController.getInstance(), (AppController) window.getUserData());
        EventManager.notify(LoginController.getInstance(), LoginController.getInstance());
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

    /**
     * Event handler for enter key pressed
     *
     * @param keyEvent: the ENTER key pressed
     *
     * @author Utsav Parajuli
     */
    public void onEnterPressed(KeyEvent keyEvent) {
        Stage window = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();    //gets the window
        appController = (AppController) window.getUserData();                           //gets the AppController

        api = appController.getApi();                                                   //getting the instance of api

        //checking if keycode was enter
        if (keyEvent.getCode() == KeyCode.ENTER) {
            EventSounds.getInstance().playButtonSound4();
            createAccount();                                                            //creating account
        }
    }

    /**
     * Event handler for create account button clicked
     *
     * @param actionEvent on click
     * @author Utsav Parajuli
     */
    public void onSignUpClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();     //gets the window
        appController = (AppController) window.getUserData();                               //gets the AppController

        api = appController.getApi();                                                       //instance of api

        //creates account
        createAccount();
    }

    /**
     * Event handler for create account button hover
     *
     * @param mouseEvent mouse event
     * @author Utsav Parajuli
     */
    public void onSignUpEnter(MouseEvent mouseEvent) {
        signUpButton.setImage(signUpButtonHover);
    }

    /**
     * Event handler for create account exit
     *
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onSignUpExit(MouseEvent mouseEvent) {
        signUpButton.setImage(signUpButtonIdle);
    }

    /**
     * Method that will error check the stats in the create account page and publishes error message depending on the
     * status of the user entry.
     *
     * @author Utsav Parajuli
     */
    private void createAccount() {

        //This conditional statement executes if there are any empty field in the signup page and will display to
        //the user that some fields are empty
        if ((firstNameEntry.getText().trim().isEmpty()) || (lastNameEntry.getText().trim().isEmpty()) ||
                (usernameEntry.getText().trim().isEmpty()) || (passwordEntry1.getText().trim().isEmpty()) ||
                (passwordEntry2.getText().trim().isEmpty())) {

            //displaying error message
            passwordMessage.setText("");
            registrationMessage.setText("");
            emptyMessage.setText("One or more fields empty!");

            //These conditional statements will check which fields are empty
            //Empty fields will be highlighted red
            if (firstNameEntry.getText().trim().isEmpty())
                firstNameEntry.setStyle("-fx-border-color: red");

            if (lastNameEntry.getText().trim().isEmpty())
                lastNameEntry.setStyle("-fx-border-color: red");

            if (usernameEntry.getText().trim().isEmpty())
                usernameEntry.setStyle("-fx-border-color: red");

            if (passwordEntry1.getText().trim().isEmpty())
                passwordEntry1.setStyle("-fx-border-color: red");

            if (passwordEntry2.getText().trim().isEmpty())
                passwordEntry2.setStyle("-fx-border-color: red");
        }   //end of first condition

        //This conditional statement will check if any of the entries are filled. If the entries are filled
        //the text box will be highlighted back to normal
        if (!(firstNameEntry.getText().trim().isEmpty()) || !(lastNameEntry.getText().trim().isEmpty()) ||
                !(usernameEntry.getText().trim().isEmpty()) || !(passwordEntry1.getText().trim().isEmpty()) ||
                !(passwordEntry2.getText().trim().isEmpty())) {

            //checks if the fields are filled and will change the border back to normal
            if (!(firstNameEntry.getText().trim().isEmpty()))
                firstNameEntry.setStyle("-fx-border-color: khaki");

            if (!(lastNameEntry.getText().trim().isEmpty()))
                lastNameEntry.setStyle("-fx-border-color: khaki");

            if (!(usernameEntry.getText().trim().isEmpty()))
                usernameEntry.setStyle("-fx-border-color: khaki");

            if (!(passwordEntry1.getText().trim().isEmpty()))
                passwordEntry1.setStyle("-fx-border-color: khaki");

            if (!(passwordEntry2.getText().trim().isEmpty()))
                passwordEntry2.setStyle("-fx-border-color: khaki");
        }

        //This condition will check if the passwords are matching. If not an error message is displayed
        if (!(passwordEntry1.getText().equals(passwordEntry2.getText()))) {
            passwordEntry2.setStyle("-fx-border-color: red");
            emptyMessage.setText("");
            registrationMessage.setText("");
            passwordMessage.setText("* Please enter matching passwords.");
        }

        //This condition will check if all the fields are entered and the passwords match
        if ((passwordEntry1.getText().equals(passwordEntry2.getText())) &&
                !(firstNameEntry.getText().trim().isEmpty()) && !(lastNameEntry.getText().trim().isEmpty()) &&
                !(usernameEntry.getText().trim().isEmpty()) && !(passwordEntry1.getText().trim().isEmpty()) &&
                !(passwordEntry2.getText().trim().isEmpty())) {

            //sending the message
            api.publish()
                    .message(new LoginData(usernameEntry.getText(), firstNameEntry.getText(),
                            lastNameEntry.getText(), passwordEntry1.getText()))
                    .channel(Channels.AUTHOR_CREATE.toString())
                    .execute();
        }
    }

    /**
     * New info is received through this method.
     *
     * @param eventType General Object type
     * @author Kord Boniadi
     * @author Utsav Parajuli
     */
    @Override
    public void update(Object eventType) {
        if (eventType instanceof AuthorizationCallback.CreateMessage) {         //checking if the object was a of
                                                                                //create message type
            messageList = (AuthorizationCallback.CreateMessage) eventType;

            //updates the UI
            Platform.runLater(()->{
                if(messageList.isAccountCreation()) {
                    usernameEntry.setStyle("-fx-border-color: khaki");
                    emptyMessage.setText("");
                    passwordMessage.setText("");
                    registrationMessage.setText("Successfully Registered! Go back to Login Screen.");
                    //clears the entry
                    firstNameEntry.clear();
                    lastNameEntry.clear();
                    usernameEntry.clear();
                    passwordEntry1.clear();
                    passwordEntry2.clear();
                } else {
                    usernameEntry.setStyle("-fx-border-color: red");
                    registrationMessage.setText("");
                    emptyMessage.setText("USERNAME ALREADY EXISTS");
                    passwordMessage.setText("");
                    //clears username entry
                    usernameEntry.clear();
                }
            });
        }
    }
}