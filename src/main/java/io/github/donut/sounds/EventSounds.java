package io.github.donut.sounds;

import io.github.donut.proj.utils.Logger;
import javafx.scene.media.AudioClip;

/**
 * Sound effect handler
 * @author Kord Boniadi
 */
public final class EventSounds {
    private static EventSounds instance;
    private final String buttonSound1 = "button_sound_1.mp3";
    private final String buttonSound2 = "button_sound_2.mp3";
    private final String buttonSound3 = "button_sound_3.mp3";
    private final String buttonSound4 = "button_sound_4.mp3";
    private final double BUTTON_VOL = 0.05; // 1 IS FULL
    private AudioClip clip;

    /**
     * @return instance of EventSounds class
     */
    public static EventSounds getInstance() {
        if (instance == null)
            instance = new EventSounds();
        return instance;
    }

    /**
     * Constructor (not used outside class)
     * @author Kord Boniadi
     */
    private EventSounds() {}

    /**
     * Sound effect 1
     * @author Kord Boniadi
     */
    public void playButtonSound1() {
        try {
            clip = new AudioClip(getClass().getResource(buttonSound1).toString());
            clip.setVolume(BUTTON_VOL + .1);
            clip.play();
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    /**
     * Sound effect 2
     * @author Kord Boniadi
     */
    public void playButtonSound2() {
        try {
            clip = new AudioClip(getClass().getResource(buttonSound2).toString());
            clip.setVolume(BUTTON_VOL);
            clip.play();
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    /**
     * Sound effect 3
     * @author Kord Boniadi
     */
    public void playButtonSound3() {
        try {
            clip = new AudioClip(getClass().getResource(buttonSound3).toString());
            clip.setVolume(BUTTON_VOL);
            clip.play();
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    /**
     * Sound effect 4
     * @author Kord Boniadi
     */
    public void playButtonSound4() {
        try {
            clip = new AudioClip(getClass().getResource(buttonSound4).toString());
            clip.setVolume(BUTTON_VOL);
            clip.play();
        } catch (Exception e) {
            Logger.log(e);
        }
    }
}
