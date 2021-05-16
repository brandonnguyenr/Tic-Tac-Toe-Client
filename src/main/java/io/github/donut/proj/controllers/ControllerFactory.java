package io.github.donut.proj.controllers;

import io.github.donut.proj.callbacks.AuthorizationCallback;
import io.github.donut.proj.model.SceneName;
import javafx.application.Platform;
import javafx.scene.Scene;

/**
 * Controller Factory class
 * @author Kord Boniadi
 */
public final class ControllerFactory {

    /**
     * Constructor
     */
    private ControllerFactory() {
        //don't delete
    }

    /**
     * Factory method
     * @author Kord Boniadi
     * @param sceneName scene type
     * @return instance of {@link AbstractController}
     */
    public static AbstractController getController(SceneName sceneName) {
        return switch (sceneName) {
            case Main:
                yield createMainController();
            case ABOUT:
                yield createAboutController();
            case START:
                yield createStartController();
//            case BOARD_PAGE:
//                yield createBoardController();
            case LOBBY_PAGE:
                yield createLobbyController();
            case LOGIN_PAGE:
                yield createLoginController();
            case SINGLEPLAYER_PAGE:
                yield createSinglePlayerController();
            case CREATEACCOUNT_PAGE:
                yield createCreateAccountController();
            default:
                throw new IllegalArgumentException("A factory has not been created yet for that controller");
        };
    }

    /**
     * LoginController Helper
     * @author Kord Boniadi
     * @return {@link LoginController}
     */
    private static LoginController createLoginController() {
        LoginController controller = new LoginController();

        controller.setAc(new AuthorizationCallback(() -> {
            Platform.runLater(() -> {
                Scene scene = AppController.getScenes().get(SceneName.Main).getScene(createMainController());
                controller.stage.setScene(scene);
                //clears fields
                controller.getUsernameEntry().clear();
                controller.getPasswordEntry().clear();
            });
        }, () -> {
            Platform.runLater(() -> {
                controller.getUsernameEntry().setStyle("-fx-border-color: red");
                controller.getPasswordEntry().setStyle("-fx-border-color: red");
                controller.getErrorMessage().setText("Incorrect username/password. Try again!");
                //clears fields
                controller.getUsernameEntry().clear();
                controller.getPasswordEntry().clear();
            });
        }));
        return controller;
    }

    /**
     * AboutController Helper
     * @author Kord Boniadi
     * @return {@link AboutUsController}
     */
    private static AboutUsController createAboutController() {
        return new AboutUsController();
    }

//    private static BoardPageController createBoardController() {
//
//        return new BoardPageController();
//    }

    /**
     * LobbyController Helper
     * @author Kord Boniadi
     * @return {@link LobbyController}
     */
    private static LobbyController createLobbyController() {
        return new LobbyController();
    }

    /**
     * MainController Helper
     * @author Kord Boniadi
     * @return {@link MainController}
     */
    private static MainController createMainController() {
        return new MainController();
    }

    /**
     * SinglePlayerController Helper
     * @author Kord Boniadi
     * @return {@link SinglePlayerController}
     */
    private static SinglePlayerController createSinglePlayerController() {
        return new SinglePlayerController();
    }

    /**
     * StartController Helper
     * @author Kord Boniadi
     * @return {@link StartController}
     */
    private static StartController createStartController() {
        return new StartController();
    }

    /**
     * CreateAccountController Helper
     * @author Kord Boniadi
     * @return {@link CreateAccountController}
     */
    private static CreateAccountController createCreateAccountController() {
        CreateAccountController controller = new CreateAccountController();

        controller.setAc(new AuthorizationCallback(() -> {
            Platform.runLater(() -> {
                controller.getUsernameEntry().setStyle("-fx-border-color: khaki");
                controller.getEmptyMessage().setText("");
                controller.getPasswordMessage().setText("");
                controller.getRegistrationMessage().setText("Successfully Registered! Go back to Login Screen.");
                //clears the entry
                controller.getFirstNameEntry().clear();
                controller.getLastNameEntry().clear();
                controller.getUsernameEntry().clear();
                controller.getPasswordEntry1().clear();
                controller.getPasswordEntry2().clear();
            });
        }, () -> {
            Platform.runLater(() -> {
                controller.getUsernameEntry().setStyle("-fx-border-color: red");
                controller.getRegistrationMessage().setText("");
                controller.getEmptyMessage().setText("USERNAME ALREADY EXISTS");
                controller.getPasswordMessage().setText("");
                //clears username entry
                controller.getUsernameEntry().clear();
            });
        }));
        return controller;
    }
}
