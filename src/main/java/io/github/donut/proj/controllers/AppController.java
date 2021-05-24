package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.Channels;
import io.github.coreutils.proj.messages.PlayerData;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.model.SceneName;
import io.github.donut.proj.utils.FxmlInfo;
import io.github.donut.proj.utils.Logger;
import io.github.donut.proj.utils.Util;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Central Hub where all classes interact with
 * @author Kord Boniadi
 * @author Utsav Parajuli
 */
public class AppController {
    private final String STYLES = "styles/styles.css";
    private final String PROPERTIES = "io/github/donut/proj/configs/logging.properties";
    private final String PRODUCTION = "production";

    private final Stage  mainStage;                                 //main stage for the application
    @Getter
    private static final Stage reactivatePopUp = new Stage();       //pop up window used for reactivation page

    @Getter
    private static final Map<SceneName, FxmlInfo> scenes = new HashMap<>();

    private static final PlayerData player = new PlayerData();      //player data

    /**
     * Constructor
     * @param stage mainStage object received from javafx start() method
     * @author Kord Boniadi
     */
    public AppController(Stage stage) {
        Logger.init(PROPERTIES);
//        Logger.init(PRODUCTION);
        this.mainStage = stage;
        final MessagingAPI api = GlobalAPIManager.getInstance().getApi();

        api.onclose(() -> {
            System.out.println("api is now dead.");
            Platform.runLater(mainStage::close);
        });

        this.mainStage.setOnCloseRequest((event) -> {
            api.free();
        });

        scenes.put(SceneName.Main, new FxmlInfo(SceneName.Main.toString(), STYLES, SceneName.Main, mainStage));
        scenes.put(SceneName.START, new FxmlInfo(SceneName.START.toString(), STYLES, SceneName.START, mainStage));
        scenes.put(SceneName.LOGIN_PAGE, new FxmlInfo(SceneName.LOGIN_PAGE.toString(), STYLES, SceneName.LOGIN_PAGE, mainStage));
        scenes.put(SceneName.CREATEACCOUNT_PAGE, new FxmlInfo(SceneName.CREATEACCOUNT_PAGE.toString(), STYLES, SceneName.CREATEACCOUNT_PAGE, mainStage));
        scenes.put(SceneName.ABOUT, new FxmlInfo(SceneName.ABOUT.toString(), STYLES, SceneName.ABOUT, mainStage));
        scenes.put(SceneName.SINGLEPLAYER_PAGE, new FxmlInfo(SceneName.SINGLEPLAYER_PAGE.toString(), STYLES, SceneName.SINGLEPLAYER_PAGE, mainStage));
        scenes.put(SceneName.LOBBY_PAGE, new FxmlInfo(SceneName.LOBBY_PAGE.toString(), STYLES, SceneName.LOBBY_PAGE, mainStage));
        scenes.put(SceneName.BOARD_PAGE, new FxmlInfo(SceneName.BOARD_PAGE.toString(), STYLES, SceneName.BOARD_PAGE, mainStage));
        scenes.put(SceneName.PORTAL_PAGE, new FxmlInfo(SceneName.PORTAL_PAGE.toString(), STYLES, SceneName.PORTAL_PAGE, mainStage));
        scenes.put(SceneName.HISTORY_PAGE, new FxmlInfo(SceneName.HISTORY_PAGE.toString(), STYLES, SceneName.HISTORY_PAGE, mainStage));
        scenes.put(SceneName.UPDATE_ACCOUNT_PAGE, new FxmlInfo(SceneName.UPDATE_ACCOUNT_PAGE.toString(), STYLES, SceneName.UPDATE_ACCOUNT_PAGE, mainStage));
        scenes.put(SceneName.REACTIVATE_ACCOUNT_PAGE, new FxmlInfo(SceneName.REACTIVATE_ACCOUNT_PAGE.toString(), STYLES, SceneName.REACTIVATE_ACCOUNT_PAGE, mainStage));
        scenes.put(SceneName.WAITING_PAGE, new FxmlInfo(SceneName.WAITING_PAGE.toString(), STYLES, SceneName.WAITING_PAGE, mainStage));

        //settings for popup page
        initializeReactivatePopUp();
    }

    private void initializeReactivatePopUp() {
        reactivatePopUp.initOwner(mainStage);
        reactivatePopUp.initModality(Modality.APPLICATION_MODAL);
        reactivatePopUp.setHeight(200);
        reactivatePopUp.setWidth(480);
        reactivatePopUp.centerOnScreen();
        reactivatePopUp.setTitle("Re-activate Account");
        reactivatePopUp.setResizable(false);
    }

//    /**
//     * @author Kord Boniadi
//     * @param sceneName name of scene (enum)
//     * @param info fxml info bundle
//     */
//    public static void updateScenes(SceneName sceneName, FxmlInfo info) {
//        scenes.put(sceneName, info);
//    }

    /**
     * Initializes starting page for app
     * @author Kord Boniadi
     */
    public void startApp() {
//        MusicPlayer.getInstance();
        Scene scene = scenes.get(SceneName.START).getScene(true, false);
        scene.getRoot().requestFocus();
        mainStage.setScene(scene);
        mainStage.setUserData(this);
        mainStage.setTitle(Util.TITLE);
        mainStage.setResizable(false);
        mainStage.show();
        Logger.log("program started..");
    }

    /**
     * This method will return the playerID of player
     * @return playerID
     */
    public static String getPlayerID() {
        return player.getPlayerID();
    }

    /**
     * This method will return the PlayerData
     * @return PlayerData
     */
    public static PlayerData getPlayer(String channel) {
        PlayerData result = new PlayerData(player);
        result.setChannel(channel);
        return result;
    }

    /**
     * This method will set the username of player
     */
    public static void setUserName(String username) {
        player.setPlayerUserName(username);
    }


    /**
     * This method will return the username of player
     * @return username
     */
    public static String getUserName() {
        return player.getPlayerUserName();
    }
}