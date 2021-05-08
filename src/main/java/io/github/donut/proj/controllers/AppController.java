package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.coreutils.proj.messages.Channels;
import io.github.donut.music.MusicPlayer;
import io.github.donut.proj.PlayerType.Human;
import io.github.donut.proj.callbacks.AuthorizationCallback;
import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.utils.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * Central Hub where all classes interact with
 * @author Kord Boniadi
 * @author Utsav Parajuli
 */
public class AppController implements IObserver {
    private final Stage mainStage;
    public Scene mainScene;
    private MessagingAPI api;
    private AuthorizationCallback ac;

    /**
     * Constructor
     * @param stage mainStage object received from javafx start() method
     * @author Kord Boniadis
     */
    public AppController(Stage stage) {
        Logger.init("io/github/donut/proj/configs/logging.properties");
        //Logger.init("production");
        this.mainStage = stage;

        try {
            //creating instance of API
            api = new MessagingAPI();
            ac = new AuthorizationCallback();

            api.subscribe()
                    .channels(Channels.AUTHOR_VALIDATE.toString(),
                              Channels.AUTHOR_CREATE.toString(),
                              Channels.PRIVATE + api.getUuid())
                    .execute();

            api.addEventListener(ac, Channels.AUTHOR_VALIDATE.toString(), Channels.AUTHOR_CREATE.toString(),
                                 Channels.PRIVATE + api.getUuid());
        } catch (IOException e) {
            api.free();
            e.printStackTrace();
        }
    }

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
    /**
     * Initializes starting page for app
     * @throws IOException failure to initialize *.fxml loader files
     * @author Kord Boniadi
     */
    public void startApp() throws IOException {
        MusicPlayer.getInstance();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startPage.fxml")));

        Scene start = new Scene(root);
        start.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());
        mainStage.setUserData(this);
        root.requestFocus();
        // set the title of the stage
        mainStage.setTitle("Donut Tic Tac Toe");
        mainStage.setScene(start);
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
        //EventManager.register(obj, this);
        //loads the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));

        //setting the controller
        loader.setController(obj);
        try {
            Scene loginPageScene = new Scene(loader.load());
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
        //loads the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAccountPage.fxml"));

        //setting the controller
        Logger.log("BRO");
        loader.setController(obj);
        System.out.println(obj);
        System.out.println(loader);
        try {
            System.out.println("1");
            Scene createAccountPageScene = new Scene(loader.load());
            System.out.println("2");
            createAccountPageScene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("styles.css"))).toExternalForm());
            System.out.println("3");
            mainStage.setScene(createAccountPageScene);
            System.out.println("4");
        } catch (IOException e) {
            System.out.println("5");
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
            Scene menuPageScene = new Scene(loader.load());
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
            Scene aboutUsScene = new Scene(loader.load());
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
            Scene singlePlayerScene = new Scene(loader.load());
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
            Scene lobbyScene = new Scene(loader.load());
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
            Scene boardScene = new Scene(loader.load());
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
        EventManager.register(MainController.getInstance(), this);
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
        else if (eventType instanceof CreateAccountController) {       // checking for Create Account page creation
            createCreateAccountPage((CreateAccountController) eventType);
        }
        else if (eventType instanceof MainController)                   // checking for Main Menu page creation
            createMenuPage((MainController) eventType);
    }
}