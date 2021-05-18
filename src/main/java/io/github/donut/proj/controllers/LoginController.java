package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.LoginData;
import io.github.donut.proj.callbacks.AuthorizationCallback;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.event.Event;
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
 * Class that handles the Login Page
 * @author  : Utsav Parajuli
 * @version : 0.2
 */
@Getter
public class LoginController extends AbstractController implements ISubject {
    @FXML
    private BorderPane loginPage;

    @FXML
    private Label loginTitle;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label errorMessage;
    @FXML
    private Label guestLabel;

    @FXML
    private TextField usernameEntry;
    @FXML
    private ImageView loginButton;
    @FXML
    private ImageView createAccountButton;
    @FXML
    private ImageView guestButton;
    @FXML
    private ImageView resetButton;

    @FXML
    private PasswordField passwordEntry;

    @Setter
    private MessagingAPI api = null;                                   //instance of api
    @Setter
    private AuthorizationCallback ac;

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
     * @author Utsav Parajuli
     */
    @FXML
    public void initialize() {

        loginTitle.setText   ("WELCOME BACK!! Please Login");
        usernameLabel.setText("Username: ");
        passwordLabel.setText("Password: ");
        guestLabel.setText   ("Press to Login as Guest");

        /*========================Action Events START=========================*/
        usernameEntry.setOnKeyPressed(this::onEnterPressed);
        passwordEntry.setOnKeyPressed(this::onEnterPressed);

        resetButton.setOnMouseClicked(this::onResetClicked);
        resetButton.setOnMouseEntered(this::onResetEnter);
        resetButton.setOnMouseExited(this::onResetExit);

        loginButton.setOnMouseClicked(this::onLoginClicked);
        loginButton.setOnMouseEntered(this::onLoginButtonEnter);
        loginButton.setOnMouseExited(this::onLoginButtonExit);

        createAccountButton.setOnMouseClicked(this::onCreateAccountClicked);
        createAccountButton.setOnMouseEntered(this::onCreateAccountEnter);
        createAccountButton.setOnMouseExited(this::onCreateAccountExit);

        guestButton.setOnMouseClicked(this::onGuestLoginClicked);
        guestButton.setOnMouseEntered(this::onGuestButtonEnter);
        guestButton.setOnMouseExited(this::onGuestButtonExit);
        /*========================Action Events END=========================*/
    }

    /*=============================HELPER==========================================*/
    public void worker(Event event) {
        EventSounds.getInstance().playButtonSound4();

        //checking if fields are empty
        if (usernameEntry.getText().trim().isEmpty() && passwordEntry.getText().trim().isEmpty()) {
            //if the fields are empty
            usernameEntry.setStyle("-fx-border-color: red");
            passwordEntry.setStyle("-fx-border-color: red");
            errorMessage.setText("Incorrect username/password. Try again!");
        }

        if (api == null)
            api = ((AppController) stage.getUserData()).getApi();
        api.addEventListener(ac, Channels.PRIVATE + api.getUuid());
        //sending the message through the api
        api.publish()
                .message(new LoginData(usernameEntry.getText(), null, null, passwordEntry.getText()))
                .channel(Channels.AUTHOR_VALIDATE.toString())
                .execute();
    }
    /*=============================HELPER END==========================================*/

    /**
     * Method that will log you in when the login button is pressed
     * @param actionEvent mouse click
     * @author Utsav Parajuli
     */
    public void onLoginClicked (MouseEvent actionEvent) {
        worker(actionEvent);
    }

    /**
     * Method that will log you in when the enter key is pressed
     * @param keyEvent Enter key pressed
     * @author Utsav Parajuli
     */
    public void onEnterPressed(KeyEvent keyEvent) {
        //if the key pressed was an enter then process the code inside
        if(keyEvent.getCode() == KeyCode.ENTER) {
            worker(keyEvent);
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
        stage.setScene(AppController.getScenes().get(SceneName.Main).getScene(ControllerFactory.getController(SceneName.Main)));
        if (api != null)
            api.removeEventListener(ac);
    }

    /**
     * Handles the event for create account button pressed. Directs user to create Account page
     * @param actionEvent: mouse click
     * @author Utsav Parajuli
     */
    public void onCreateAccountClicked(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.CREATEACCOUNT_PAGE).getScene(ControllerFactory.getController(SceneName.CREATEACCOUNT_PAGE), false));
        if (api != null)
            api.removeEventListener(ac);
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
}