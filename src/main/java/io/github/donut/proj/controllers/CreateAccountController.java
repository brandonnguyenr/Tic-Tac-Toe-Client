package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.Logger;
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
import java.util.HashMap;
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
    public Label firstNameError;
    public Label lastNameError;
    public Label usernameError;
    public Label password1Error;
    public Label password2Error;
    public Label registrationMessage;
    public ImageView createAccountButton;

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
    public Label emptyMessage;
    public Label passwordMessage;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createAccountTitle.setText("CREATE YOUR ACCOUNT");

        firstNameLabel.setText("First Name  ");
        lastNameLabel.setText ("Last Name   ");
        usernameLabel.setText ("Username    ");
        passwordLabel1.setText("Password    ");
        passwordLabel2.setText("Confirm     \nPassword");


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

        //checking if keycode was enter
        if(keyEvent.getCode() == KeyCode.ENTER) {
            createAccount();
        }

    }

    public void onCreateAccountEnter(MouseEvent mouseEvent) {
        createAccountButton.setImage(createAccountButtonHover);
    }

    public void onCreateAccountExit(MouseEvent mouseEvent) {
        createAccountButton.setImage(createAccountButtonIdle);
    }

    public void onCreateAccountClick (MouseEvent actionEvent) {

        //checking if mouse was clicked
        createAccount();
    }

    private void createAccount() {
        if ((firstNameEntry.getText().trim().isEmpty()) || (lastNameEntry.getText().trim().isEmpty()) ||
                (usernameEntry.getText().trim().isEmpty()) || (passwordEntry1.getText().trim().isEmpty()) ||
                (passwordEntry2.getText().trim().isEmpty())) {

            //displaying error message
            passwordMessage.setText("");
            registrationMessage.setText("");
            emptyMessage.setText("One or more fields empty!");

            if(firstNameEntry.getText().trim().isEmpty())
                firstNameEntry.setStyle("-fx-border-color: red");

            if (lastNameEntry.getText().trim().isEmpty())
                lastNameEntry.setStyle("-fx-border-color: red");

            if (usernameEntry.getText().trim().isEmpty())
                usernameEntry.setStyle("-fx-border-color: red");

            if (passwordEntry1.getText().trim().isEmpty())
                passwordEntry1.setStyle("-fx-border-color: red");

            if (passwordEntry2.getText().trim().isEmpty())
                passwordEntry2.setStyle("-fx-border-color: red");
        }

        if (!(firstNameEntry.getText().trim().isEmpty()) || !(lastNameEntry.getText().trim().isEmpty()) ||
                !(usernameEntry.getText().trim().isEmpty()) || !(passwordEntry1.getText().trim().isEmpty()) ||
                !(passwordEntry2.getText().trim().isEmpty())) {

//                //displaying error message
//                registrationMessage.setStyle("-fx-text-fill: red");
//                registrationMessage.setText("One or more fields empty!");

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

        if (!(passwordEntry1.getText().equals(passwordEntry2.getText()))) {
            passwordEntry2.setStyle("-fx-border-color: red");
            emptyMessage.setText("");
            registrationMessage.setText("");
            passwordMessage.setText("* Please enter matching passwords.");
        }

        if ((passwordEntry1.getText().equals(passwordEntry2.getText())) &&
                !(firstNameEntry.getText().trim().isEmpty()) && !(lastNameEntry.getText().trim().isEmpty()) &&
                !(usernameEntry.getText().trim().isEmpty()) && !(passwordEntry1.getText().trim().isEmpty()) &&
                !(passwordEntry2.getText().trim().isEmpty())) {

            emptyMessage.setText("");
            passwordMessage.setText("");
            registrationMessage.setText("Successfully Registered! Go back to Login Screen.");

            PlayerInfo newPlayer = new PlayerInfo(firstNameEntry.getText(), lastNameEntry.getText(),
                                                    usernameEntry.getText(), passwordEntry1.getText());
            firstNameEntry.clear();
            lastNameEntry.clear();
            usernameEntry.clear();
            passwordEntry1.clear();
            passwordEntry2.clear();

            System.out.println(newPlayer.toString());
        }
    }

    static class PlayerInfo {
        private final String firstName;
        private final String lastName;
        private final String username;
        private final String password;

        public PlayerInfo(String firstName, String lastName, String username, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerInfo that = (PlayerInfo) o;
            return Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getFirstName(), getLastName(), getUsername(), getPassword());
        }

        /**
         * Returns a string representation of the object. In general, the
         * {@code toString} method returns a string that
         * "textually represents" this object. The result should
         * be a concise but informative representation that is easy for a
         * person to read.
         *
         * @return a string representation of the object.
         */
        @Override
        public String toString() {
            return "PlayerInfo{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
