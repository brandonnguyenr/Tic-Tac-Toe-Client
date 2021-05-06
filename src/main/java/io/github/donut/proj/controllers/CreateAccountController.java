package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.utils.RestrictiveTextField;
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
 * @version : 0.2
 */
public class CreateAccountController implements Initializable, ISubject {

    public Label createAccountTitle;
    public Label firstNameLabel;
    public Label lastNameLabel;
    public Label usernameLabel;
    public Label passwordLabel1;
    public Label passwordLabel2;
    public Label registrationMessage;
    public Label emptyMessage;
    public Label passwordMessage;

    public ImageView  backButton;
    public ImageView  createAccountButton;

    public BorderPane createAccountPage;
    public TextField firstNameEntry;
    public TextField  lastNameEntry;
    public TextField  usernameEntry;

    public PasswordField passwordEntry1;
    public PasswordField passwordEntry2;

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


    @Override
    /**
     * Initialize the class.
     * @author: Utsav Parajuli
     */
    public void initialize(URL location, ResourceBundle resources) {
        createAccountTitle.setText("CREATE YOUR ACCOUNT");

        firstNameLabel.setText("First Name  ");
        lastNameLabel.setText ("Last Name   ");
        usernameLabel.setText ("Username    ");
        passwordLabel1.setText("Password    ");
        passwordLabel2.setText("Confirm     \nPassword");
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
    public CreateAccountController() {
        instance = this;
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Utsav Parajuli
     */
    public void onBackButtonClick(MouseEvent actionEvent) throws IOException {
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
     */
    public void onEnterPressed(KeyEvent keyEvent) {

        //checking if keycode was enter
        if(keyEvent.getCode() == KeyCode.ENTER) {
            createAccount();
        }
    }


    /**
     * Event handler for create account button hover
     * @param mouseEvent mouse event
     *
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
     * Event handler for create account button clicked
     *
     * @param actionEvent on click
     * @author Utsav Parajuli
     */
    public void onCreateAccountClick (MouseEvent actionEvent) {
        //if mouse was clicked
        createAccount();
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
        if  ((firstNameEntry.getText().trim().isEmpty()) || (lastNameEntry.getText().trim().isEmpty())  ||
             (usernameEntry.getText().trim().isEmpty())  || (passwordEntry1.getText().trim().isEmpty()) ||
             (passwordEntry2.getText().trim().isEmpty())) {

            //displaying error message
            passwordMessage.setText("");
            registrationMessage.setText("");
            emptyMessage.setText("One or more fields empty!");

            //These conditional statements will check which fields are empty
            //Empty fields will be highlighted red
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
        }   //end of first condition

        //This conditional statement will check if any of the entries are filled. If the entries are filled
        //the text box will be highlighted back to normal
        if  (!(firstNameEntry.getText().trim().isEmpty()) || !(lastNameEntry.getText().trim().isEmpty())  ||
             !(usernameEntry.getText().trim().isEmpty())  || !(passwordEntry1.getText().trim().isEmpty()) ||
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
             !(firstNameEntry.getText().trim().isEmpty()) && !(lastNameEntry.getText().trim().isEmpty())  &&
             !(usernameEntry.getText().trim().isEmpty())  && !(passwordEntry1.getText().trim().isEmpty()) &&
             !(passwordEntry2.getText().trim().isEmpty())) {

            //displays the success message
            emptyMessage.setText("");
            passwordMessage.setText("");
            registrationMessage.setText("Successfully Registered! Go back to Login Screen.");

            //TODO: from here send this message to the database and register the user. Made a PlayerInfo class that has
            // all of the users info
            PlayerInfo newPlayer = new PlayerInfo(firstNameEntry.getText(), lastNameEntry.getText(),
                                                  usernameEntry.getText(), passwordEntry1.getText());

            //clears the entry
            firstNameEntry.clear();
            lastNameEntry.clear();
            usernameEntry.clear();
            passwordEntry1.clear();
            passwordEntry2.clear();

            //TEST
            System.out.println(newPlayer);
        }
    }

    /**
     * This class will include all of the info of the user that registered. Can be used as a data packet to send to
     * database
     * @author Utsav Parajuli
     */
    static class PlayerInfo {
        private final String firstName;
        private final String lastName;
        private final String username;
        private final String password;

        /**
         * Constructor
         * @param firstName: first name
         * @param lastName: last name
         * @param username: username
         * @param password: password
         */
        public PlayerInfo(String firstName, String lastName, String username, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
        }

        /**
         * Returns firstname
         * @return firstname
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Returns last name
         * @return last name
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Returns username
         * @return username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Returns password
         * @return password
         */
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