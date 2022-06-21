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

    private final MediaPlayer menuMusic = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/menuMusic.mp3").toURI().toString()));
    private final MediaPlayer gameMusic = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/gameMusic.mp3").toURI().toString()));
    private final MediaPlayer buttonClick = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/buttonClick.mp3").toURI().toString()));
    private final MediaPlayer cursorSelect = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/cursorSelect.mp3").toURI().toString()));
    private final MediaPlayer alertBeep = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/alertBeep.mp3").toURI().toString()));
    private final MediaPlayer cardFlip = new MediaPlayer(new Media(new File("src/main/resources/org/juno/sounds/cardFlip.mp3").toURI().toString()));

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
        menuMusic.setOnEndOfMedia(
                () ->
                {
                    menuMusic.seek(Duration.ZERO);
                    menuMusic.play();
                }
        );
        gameMusic.setOnEndOfMedia(
                () ->
                {
                    gameMusic.seek(Duration.ZERO);
                    gameMusic.play();
                }
        );
    }

    public static AudioPlayer getINSTANCE()
    {
        return INSTANCE;
    }

    public void play(int n) throws NotExistingSoundException
    {

        switch (n)
        {
            case 0 -> menuMusic.play();
            case 1 -> gameMusic.play();
            case 2 ->
            {
                buttonClick.seek(Duration.ZERO);
                buttonClick.play();
            }
            case 3 ->
            {
                cursorSelect.seek(Duration.ZERO);
                cursorSelect.play();
            }
            case 4 ->
            {
                alertBeep.seek(Duration.ZERO);
                alertBeep.play();
            }
            case 5 ->
            {
                cardFlip.seek(Duration.ZERO);
                cardFlip.play();
            }
            default -> throw new NotExistingSoundException("Questo suono non esiste!");
        }
    }

    public void play(Sounds s) throws NotExistingSoundException
    {
        play(s.getValue());
    }

    public void stop(int n) throws NotExistingSoundException
    {
        switch (n)
        {
            case 0 -> menuMusic.stop();
            case 1 -> gameMusic.stop();
            case 2 -> buttonClick.stop();
            case 3 -> cursorSelect.stop();
            case 4 -> alertBeep.stop();
            case 5 -> cardFlip.stop();
            default -> throw new NotExistingSoundException("Questo suono non esiste!");
        }
    }

    public void stop(Sounds s) throws NotExistingSoundException
    {
        stop(s.getValue());
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
    public double getMusicVolume()
    {
        return menuMusic.getVolume();
    }
    public double getEffectsVolume()
    {
        return alertBeep.getVolume();
    }


}
