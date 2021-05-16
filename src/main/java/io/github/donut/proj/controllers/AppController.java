package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.Channels;
import io.github.donut.music.MusicPlayer;
import io.github.donut.proj.PlayerType.Human;
import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.model.SceneName;
import io.github.donut.proj.utils.FxmlInfo;
import io.github.donut.proj.utils.Logger;
import io.github.donut.proj.utils.Util;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Central Hub where all classes interact with
 * @author Kord Boniadi
 * @author Utsav Parajuli
 */
public class AppController implements IObserver {
    private static final String STYLES = "styles/styles.css";
    private static final String PROPERTIES = "io/github/donut/proj/configs/logging.properties";
    private static final String PRODUCTION = "production";

    private MessagingAPI api;           //instance of the API this instance is universal for one client
    public  Scene        mainScene;
    private final Stage  mainStage;
    @Getter
    private static final Map<SceneName, FxmlInfo> scenes = new HashMap<>();

    /**
     * Constructor
     * @param stage mainStage object received from javafx start() method
     * @author Kord Boniadi
     */
    public AppController(Stage stage) {
        Logger.init(PROPERTIES);
//        Logger.init(PRODUCTION);
        this.mainStage = stage;
        //creating instance of api and subscribing to appropriate channels
        api = new MessagingAPI();

        //channels the api is subscribed to
        api.subscribe()
                .channels(Channels.PRIVATE + api.getUuid())
                .execute();

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
    }

    /**
     * This method will return the instance of api
     * @return api : the api
     */
    public MessagingAPI getApi() {
        return api;
    }

    /**
     * Sets the main scene for the main menu page as we use this scene as a cache for back buttons
     * @param scene : the main menu scene
     */
    public void setMainScene(Scene scene) {
        this.mainScene = scene;
    }

    public static void updateScenes(SceneName sceneName, FxmlInfo info) {
        scenes.put(sceneName, info);
    }
    /**
     * Initializes starting page for app
     * @throws IOException failure to initialize *.fxml loader files
     * @author Kord Boniadi
     */
    public void startApp() throws IOException {
        MusicPlayer.getInstance();
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
     * LoginPage factory method
     * @param obj instance of Controller with initial params
     * @author Utsav Parajuli
     */
    public void createLoginPage(LoginController obj) {
        obj.setApi(api);
        //loads the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));

        //setting the controller
        loader.setController(obj);
        try {
            Scene loginPageScene = new Scene(loader.load(), Util.APP_WIDTH, Util.APP_HEIGHT);
            loginPageScene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());
            mainStage.setScene(loginPageScene);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    /**
     * CreateAccount factory method
     * @param obj instance of Controller with initial params
     * @author Utsav Parajuli
     */
    public void createCreateAccountPage(CreateAccountController obj) {
        obj.setApi(api);
        //loads the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAccountPage.fxml"));

        //setting the controller
        loader.setController(obj);
        try {
            Scene createAccountPageScene = new Scene(loader.load(), Util.APP_WIDTH, Util.APP_HEIGHT);
            createAccountPageScene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());
            mainStage.setScene(createAccountPageScene);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    /**
     * Create Menu Page factory method
     * @param obj instance of Controller with initial params
     * @author Utsav Parajuli
     */
    public void createMenuPage(MainController obj) {
        //loads the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuPage.fxml"));

        //setting the controller
        loader.setController(obj);
        try {
            Scene menuPageScene = new Scene(loader.load(), Util.APP_WIDTH, Util.APP_HEIGHT);
            menuPageScene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());

            setMainScene(menuPageScene);
            mainStage.setScene(menuPageScene);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    /**
     * AboutPage factory method
     * @param obj instance of Controller with initial params
     * @author Kord Boniadi
     * @author Utsav Parajuli
     */
    public void createAboutPage(AboutUsController obj) {
        EventManager.register(obj, this);

        //loads fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("aboutUs.fxml"));

        //setting the main stage to the about us page scene
        loader.setController(obj);
        try {
            Scene aboutUsScene = new Scene(loader.load(), Util.APP_WIDTH, Util.APP_HEIGHT);
            aboutUsScene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());
            mainStage.setScene(aboutUsScene);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    /**
     * SinglePlayerPage factory method
     * @param obj instance of Controller with initial params
     * @author Kord Bonaidi
     * @author Utsav Parajuli
     */
    public void createSinglePlayerPage(SinglePlayerController obj) {
        EventManager.register(obj, this);

        //loads the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("singlePlayerPage.fxml"));

        //setting the controller
        loader.setController(obj);
        try {
            Scene singlePlayerScene = new Scene(loader.load(), Util.APP_WIDTH, Util.APP_HEIGHT);
            singlePlayerScene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());
            mainStage.setScene(singlePlayerScene);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    /**
     * LobbyPage factory method
     * @param obj instance of Controller with initial params
     * @author Kord Boniadi
     * @author Utsav Parajuli
     * @author Joey Campbell
     */
    public void createLobbyPage(LobbyController obj) {
        EventManager.register(obj, this);

        //loads the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lobbyPage.fxml"));

        //setting the controller
        loader.setController(obj);
        try {
            Scene lobbyScene = new Scene(loader.load(), Util.APP_WIDTH, Util.APP_HEIGHT);
            lobbyScene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());
            mainStage.setScene(lobbyScene);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    /**
     * BoardPage factory method
     * @param obj instance of GameController with initial params
     * @author Kord Boniadi
     */
    public void createBoardPage(GameController obj) {
        BoardUI boardUI = new BoardUI();
        BoardPageController controller = new BoardPageController(boardUI, obj);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("boardPage.fxml"));
        loader.setController(controller);
        try {
            Scene boardScene = new Scene(loader.load(), Util.APP_WIDTH, Util.APP_HEIGHT);
            boardScene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());
            mainStage.setScene(boardScene);
        } catch (IOException e) {
            Logger.log(e);
        }

        if (obj.getPlayer1().getPlayerType() instanceof Human) {
            EventManager.register(boardUI, (Human) obj.getPlayer1().getPlayerType());
        }

        if (obj.getPlayer2().getPlayerType() instanceof Human) {
            EventManager.register(boardUI, (Human) obj.getPlayer2().getPlayerType());
        }

        EventManager.register(boardUI, obj.getPlayer1());
        EventManager.register(boardUI, obj.getPlayer2());
        EventManager.register(controller, this);
        EventManager.register(obj, boardUI);
        obj.startGame();
    }

    /**
     * MenuPage factory method
     * @author Kord Boniadi
     */
    public void recreateMenuPage() {
        EventManager.cleanup();
        mainStage.setScene(mainScene);
        EventManager.register(new MainController(), this);
    }

    /**
     * Receives data from a subscribed subject
     * @param eventType object container
     * @author Kord Boniadi
     * @author Utsav Parajuli
     */
    @Override
    public void update(Object eventType) {
        if (eventType instanceof AboutUsController)                     // checking for About page creation
            createAboutPage((AboutUsController) eventType);
        else if (eventType instanceof SinglePlayerController)           // checking for Single player page creation
            createSinglePlayerPage((SinglePlayerController) eventType);
        else if (eventType instanceof LobbyController)                  // checking for Multi player page creation
            createLobbyPage((LobbyController) eventType);
        else if (eventType instanceof GameController)                   // checking for Board page creation
            createBoardPage((GameController) eventType);
        else if (eventType instanceof BoardPageController.Finished)     // checking for Menu page creation
            recreateMenuPage();
        else if (eventType instanceof LoginController)                  // checking for Login page creation
            createLoginPage((LoginController) eventType);
        else if (eventType instanceof CreateAccountController)          // checking for Create Account page creation
            createCreateAccountPage((CreateAccountController) eventType);
        else if (eventType instanceof MainController)                   // checking for Main Menu page creation
            createMenuPage((MainController) eventType);
    }
}