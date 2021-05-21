package io.github.donut.proj.utils;

import io.github.donut.proj.controllers.AbstractController;
import io.github.donut.proj.controllers.AppController;
import io.github.donut.proj.controllers.ControllerFactory;
import io.github.donut.proj.model.Stageable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;
import java.util.logging.Level;

/**
 * This class handles a fxml loading
 * @author Kord Boniadi
 */
public final class FxmlLoad {
    /**
     * Constructor
     * @author Kord Boniadi
     */
    private FxmlLoad() {
        // don't delete
    }

    /**
     * Get controller instance linked with Scene
     * @author Kord Boniadi
     * @param info FxmlInfo instance {@link FxmlInfo}
     * @param <T> generic return type
     * @return controller class
     * @throws IOException thrown here: {@link #getLoader(String)}
     */
    public static <T> T getController(FxmlInfo info) throws IOException {
        FXMLLoader loader = getLoader(info.getResourceName());
        return loader.getController();
    }

    /**
     * load scene method
     * <p>
     *     <code>load(info, controller: null, isFxml);</code>
     * </p>
     * @author Kord Boniadi
     * @param info fxml info {@link FxmlInfo}
     * @param isFxml {@code true if { using controller injection } else false}
     * @return instance of Scene
     * @throws IOException thrown here: {@link #getLoader(String)}
     */
    public static Scene load(FxmlInfo info, boolean isFxml) throws IOException {
        return load(info, null, isFxml);
    }

    /**
     * load scene method
     * @author Kord Boniadi
     * @param info fxml info {@link FxmlInfo}
     * @param controller set custom controller
     * @param isFxml {@code true if { using controller injection } else false}
     * @return instance of Scene
     * @throws IOException thrown here: {@link #getLoader(String)}
     */
    public static Scene load(FxmlInfo info, AbstractController controller, boolean isFxml) throws IOException {
        if (info.isCached()) {
            System.out.println("this should probably not be seen: Scene load()");
            return info.getScene();
        }

        FXMLLoader loader = getLoader(info.getResourceName());

        if (!isFxml && controller != null) {
            loader.setController(controller);
        } else if (!isFxml) {
            loader.setController(ControllerFactory.getController(info.getSceneName()));
        }

        Scene scene = new Scene(loader.load(), Util.APP_WIDTH, Util.APP_HEIGHT);
//        info.setScene(scene);
//        AppController.updateScenes(info.getSceneName(), info);
        Stageable refController = loader.getController();
        if (refController != null) {
            refController.setStage(info.getStage());
        }

        if (info.getStyleSheet() != null)
            scene.getStylesheets().add((Objects.requireNonNull(FxmlLoad.class.getResource(info.getStyleSheet()))).toExternalForm());
        return scene;
    }

    /**
     * Helper for FXMLLoader retrieval
     * @author Kord Boniadi
     * @param resourceName fxml file path { relative }
     * @return FXMLLoader instance {@link FXMLLoader}
     * @throws IOException URL {@code null}
     */
    private static FXMLLoader getLoader(String resourceName) throws IOException {
        URL url = FxmlLoad.class.getResource(resourceName);

        if (url == null) {
            Logger.log("The URL for the resource \"{0}\" was not found", resourceName);
            debugInfo(resourceName);
            throw new IOException("URL returned null");
        }

        return new FXMLLoader(url);
    }

    /**
     * Helper { debug only }
     * @author Kord Boniadi
     * @param resourceName fxml file path { relative }
     */
    private static void debugInfo(String resourceName) {
        Logger.log(Level.SEVERE, "Working Directory = {0}", System.getProperty("user.dir"));
        Logger.log(Level.SEVERE, "Resources for {0}", resourceName);
        try {
            Enumeration<URL> urls = ClassLoader.getSystemClassLoader().getResources(resourceName);
            while (urls.hasMoreElements()) {
                Logger.log(Level.SEVERE, urls.nextElement().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
