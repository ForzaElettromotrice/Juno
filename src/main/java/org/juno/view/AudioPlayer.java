package org.juno.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * Defines: AudioPlayer, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class AudioPlayer
{
    private static final AudioPlayer INSTANCE = new AudioPlayer();
    private static final String ERROR_MESSAGE = "Questo suono non esiste!!";

    private MediaPlayer menuMusic;
    private MediaPlayer gameMusic;
    private MediaPlayer buttonClick;
    private MediaPlayer cursorSelect;
    private MediaPlayer alertBeep;
    private MediaPlayer cardFlip;

    public enum Sounds
    {
        MENUMUSIC(0),
        GAMEMUSIC(1),
        BUTTONCLICK(2),
        CURSORSELECT(3),
        ALERTBEEP(4),
        CARDFLIP(5);

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

    private AudioPlayer()
    {
    }

    public static AudioPlayer getINSTANCE()
    {
        return INSTANCE;
    }


    public double getMusicVolume()
    {
        return menuMusic.getVolume();
    }
    public double getEffectsVolume()
    {
        return alertBeep.getVolume();
    }


    public void setMusicVolume(double n)
    {
        menuMusic.setVolume(n);
        gameMusic.setVolume(n);
    }
    public void setEffectsVolume(double n)
    {
        buttonClick.setVolume(n);
        cursorSelect.setVolume(n);
        alertBeep.setVolume(n);
        cardFlip.setVolume(n);
    }


    public void load()
    {
        menuMusic = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/menuMusic.mp3").toURI().toString()));
        gameMusic = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/gameMusic.mp3").toURI().toString()));
        buttonClick = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/buttonClick.mp3").toURI().toString()));
        cursorSelect = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/cursorSelect.mp3").toURI().toString()));
        alertBeep = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/alertBeep.mp3").toURI().toString()));
        cardFlip = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/cardFlip.mp3").toURI().toString()));

        // Needed to loop the songs

        menuMusic.setOnEndOfMedia(
                () -> menuMusic.seek(Duration.ZERO)
        );
        gameMusic.setOnEndOfMedia(
                () -> gameMusic.seek(Duration.ZERO)
        );

    }


    public void play(Sounds s)
    {

        switch (s)
        {
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
    public void stop(Sounds s)
    {
        switch (s)
        {
            case MENUMUSIC -> menuMusic.stop();
            case GAMEMUSIC -> gameMusic.stop();
            case BUTTONCLICK -> buttonClick.stop();
            case CURSORSELECT -> cursorSelect.stop();
            case ALERTBEEP -> alertBeep.stop();
            case CARDFLIP -> cardFlip.stop();
        }
    }
}
