package io.github.donut.proj.controllers;

import io.github.donut.music.MusicPlayer;
import io.github.donut.proj.PlayerType.Human;
import io.github.donut.proj.common.BoardUI;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.utils.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Central Hub where all classes interact with
 * @author Kord Boniadi
 * @author Utsav Parajuli
 */
public class AppController implements IObserver {
    private final Stage mainStage;
    public Scene mainScene;

    /**
     * Constructor
     * @param stage mainStage object received from javafx start() method
     * @author Kord Boniadis
     */
    public AppController(Stage stage) {
//        Logger.init("io/github/donut/proj/configs/logging.properties");
        Logger.init("production");
        this.mainStage = stage;
    }

    /**
     * Initializes starting page for app
     * @throws IOException failure to initialize *.fxml loader files
     * @author Kord Boniadi
     */
    public void startApp() throws IOException {
        MusicPlayer.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("startPage.fxml"));

        Scene start = new Scene(root);
        start.getStylesheets().add((getClass().getResource("styles.css")).toExternalForm());
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
            aboutUsScene.getStylesheets().add((getClass().getResource("styles.css")).toExternalForm());
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
            singlePlayerScene.getStylesheets().add((getClass().getResource("styles.css")).toExternalForm());
            mainStage.setScene(singlePlayerScene);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    /**
     * MultiPlayerPage factory method
     * @param obj instance of Controller with initial params
     * @author Kord Boniadi
     * @author Joey Campbell
     */
    public void createMultiPlayerPage(MultiplayerController obj) {
        EventManager.register(obj, this);

        //loads the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("multiplayerPage.fxml"));

        //setting the controller
        loader.setController(obj);
        try {
            Scene multiplayerScene = new Scene(loader.load());
            multiplayerScene.getStylesheets().add((getClass().getResource("styles.css")).toExternalForm());
            mainStage.setScene(multiplayerScene);
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
            boardScene.getStylesheets().add((getClass().getResource("styles.css")).toExternalForm());
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
    public void creatMenuPage() {
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
        else if (eventType instanceof MultiplayerController)            // checking for Multi player page creation
            createMultiPlayerPage((MultiplayerController) eventType);
        else if (eventType instanceof GameController)                   // checking for Board page creation
            createBoardPage((GameController) eventType);
        else if (eventType instanceof BoardPageController.Finished)     // checking for Menu page creation
            creatMenuPage();
    }
}