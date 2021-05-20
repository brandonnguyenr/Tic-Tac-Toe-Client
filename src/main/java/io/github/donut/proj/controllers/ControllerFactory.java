package io.github.donut.proj.controllers;

import io.github.coreutils.proj.messages.Channels;
import io.github.donut.proj.callbacks.AuthorizationCallback;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.callbacks.RoomListCallback;
import io.github.donut.proj.callbacks.UpdatesCallback;
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
            case PORTAL_PAGE:
                yield createProfilePortalController();
            case HISTORY_PAGE:
                yield createPlayerHistoryController();
            case UPDATE_ACCOUNT_PAGE:
                yield createUpdateAccountController();
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
        LobbyController controller = new LobbyController();
        controller.setCreateHandler((info) -> {

        });
        return controller;
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
     * ProfilePortalController Helper
     * @author Joey Campbell
     * @return {@link ProfilePortalController}
     */
    private static ProfilePortalController createProfilePortalController() { return new ProfilePortalController(); }

    /**
     * PlayerHistoryController Helper
     * @author Joey Campbell
     * @return {@link PlayerHistoryController}
     */
    private static PlayerHistoryController createPlayerHistoryController() { return new PlayerHistoryController(); }

    /**
     * UpdateAccountController Helper
     * @author Joey Campbell
     * @author Utsav Parajuli
     * @return {@link UpdateAccountController}
     */
    private static UpdateAccountController createUpdateAccountController() {
        UpdateAccountController controller = new UpdateAccountController();

        controller.setUc(new UpdatesCallback((event) -> {
            //runs if the state was resolved
            Platform.runLater(() -> {
                if (event.getType().equalsIgnoreCase("USERNAME")) {             //username update successful
                    controller.getCurrentUserNameTab1().setStyle("-fx-border-color: khaki");
                    controller.getNewUserNameTab1().setStyle("-fx-border-color: khaki");
                    controller.getConfirmUserNameTab1().setStyle("-fx-border-color: khaki");

                    controller.getCurrentUsernameErrorTab1().setText("");
                    controller.getDifferentUsernameErrorTab1().setText("");
                    controller.getSuccessfulUpdateTab1().setText("ACCOUNT UPDATED!");

                    //clearing screen
                    controller.getCurrentUserNameTab1().clear();
                    controller.getNewUserNameTab1().clear();
                    controller.getConfirmUserNameTab1().clear();

                } else if (event.getType().equalsIgnoreCase("PERSONAL")) {      //personal info update successful
                    controller.getUserNameTab2().setStyle("-fx-border-color: khaki");
                    controller.getFirstNameEntryTab2().setStyle("-fx-border-color: khaki");
                    controller.getLastNameEntryTab2().setStyle("-fx-border-color: khaki");

                    controller.getUsernameErrorTab2().setText("");
                    controller.getSuccessfulUpdateTab2().setText("ACCOUNT UPDATED!");

                    //clearing screen
                    controller.getUserNameTab2().clear();
                    controller.getFirstNameEntryTab2().clear();
                    controller.getLastNameEntryTab2().clear();

                } else if (event.getType().equalsIgnoreCase("PASSWORD")) {      //password update successful
                    controller.getUserNameEntryTab3().setStyle("-fx-border-color: khaki");
                    controller.getCurrentPasswordTab3().setStyle("-fx-border-color: khaki");
                    controller.getNewPasswordEntryTab3().setStyle("-fx-border-color: khaki");
                    controller.getConfirmPasswordEntryTab3().setStyle("-fx-border-color: khaki");

                    controller.getUsernameErrorTab3().setText("");
                    controller.getDifferentPasswordErrorTab3().setText("");
                    controller.getSuccessfulUpdateTab3().setText("ACCOUNT UPDATED!");

                    controller.getUserNameEntryTab3().clear();
                    controller.getCurrentPasswordTab3().clear();
                    controller.getNewPasswordEntryTab3().clear();
                    controller.getConfirmPasswordEntryTab3().clear();
                }
            });

        }, ((event) -> {
            //the update was unsuccessful
            Platform.runLater(() -> {
                if (event.getType().equalsIgnoreCase("USERNAME")) {             //username update error
                    controller.getCurrentUserNameTab1().setStyle("-fx-border-color: red");
                    controller.getNewUserNameTab1().setStyle("-fx-border-color: red");
                    controller.getConfirmUserNameTab1().setStyle("-fx-border-color: red");

                    controller.getDifferentUsernameErrorTab1().setText("");
                    controller.getSuccessfulUpdateTab1().setText("");
                    controller.getCurrentUsernameErrorTab1().setText("Incorrect username/Username already exists!");
                } else if (event.getType().equalsIgnoreCase("PERSONAL")) {      //personal update error
                    controller.getUserNameTab2().setStyle("-fx-border-color: red");

                    controller.getSuccessfulUpdateTab2().setText("");
                    controller.getUsernameErrorTab2().setText("Username does not exist!");
                } else if (event.getType().equalsIgnoreCase("PASSWORD")) {      //password update error
                    controller.getUserNameEntryTab3().setStyle("-fx-border-color: red");
                    controller.getCurrentPasswordTab3().setStyle("-fx-border-color: red");

                    controller.getDifferentPasswordErrorTab3().setText("");
                    controller.getSuccessfulUpdateTab3().setText("");
                    controller.getUsernameErrorTab3().setText("Username/Password do not match");
                }
            });
        })));
        return controller;
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
