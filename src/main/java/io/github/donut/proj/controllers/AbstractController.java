package io.github.donut.proj.controllers;

import io.github.donut.proj.model.Stageable;
import javafx.stage.Stage;

/**
 * A more concrete impl of Stageable
 * @author Kord Boniadi
 */
public abstract class AbstractController implements Stageable {
    protected Stage stage;

    /**
     * SETTER
     * @author Kord Boniadi
     * @param stage instance of main stage
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
