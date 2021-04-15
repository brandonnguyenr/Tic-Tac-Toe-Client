package io.github.donut.proj;

import io.github.donut.proj.controllers.AppController;
import io.github.donut.proj.utils.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * <h1>Tic TacToe App</h1>
 * <p>
 *     This is a tic-tac-toe app developed in java using the javafx framework.
 *     Players will be able to play local multiplayer with there friends, as well as
 *     battle against a computer A.I. in two difficulty modes. This app is currently
 *     a work is progress and will continue to add new features every season....
 *     Have fun and happy playing!!
 * </p>
 * @author Kord Boniadi
 * @author Joey Campbell
 * @author Utsav Parajuli
 * @author Grant Goldsworth
 * @author Brandon Nguyen
 * @version 0.1
 * @since 2/13/21
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(GsonWrapper.toJson(MoveData.of("1", 1, 2, "X")));
        new AppController(stage).startApp();
    }

    public static void main(String[] args) {
        launch(args);
    }
}