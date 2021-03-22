package io.github.donut.music;

import io.github.donut.proj.utils.Logger;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Singleton class for app music paler
 * @author Kord Boniadi
 */
public final class MusicPlayer {
    private static MusicPlayer instance;
    private ChangeListener<Duration> progressChangeListener;
    private final ProgressBar progress = new ProgressBar();
    private final List<String> SUPPORTED_FILE_TYPES = Arrays.asList(".mp3", ".wav");
    private final String musicDirPath = "src/main/resources/io/github/donut/music/";

    /**
     * @return one instance of MusicPlayer
     */
    public static MusicPlayer getInstance() {
        if (instance == null)
            instance = new MusicPlayer();
        return instance;
    }

    /**
     * Constructor
     * @author Kord Boniadi
     */
    private MusicPlayer() {
        final File dir = new File(musicDirPath);
        if (!dir.exists() && dir.isDirectory()) {
            Logger.log("Cannot find audio source directory: {0}", dir);
            return;
        }

        final List<MediaPlayer> players = new LinkedList<>();
        for (String file : Objects.requireNonNull(dir.list((dir1, name) -> {
            for (String ext : SUPPORTED_FILE_TYPES) {
                if (name.endsWith(ext))
                    return true;
            }
            return false;
        }))) players.add(createPlayer(dir + "/" + file));

        if (players.isEmpty()) {
            Logger.log("no audio found in {0}", dir);
            return;
        }

        for (int i = 0; i < players.size(); i++) {
            final MediaPlayer player = players.get(i);
            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            player.setOnEndOfMedia(() -> {
                player.currentTimeProperty().removeListener(progressChangeListener);
                player.stop();
                nextPlayer.play();
//                nextPlayer.seek(Duration.ZERO);
            });
        }
//        players.get(0).setVolume(0.05);
        players.get(0).play();
        setCurrentlyPlaying(players.get(0));
    }

    /**
     * Helper for creating a MediaPlayer instance for a specific mp3 file
     * @param filePath location of media file
     * @return instance of MediaPlayer for a specific media file
     * @author Kord Boniadi
     */
    private MediaPlayer createPlayer(String filePath) {
        final MediaPlayer player = new MediaPlayer(new Media(new File(filePath).toURI().toString()));
        player.setVolume(0.2);
        player.setOnError(() -> Logger.log("Media error occurred: {0}", player.getError()));
        return player;
    }

    /**
     * Helper class for setting the current active player instance
     * @param newPlayer MediaPlayer instance that will be played
     * @author Kord Boniadi
     */
    private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
        newPlayer.seek(Duration.ZERO);
        progress.setProgress(0);
        progressChangeListener = (observableValue, oldValue, newValue) -> {
            progress.setProgress(newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
        };
        newPlayer.currentTimeProperty().addListener(progressChangeListener);
    }
}