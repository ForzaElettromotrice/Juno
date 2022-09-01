package org.juno.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.juno.model.user.User;

import java.io.File;

/**
 * Defines: AudioPlayer, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class AudioPlayer
{
    private static final AudioPlayer INSTANCE = new AudioPlayer();

    private MediaPlayer loginMusic;
    private MediaPlayer menuMusic;
    private MediaPlayer gameMusic;
    private MediaPlayer buttonClick;
    private MediaPlayer cursorSelect;
    private MediaPlayer alertBeep;
    private MediaPlayer cardFlip;

    public enum Sounds
    {
        LOGINMUSIC(0),
        MENUMUSIC(1),
        GAMEMUSIC(2),
        BUTTONCLICK(3),
        CURSORSELECT(4),
        ALERTBEEP(5),
        CARDFLIP(6);

        private final int value;

        Sounds(int val)
        {
            value = val;
        }

        public int getValue()
        {
            return value;
        }
    }

    /**
     * Constructor for AudioPlayer class (private) (singleton)
     */
    private AudioPlayer()
    {
    }

    /**
     * @return the instance of AudioPlayer
     */
    public static AudioPlayer getINSTANCE()
    {
        return INSTANCE;
    }

    /**
     * @return the music volume
     */
    public double getMusicVolume()
    {
        return menuMusic.getVolume();
    }
    /**
     * @return the effects volume
     */
    public double getEffectsVolume()
    {
        return alertBeep.getVolume();
    }

    /**
     * sets the music volume to the given value
     *
     * @param n the new volume value
     */
    public void setMusicVolume(double n)
    {
        User.getInstance().setMusicVolume(n);
        loginMusic.setVolume(n);
        menuMusic.setVolume(n);
        gameMusic.setVolume(n);
    }
    /**
     * sets the effects volume to the given value
     *
     * @param n the new volume value
     */
    public void setEffectsVolume(double n)
    {
        User.getInstance().setEffectsVolume(n);
        buttonClick.setVolume(n);
        cursorSelect.setVolume(n);
        alertBeep.setVolume(n);
        cardFlip.setVolume(n);
    }

    /**
     * loads all the sounds into the AudioPlayer from the filepaths
     */
    public void load()
    {
        loginMusic = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/loginMusic.mp3").toURI().toString()));
        menuMusic = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/menuMusic.mp3").toURI().toString()));
        gameMusic = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/gameMusic.mp3").toURI().toString()));
        buttonClick = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/buttonClick.mp3").toURI().toString()));
        cursorSelect = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/cursorSelect.mp3").toURI().toString()));
        alertBeep = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/alertBeep.mp3").toURI().toString()));
        cardFlip = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/cardFlip.mp3").toURI().toString()));

        // Needed to loop the songs
        loginMusic.setOnEndOfMedia(() -> loginMusic.seek(Duration.ZERO));

        menuMusic.setOnEndOfMedia(
                () -> menuMusic.seek(Duration.ZERO)
        );
        gameMusic.setOnEndOfMedia(
                () -> gameMusic.seek(Duration.ZERO)
        );

    }

    /**
     * plays the sound chosen by the enum
     *
     * @param s the sound to play
     */
    public void play(Sounds s)
    {

        switch (s)
        {
            case LOGINMUSIC ->
            {
                loginMusic.seek(Duration.ZERO);
                loginMusic.play();
            }
            case MENUMUSIC ->
            {
                menuMusic.seek(Duration.ZERO);
                menuMusic.play();
            }
            case GAMEMUSIC ->
            {
                gameMusic.seek(Duration.ZERO);
                gameMusic.play();
            }
            case BUTTONCLICK ->
            {
                buttonClick.seek(Duration.ZERO);
                buttonClick.play();
            }
            case CURSORSELECT ->
            {
                cursorSelect.seek(Duration.ZERO);
                cursorSelect.play();
            }
            case ALERTBEEP ->
            {
                alertBeep.seek(Duration.ZERO);
                alertBeep.play();
            }
            case CARDFLIP ->
            {
                cardFlip.seek(Duration.ZERO);
                cardFlip.play();
            }
        }
    }
    /**
     * stops the sound chosen by the enum
     *
     * @param s the sound to stop
     */
    public void stop(Sounds s)
    {
        switch (s)
        {
            case LOGINMUSIC -> loginMusic.stop();
            case MENUMUSIC -> menuMusic.stop();
            case GAMEMUSIC -> gameMusic.stop();
            case BUTTONCLICK -> buttonClick.stop();
            case CURSORSELECT -> cursorSelect.stop();
            case ALERTBEEP -> alertBeep.stop();
            case CARDFLIP -> cardFlip.stop();
        }
    }
}
