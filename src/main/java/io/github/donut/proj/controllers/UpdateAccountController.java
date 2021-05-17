package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.UpdateData;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.proj.utils.Logger;
import io.github.donut.sounds.EventSounds;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateAccountController extends AbstractController implements Initializable, ISubject {

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

//    private UpdatesCallback.UsernameMsg     usernameMsg;
//    private UpdatesCallback.PersonalInfoMsg personalInfoMsg;
//    private UpdatesCallback.PasswordMsg     passwordMsg;

    private MessagingAPI api;                                   //instance of api
    private AppController appController;                        //instance of app controller

    private static UpdateAccountController instance = new UpdateAccountController();

    /**
     * @return instance of Create account screen controller
     */
    public static UpdateAccountController getInstance() {
        return instance;
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
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        stage.setScene(AppController.getScenes().get(SceneName.PORTAL_PAGE).getScene(false));
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

    public void onUserNameChangeEnterPressed(KeyEvent keyEvent) {

        Stage window = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();    //gets the window
        appController = (AppController) window.getUserData();                           //gets the AppController

        api = appController.getApi();                                                   //getting the instance of api

        if (keyEvent.getCode() == KeyCode.ENTER) {
            EventSounds.getInstance().playButtonSound4();
            userNameChange();
        }
    }

    private void userNameChange() {
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

            //TODO: make DB call
//            currentUsernameErrorTab1.setText("");
//            differentUsernameErrorTab1.setText("");
//            successfulUpdateTab1.setText("UPDATED!");
            //sending the message
//            api.publish()
//                    .message(new UpdateData(currentUserNameTab1.getText(), null, null, null,
//                            newUserNameTab1.getText(), null))
//                    .channel(Channels.UPDATE_USERNAME.toString())
//                    .execute();
        }
    }

    /**
     * Event handler for create account button clicked
     *
     * @param actionEvent on click
     * @author Utsav Parajuli
     */
    public void onUserNameChangeClick(MouseEvent actionEvent) {

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();    //gets the window
        appController = (AppController) window.getUserData();                           //gets the AppController

        api = appController.getApi();                                                   //getting the instance of api

        EventSounds.getInstance().playButtonSound4();

        userNameChange();
    }

    /**
     * Event handler for create account button clicked
     *
     * @param actionEvent on click
     * @author Utsav Parajuli
     */
    public void onPersonalInfoClick(MouseEvent actionEvent) {

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();    //gets the window
        appController = (AppController) window.getUserData();                           //gets the AppController

        api = appController.getApi();                                                   //getting the instance of api

        EventSounds.getInstance().playButtonSound4();

        personalInfoChange();
    }

    public void onPersonalInfoChangeEnterPressed(KeyEvent keyEvent) {

        Stage window = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();    //gets the window
        appController = (AppController) window.getUserData();                           //gets the AppController

        api = appController.getApi();                                                   //getting the instance of api

        if (keyEvent.getCode() == KeyCode.ENTER) {
            EventSounds.getInstance().playButtonSound4();
            personalInfoChange();
        }
    }

    private void personalInfoChange() {
        if(userNameTab2.getText().trim().isEmpty() || firstNameEntryTab2.getText().trim().isEmpty() ||
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

        if(!(userNameTab2.getText().trim().isEmpty()) || !(firstNameEntryTab2.getText().trim().isEmpty()) ||
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

            //TODO: make DB call
//            usernameErrorTab2.setText("");
//            successfulUpdateTab2.setText("UPDATED!");
//            api.publish()
//                    .message(new UpdateData(userNameTab2.getText(), null, firstNameEntryTab2.getText(),
//                            lastNameEntryTab2.getText(), null, null))
//                    .channel(Channels.UPDATE_PERSONAL_INFO.toString())
//                    .execute();
        }
    }

    /**
     * Event handler for create account button clicked
     *
     * @param actionEvent on click
     * @author Utsav Parajuli
     */
    public void onPasswordChangeClick(MouseEvent actionEvent) {

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();    //gets the window
        appController = (AppController) window.getUserData();                           //gets the AppController

        api = appController.getApi();                                                   //getting the instance of api

        EventSounds.getInstance().playButtonSound4();

        passwordChange();
    }



    public void onPasswordChangeEnterPressed(KeyEvent keyEvent) {

        Stage window = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();    //gets the window
        appController = (AppController) window.getUserData();                           //gets the AppController

        api = appController.getApi();                                                   //getting the instance of api

        if (keyEvent.getCode() == KeyCode.ENTER) {
            EventSounds.getInstance().playButtonSound4();
            passwordChange();
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

            //TODO: make DB call
//            usernameErrorTab3.setText("");
//            differentPasswordErrorTab3.setText("");
//            successfulUpdateTab3.setText("UPDATED!");
//            api.publish()
//                    .message(new UpdateData(userNameEntryTab3.getText(), currentPasswordTab3.getText(), null,
//                            null, null, newPasswordEntryTab3.getText()))
//                    .channel(Channels.UPDATE_PASSWORD.toString())
//                    .execute();
        }
    }

//    /**
//     * New info is received through this method. Object decoding is needed
//     *
//     * @param eventType General Object type
//     * @author Kord Boniadi
//     */
//    @Override
//    public void update(Object eventType) {
//        if (eventType instanceof UpdatesCallback.UsernameMsg) {
//            usernameMsg = (UpdatesCallback.UsernameMsg) eventType;
//            Platform.runLater(()->{
//                if (usernameMsg.isUsernameUpdate()) {
//
//                    currentUserNameTab1.setStyle("-fx-border-color: khaki");
//                    newUserNameTab1.setStyle("-fx-border-color: khaki");
//                    confirmUserNameTab1.setStyle("-fx-border-color: khaki");
//
//                    currentUsernameErrorTab1.setText("");
//                    differentUsernameErrorTab1.setText("");
//                    successfulUpdateTab1.setText("ACCOUNT UPDATED!");
//
//                    currentUserNameTab1.clear();
//                    newUserNameTab1.clear();
//                    confirmUserNameTab1.clear();
//                } else{
//                    currentUserNameTab1.setStyle("-fx-border-color: red");
//                    currentUserNameTab1.setStyle("-fx-border-color: red");
//                    confirmUserNameTab1.setStyle("-fx-border-color: red");
//
//                    differentUsernameErrorTab1.setText("");
//                    successfulUpdateTab1.setText("");
//                    currentUsernameErrorTab1.setText("Incorrect username/Username already exists!");
//                }
//
//            });
//        }
//        else if (eventType instanceof UpdatesCallback.PersonalInfoMsg) {
//            personalInfoMsg = (UpdatesCallback.PersonalInfoMsg) eventType;
//            Platform.runLater(()->{
//                if (personalInfoMsg.isPersonalInfoUpdate()) {
//
//                    userNameTab2.setStyle("-fx-border-color: khaki");
//                    firstNameEntryTab2.setStyle("-fx-border-color: khaki");
//                    lastNameEntryTab2.setStyle("-fx-border-color: khaki");
//
//                    usernameErrorTab2.setText("");
//                    successfulUpdateTab2.setText("ACCOUNT UPDATED!");
//
//                    userNameTab2.clear();
//                    firstNameEntryTab2.clear();
//                    lastNameEntryTab2.clear();
//                }
//                else {
//                    userNameTab2.setStyle("-fx-border-color: red");
//
//                    successfulUpdateTab2.setText("");
//                    usernameErrorTab2.setText("Username does not exist!");
//                }
//            });
//        }
//        else if (eventType instanceof UpdatesCallback.PasswordMsg) {
//            passwordMsg = (UpdatesCallback.PasswordMsg) eventType;
//            Platform.runLater(()->{
//                if (passwordMsg.isPasswordUpdate()) {
//
//                    userNameEntryTab3.setStyle("-fx-border-color: khaki");
//                    currentPasswordTab3.setStyle("-fx-border-color: khaki");
//                    newPasswordEntryTab3.setStyle("-fx-border-color: khaki");
//                    confirmPasswordEntryTab3.setStyle("-fx-border-color: khaki");
//
//                    usernameErrorTab3.setText("");
//                    differentPasswordErrorTab3.setText("");
//                    successfulUpdateTab3.setText("ACCOUNT UPDATED!");
//
//                    userNameEntryTab3.clear();
//                    currentPasswordTab3.clear();
//                    newPasswordEntryTab3.clear();
//                    confirmPasswordEntryTab3.clear();
//                }
//                else {
//                    userNameEntryTab3.setStyle("-fx-border-color: red");
//                    currentPasswordTab3.setStyle("-fx-border-color: red");
//
//                    differentPasswordErrorTab3.setText("");
//                    successfulUpdateTab3.setText("");
//                    usernameErrorTab3.setText("Username/Password do not match");
//                }
//            });
//        }
//    }
}