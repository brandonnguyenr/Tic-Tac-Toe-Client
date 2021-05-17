package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.UpdateData;
import io.github.donut.proj.callbacks.UpdatesCallback;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * UpdateAccountController where player will be able to change their username, personal info, and password.
 *
 * @author Utsav Parajuli
 */
@Getter
public class UpdateAccountController extends AbstractController implements ISubject {

    @FXML
    private ImageView backButton;

    @FXML
    private ImageView passwordChangeButton;
    @FXML
    private ImageView personalInfoButton;
    @FXML
    private ImageView userNameChangeButton;

    @FXML
    private TextField currentUserNameTab1;
    @FXML
    private TextField newUserNameTab1;
    @FXML
    private TextField confirmUserNameTab1;

    @FXML
    private TextField userNameTab2;
    @FXML
    private TextField firstNameEntryTab2;
    @FXML
    private TextField lastNameEntryTab2;

    @FXML
    private TextField userNameEntryTab3;
    @FXML
    private PasswordField currentPasswordTab3;
    @FXML
    private PasswordField newPasswordEntryTab3;
    @FXML
    private PasswordField confirmPasswordEntryTab3;

    @FXML
    private Label currentUsernameErrorTab1;
    @FXML
    private Label successfulUpdateTab1;
    @FXML
    private Label differentUsernameErrorTab1;

    @FXML
    private Label usernameErrorTab2;
    @FXML
    private Label successfulUpdateTab2;

    @FXML
    private Label differentPasswordErrorTab3;
    @FXML
    private Label successfulUpdateTab3;
    @FXML
    private Label usernameErrorTab3;

    @FXML
    private Tab updateUsernameTitle;
    @FXML
    private Tab updatePersonalInfoTitle;
    @FXML
    private Tab changePasswordTitle;
    @FXML
    private TabPane tabPane;

    @Setter
    private MessagingAPI api = null;                            //instance of api

    @Setter
    private UpdatesCallback uc;                                //instance of updates callback

    /**
     * Initialize the class.
     * @author Utsav Parajuli
     */
    @FXML
    public void initialize() {

        tabPane.getSelectionModel().select(updateUsernameTitle);

        /*========================Action Events START=========================*/
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);

        userNameChangeButton.setOnMouseEntered(this::onUpdateEnter);
        personalInfoButton.setOnMouseEntered(this::onUpdateEnter);
        passwordChangeButton.setOnMouseEntered(this::onUpdateEnter);

        userNameChangeButton.setOnMouseExited(this::onUpdateExit);
        personalInfoButton.setOnMouseExited(this::onUpdateExit);
        passwordChangeButton.setOnMouseExited(this::onUpdateExit);

        userNameChangeButton.setOnMouseClicked(this::onUserNameChangeClick);
        personalInfoButton.setOnMouseClicked(this::onPersonalInfoClick);
        passwordChangeButton.setOnMouseClicked(this::onPasswordChangeClick);

        currentUserNameTab1.setOnKeyPressed(this::onUserNameChangeEnterPressed);
        newUserNameTab1.setOnKeyPressed(this::onUserNameChangeEnterPressed);
        confirmUserNameTab1.setOnKeyPressed(this::onUserNameChangeEnterPressed);

        userNameTab2.setOnKeyPressed(this::onPersonalInfoChangeEnterPressed);
        firstNameEntryTab2.setOnKeyPressed(this::onPersonalInfoChangeEnterPressed);
        lastNameEntryTab2.setOnKeyPressed(this::onPersonalInfoChangeEnterPressed);

        userNameEntryTab3.setOnKeyPressed(this::onPasswordChangeEnterPressed);
        currentPasswordTab3.setOnKeyPressed(this::onPasswordChangeEnterPressed);
        newPasswordEntryTab3.setOnKeyPressed(this::onPasswordChangeEnterPressed);
        confirmPasswordEntryTab3.setOnKeyPressed(this::onPasswordChangeEnterPressed);

        updateUsernameTitle.setOnSelectionChanged(this::onTabClosed);
        updatePersonalInfoTitle.setOnSelectionChanged(this::onTabClosed);
        changePasswordTitle.setOnSelectionChanged(this::onTabClosed);
        /*========================Action Events END=========================*/

        clearScreen();
    }

    /**
     * Method that will clear all the other tabs when one is closed
     * @param event : mouse event
     * @author Utsav Parajuli
     */
    public void onTabClosed(Event event) {
        clearScreen();
    }

    /**
     * Clears UI elements
     *
     * @author Utsav Parajuli
     */
    public void clearScreen() {

        currentUserNameTab1.setStyle("-fx-border-color: khaki");
        newUserNameTab1.setStyle("-fx-border-color: khaki");
        confirmUserNameTab1.setStyle("-fx-border-color: khaki");

        userNameTab2.setStyle("-fx-border-color: khaki");
        firstNameEntryTab2.setStyle("-fx-border-color: khaki");
        lastNameEntryTab2.setStyle("-fx-border-color: khaki");

        userNameEntryTab3.setStyle("-fx-border-color: khaki");
        currentPasswordTab3.setStyle("-fx-border-color: khaki");
        newPasswordEntryTab3.setStyle("-fx-border-color: khaki");
        confirmPasswordEntryTab3.setStyle("-fx-border-color: khaki");

        currentUsernameErrorTab1.setText("");
        successfulUpdateTab1.setText("");
        differentUsernameErrorTab1.setText("");

        usernameErrorTab2.setText("");
        successfulUpdateTab2.setText("");

        usernameErrorTab3.setText("");
        successfulUpdateTab3.setText("");
        differentPasswordErrorTab3.setText("");
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Kord Boniadi
     * @author Utsav Parajuli
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        stage.setScene(AppController.getScenes().get(SceneName.PORTAL_PAGE).getScene(false));
        clearScreen();
        tabPane.getSelectionModel().select(updateUsernameTitle);
    }

    /**
     * Event handler for back button hover effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonEnter(MouseEvent actionEvent) {
        backButton.setImage(backButtonHover);
    }

    /**
     * Event handler for back button idle effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonExit(MouseEvent actionEvent) {
        backButton.setImage(backButtonIdle);
    }


    /**
     * Event handler for create account button hover
     *
     * @param mouseEvent mouse event
     * @author Utsav Parajuli
     */
    public void onUpdateEnter(MouseEvent mouseEvent) {
        userNameChangeButton.setImage(updateButtonHover);
        personalInfoButton.setImage(updateButtonHover);
        passwordChangeButton.setImage(updateButtonHover);
    }

    /**
     * Event handler for create account exit
     *
     * @param mouseEvent: mouse event
     * @author Utsav Parajuli
     */
    public void onUpdateExit(MouseEvent mouseEvent) {
        userNameChangeButton.setImage(updateButtonIdle);
        personalInfoButton.setImage(updateButtonIdle);
        passwordChangeButton.setImage(updateButtonIdle);
    }

    /**
     * Event handler for ENTER pressed on the username update tab
     * @param keyEvent : key pressed
     */
    public void onUserNameChangeEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            EventSounds.getInstance().playButtonSound4();
            userNameChange();
        }
    }

    /**
     * Event handler for username update button clicked
     *
     * @param actionEvent on click
     * @author Utsav Parajuli
     */
    public void onUserNameChangeClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();
        userNameChange();
    }

    /**
     * Event handler for ENTER pressed on the personal info update tab
     * @param keyEvent : key pressed
     */
    public void onPersonalInfoChangeEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            EventSounds.getInstance().playButtonSound4();
            personalInfoChange();
        }
    }

    /**
     * Event handler for personal info update button clicked
     *
     * @param actionEvent on click
     * @author Utsav Parajuli
     */
    public void onPersonalInfoClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();
        personalInfoChange();
    }

    /**
     * Event handler for ENTER pressed on the password update tab
     * @param keyEvent : key pressed
     */
    public void onPasswordChangeEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            EventSounds.getInstance().playButtonSound4();
            passwordChange();
        }
    }

    /**
     * Event handler for password change button clicked
     *
     * @param actionEvent on click
     * @author Utsav Parajuli
     */
    public void onPasswordChangeClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound4();
        passwordChange();
    }

    /**
     * This method will update the username of the player. Will ask for current username and the new username
     * two times to update. New usernames should match. Will also publish via the api
     */
    private void userNameChange() {
        //checking if any of the fields were empty
        if(currentUserNameTab1.getText().trim().isEmpty() || newUserNameTab1.getText().trim().isEmpty() ||
                confirmUserNameTab1.getText().trim().isEmpty()) {

            successfulUpdateTab1.setText("");
            differentUsernameErrorTab1.setText("");
            currentUsernameErrorTab1.setText("One or more fields are empty");

            if (currentUserNameTab1.getText().trim().isEmpty())
                currentUserNameTab1.setStyle("-fx-border-color: red");
            if (newUserNameTab1.getText().trim().isEmpty())
                newUserNameTab1.setStyle("-fx-border-color: red");
            if (confirmUserNameTab1.getText().trim().isEmpty())
                confirmUserNameTab1.setStyle("-fx-border-color: red");
        }

        if(!(currentUserNameTab1.getText().trim().isEmpty()) || !(newUserNameTab1.getText().trim().isEmpty()) ||
                !(confirmUserNameTab1.getText().trim().isEmpty())) {

            if (!(currentUserNameTab1.getText().trim().isEmpty()))
                currentUserNameTab1.setStyle("-fx-border-color: khaki");
            if (!(newUserNameTab1.getText().trim().isEmpty()))
                newUserNameTab1.setStyle("-fx-border-color: khaki");
            if (!(confirmUserNameTab1.getText().trim().isEmpty()))
                confirmUserNameTab1.setStyle("-fx-border-color: khaki");
        }

        if(!(newUserNameTab1.getText().equals(confirmUserNameTab1.getText()))) {
            confirmUserNameTab1.setStyle("-fx-border-color: red");
            currentUsernameErrorTab1.setText("");
            successfulUpdateTab1.setText("");
            differentUsernameErrorTab1.setText("Usernames do not match");
        }

        if(!(currentUserNameTab1.getText().trim().isEmpty()) && !(newUserNameTab1.getText().trim().isEmpty()) &&
                !(confirmUserNameTab1.getText().trim().isEmpty()) &&
                newUserNameTab1.getText().equals(confirmUserNameTab1.getText())) {
            if (api == null)
                api = ((AppController) stage.getUserData()).getApi();
            api.addEventListener(uc, Channels.PRIVATE + api.getUuid());
            //sending the message
            api.publish()
                    .message(new UpdateData(currentUserNameTab1.getText(), null, null, null,
                            newUserNameTab1.getText(), null))
                    .channel(Channels.UPDATE_USERNAME.toString())
                    .execute();

        }
    }

    private void personalInfoChange() {
        if (userNameTab2.getText().trim().isEmpty() || firstNameEntryTab2.getText().trim().isEmpty() ||
                lastNameEntryTab2.getText().trim().isEmpty()) {

            successfulUpdateTab2.setText("");
            usernameErrorTab2.setText("One or more fields are empty");

            if (userNameTab2.getText().trim().isEmpty())
                userNameTab2.setStyle("-fx-border-color: red");
            if (firstNameEntryTab2.getText().trim().isEmpty())
                firstNameEntryTab2.setStyle("-fx-border-color: red");
            if (lastNameEntryTab2.getText().trim().isEmpty())
                lastNameEntryTab2.setStyle("-fx-border-color: red");
        }

        if (!(userNameTab2.getText().trim().isEmpty()) || !(firstNameEntryTab2.getText().trim().isEmpty()) ||
                !(lastNameEntryTab2.getText().trim().isEmpty())) {

            if (!(userNameTab2.getText().trim().isEmpty()))
                userNameTab2.setStyle("-fx-border-color: khaki");
            if (!(firstNameEntryTab2.getText().trim().isEmpty()))
                firstNameEntryTab2.setStyle("-fx-border-color: khaki");
            if (!(lastNameEntryTab2.getText().trim().isEmpty()))
                lastNameEntryTab2.setStyle("-fx-border-color: khaki");
        }

        if(!(userNameTab2.getText().trim().isEmpty()) && !(firstNameEntryTab2.getText().trim().isEmpty()) &&
                !(lastNameEntryTab2.getText().trim().isEmpty())) {
            if (api == null)
                api = ((AppController) stage.getUserData()).getApi();
            api.addEventListener(uc, Channels.PRIVATE + api.getUuid());

            api.publish()
                    .message(new UpdateData(userNameTab2.getText(), null, firstNameEntryTab2.getText(),
                            lastNameEntryTab2.getText(), null, null))
                    .channel(Channels.UPDATE_PERSONAL_INFO.toString())
                    .execute();
        }
    }

    private void passwordChange() {
        if(userNameEntryTab3.getText().trim().isEmpty() || currentPasswordTab3.getText().trim().isEmpty() ||
                newPasswordEntryTab3.getText().trim().isEmpty() ||
                confirmPasswordEntryTab3.getText().trim().isEmpty()) {

            successfulUpdateTab3.setText("");
            differentPasswordErrorTab3.setText("");
            usernameErrorTab3.setText("One or more fields are empty");

            if (userNameEntryTab3.getText().trim().isEmpty())
                userNameEntryTab3.setStyle("-fx-border-color: red");
            if (currentPasswordTab3.getText().trim().isEmpty())
                currentPasswordTab3.setStyle("-fx-border-color: red");
            if (newPasswordEntryTab3.getText().trim().isEmpty())
                newPasswordEntryTab3.setStyle("-fx-border-color: red");
            if (confirmPasswordEntryTab3.getText().trim().isEmpty())
                confirmPasswordEntryTab3.setStyle("-fx-border-color: red");
        }

        if(!(userNameEntryTab3.getText().trim().isEmpty()) || !(currentPasswordTab3.getText().trim().isEmpty()) ||
                !(newPasswordEntryTab3.getText().trim().isEmpty()) || !(confirmPasswordEntryTab3.getText().trim().isEmpty())) {

            if (!(userNameEntryTab3.getText().trim().isEmpty()))
                userNameEntryTab3.setStyle("-fx-border-color: khaki");
            if (!(currentPasswordTab3.getText().trim().isEmpty()))
                currentPasswordTab3.setStyle("-fx-border-color: khaki");
            if (!(newPasswordEntryTab3.getText().trim().isEmpty()))
                newPasswordEntryTab3.setStyle("-fx-border-color: khaki");
            if (!(confirmPasswordEntryTab3.getText().trim().isEmpty()))
                confirmPasswordEntryTab3.setStyle("-fx-border-color: khaki");
        }

        if(!(newPasswordEntryTab3.getText().equals(confirmPasswordEntryTab3.getText()))) {
            confirmPasswordEntryTab3.setStyle("-fx-border-color: red");
            successfulUpdateTab3.setText("");
            usernameErrorTab3.setText("");
            differentPasswordErrorTab3.setText("Passwords do not match!");
        }

        if(!(userNameEntryTab3.getText().trim().isEmpty()) && !(currentPasswordTab3.getText().trim().isEmpty()) &&
                !(newPasswordEntryTab3.getText().trim().isEmpty()) &&
                !(confirmPasswordEntryTab3.getText().trim().isEmpty()) &&
                (newPasswordEntryTab3.getText().equals(confirmPasswordEntryTab3.getText())))
        {

            if (api == null)
                api = ((AppController) stage.getUserData()).getApi();
            api.addEventListener(uc, Channels.PRIVATE + api.getUuid());

            api.publish()
                    .message(new UpdateData(userNameEntryTab3.getText(), currentPasswordTab3.getText(), null,
                            null, null, newPasswordEntryTab3.getText()))
                    .channel(Channels.UPDATE_PASSWORD.toString())
                    .execute();
        }
    }

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


    //update account button idle
    private final Image updateButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/update_button.png")
    ));

    //update account button hover
    private final Image updateButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/icons/update_button_select.png")
    ));
}