package io.github.donut.proj.controllers;

import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.LoginData;
import io.github.donut.proj.callbacks.AuthorizationCallback;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Class that handles the CreateAccount page
 * @author  : Utsav Parajuli
 * @version : 0.3
 */
@Getter
public class CreateAccountController extends AbstractController implements ISubject {

    @FXML
    private Label createAccountTitle;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel1;
    @FXML
    private Label passwordLabel2;
    @FXML
    private Label registrationMessage;
    @FXML
    private Label emptyMessage;
    @FXML
    private Label passwordMessage;

    @FXML
    private ImageView backButton;
    @FXML
    private ImageView signUpButton;
    @FXML

    private BorderPane createAccountPage;
    @FXML
    private TextField  firstNameEntry;
    @FXML
    private TextField  lastNameEntry;
    @FXML
    private TextField  usernameEntry;

    @FXML
    private PasswordField passwordEntry1;
    @FXML
    private PasswordField passwordEntry2;

//    @Setter
//    private MessagingAPI api = null;                                   //instance of api
    @Setter
    private AuthorizationCallback ac;

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
    @FXML
    public void initialize() {
        createAccountTitle.setText("CREATE YOUR ACCOUNT");
        firstNameLabel.setText    ("First Name  ");
        lastNameLabel.setText     ("Last Name   ");
        usernameLabel.setText     ("Username    ");
        passwordLabel1.setText    ("Password    ");
        passwordLabel2.setText    ("Confirm     \nPassword");

        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);

        firstNameEntry.setOnKeyPressed(this::onEnterPressed);
        lastNameEntry.setOnKeyPressed(this::onEnterPressed);
        usernameEntry.setOnKeyPressed(this::onEnterPressed);
        passwordEntry1.setOnKeyPressed(this::onEnterPressed);
        passwordEntry2.setOnKeyPressed(this::onEnterPressed);

        signUpButton.setOnMouseClicked(this::onSignUpClick);
        signUpButton.setOnMouseEntered(this::onSignUpEnter);
        signUpButton.setOnMouseExited(this::onSignUpExit);
        /*========================Action Events END=========================*/
    }

    /**
     * Clear UI elements
     * @author Kord Boniadi
     */
    public void clearScreen() {
        firstNameEntry.clear();
        lastNameEntry.clear();
        usernameEntry.clear();
        passwordEntry1.clear();
        passwordEntry2.clear();

        emptyMessage.setText("");
        passwordMessage.setText("");
        registrationMessage.setText("");

        firstNameEntry.setStyle("-fx-border-color: khaki");
        lastNameEntry.setStyle("-fx-border-color: khaki");
        usernameEntry.setStyle("-fx-border-color: khaki");
        passwordEntry1.setStyle("-fx-border-color: khaki");
        passwordEntry2.setStyle("-fx-border-color: khaki");
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Utsav Parajuli
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        stage.setScene(AppController.getScenes().get(SceneName.LOGIN_PAGE).getScene(ControllerFactory.getController(SceneName.LOGIN_PAGE), false));
        clearScreen();

//        if (api != null)
//            api.removeEventListener(ac);
    }

    /**
     * Event handler for back button hover effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonEnter(MouseEvent mouseEvent) {
        backButton.setImage(backButtonHover);
    }

    /**
     * Event handler for back button idle effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonExit(MouseEvent mouseEvent) {
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
        //checking if keycode was enter
        if (keyEvent.getCode() == KeyCode.ENTER) {
            EventSounds.getInstance().playButtonSound4();
            //creates account
            createAccount();

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

            GlobalAPIManager.getInstance().swapListener(ac, Channels.PRIVATE + GlobalAPIManager.getInstance().getApi().getUuid());
            GlobalAPIManager.getInstance().send(new LoginData(usernameEntry.getText(), firstNameEntry.getText(),
                    lastNameEntry.getText(), passwordEntry1.getText()), Channels.AUTHOR_CREATE.toString());
        }
    }
}