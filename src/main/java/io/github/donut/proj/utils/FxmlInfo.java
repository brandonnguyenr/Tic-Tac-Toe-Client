package io.github.donut.proj.utils;

import io.github.donut.proj.controllers.AbstractController;
import io.github.donut.proj.model.SceneName;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * This class handles a fxml scene
 * @author Kord Boniadi
 */
public class FxmlInfo {
    @Getter
    private final String resourceName;
    @Getter
    private final SceneName sceneName;
    @Getter
    private final Stage stage;
    @Getter
    private final String styleSheet;
    @Setter
    private Scene scene;

    /**
     * Constructor
     * @author Kord Boniadi
     * @param resourceName fxml file path { relative }
     * @param sceneName Scene name Scene name {@link SceneName}
     * @param stage instance of {@link Stage}
     */
    public FxmlInfo(String resourceName, SceneName sceneName, Stage stage) {
        this.resourceName = resourceName;
        this.styleSheet = null;
        this.sceneName = sceneName;
        this.stage = stage;
    }

    /**
     * Constructor
     * @param resourceName fxml file path { relative }
     * @param styleSheet CSS file path { relative }
     * @param sceneName Scene name {@link SceneName}
     * @param stage instance of main stage
     */
    public FxmlInfo(String resourceName, String styleSheet, SceneName sceneName, Stage stage) {
        this.resourceName = resourceName;
        this.styleSheet = styleSheet;
        this.sceneName = sceneName;
        this.stage = stage;
    }

    /**
     * @author Kord Boniadi
     * @return if current scene is cached
     */
    public boolean isCached() {
        return scene != null;
    }

    /**
     * Clears current scene from memory
     * @author Kord Boniadi
     */
    public void clearCache() {
        this.scene = null;
    }

    /**
     * Default getScene() method. Equivalent to calling:
     * <p>
     *     <code>worker(true, null, true);</code>
     * </p>
     * @author Kord Boniadi
     * @see #worker(boolean, AbstractController, boolean)
     * @return scene linked with current fxml file
     */
    public Scene getScene() {
        return worker(true, null, true);
    }

    /**
     * Equivalent to calling:
     * <p>
     *     <code>worker(isFxml, null, true);</code>
     * </p>
     * @author Kord Boniadi
     * @param isFxml {@code true if { using controller injection } else false}
     * @return scene linked with current fxml file
     */
    public Scene getScene(boolean isFxml) {
        return worker(isFxml, null, true);
    }

    /**
     * Equivalent to calling:
     * <p>
     *     <code>worker(false, controller, true);</code>
     * </p>
     * @author Kord Boniadi
     * @param controller set custom controller
     * @return scene linked with current fxml file
     */
    public Scene getScene(AbstractController controller) {
        return worker(false, controller, true);
    }

    /**
     * Equivalent to calling:
     * <p>
     *     <code>worker(isFxml, null, isCache);</code>
     * </p>
     * @author Kord Boniadi
     * @param isFxml {@code true if { using controller injection } else false}
     * @param isCache {@code true if { want to save } else false}
     * @return scene linked with current fxml file
     */
    public Scene getScene(boolean isFxml, boolean isCache) {
        return worker(isFxml, null, isCache);
    }

    /**
     * Equivalent to calling:
     * <p>
     *     <code>worker(false, controller, true);</code>
     * </p>
     * @author Kord Boniadi
     * @param controller set custom controller
     * @param isCache {@code true if { want to save } else false}
     * @return scene linked with current fxml file
     */
    public Scene getScene(AbstractController controller, boolean isCache) {
        return worker(false, controller, isCache);
    }

    /**
     * Worker method
     * @author Kord Boniadi
     * @param isFxml {@code true if { using controller injection } else false}
     * @param controller set custom controller
     * @param isCache {@code true if { want to save } else false}
     * @return scene linked with current fxml file
     */
    private Scene worker(boolean isFxml, AbstractController controller, boolean isCache) {
        if (isCache) {
            if (scene == null) {
                try {
                    scene = FxmlLoad.load(this, controller, isFxml);
                    if (Logger.isLoggingEnabled()) {
                        Logger.log("{0} has been built", sceneName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return scene;
        }

        Scene scene = null;
        try {
            scene = FxmlLoad.load(this, controller, isFxml);
            if (Logger.isLoggingEnabled()) {
                Logger.log("{0} has been built", sceneName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }
}
