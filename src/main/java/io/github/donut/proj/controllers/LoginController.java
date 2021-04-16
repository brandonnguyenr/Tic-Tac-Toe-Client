package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.ISubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable, ISubject {

    @FXML
    public Label test;

    @FXML
    public Button testButton;
    public BorderPane loginPage;

    /*
     *  // By pass the need for this:
     *  FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
     *  Parent root = loader.load();
     *
     *  Controller myController = loader.getController();
     *********************************************************************/
    private static LoginController instance;

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
        test.setText("TESTING LOGIN PAGE");
    }

    public void onLoginClicked (ActionEvent actionEvent) throws IOException {
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
}
