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


    private AudioPlayer()
    {
    }

    public static AudioPlayer getINSTANCE()
    {
        return INSTANCE;
    }

    public void play(int n) throws NotExistingSoundException
    {
        switch (n)
        {
            case 0 ->
            {
                menuMusic.play();
                menuMusic.setOnEndOfMedia(() ->
                {
                    menuMusic.seek(Duration.ZERO);
                    menuMusic.play();
                });
            }
            case 1 ->
            {
                gameMusic.play();
                gameMusic.setOnEndOfMedia(() ->
                {
                    gameMusic.seek(Duration.ZERO);
                    gameMusic.play();
                });
            }
            case 2 -> buttonClick.play();
            case 3 -> cursorSelect.play();
            case 4 -> cardFlip.play();
            case 5 -> alertBeep.play();
            default -> throw new NotExistingSoundException("This sounds not exist!");
        }
    }

    public void stop(int n) throws NotExistingSoundException
    {
        switch (n)
        {
            case 0 -> menuMusic.stop();
            case 1 -> gameMusic.stop();
            case 2 -> buttonClick.stop();
            case 3 -> cursorSelect.stop();
            case 4 -> cardFlip.stop();
            case 5 -> alertBeep.stop();
            default -> throw new NotExistingSoundException("This sounds not exist!");
        }
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
        cardFlip.setVolume(n);
        alertBeep.setVolume(n);
    }
    public double getMusicVolume()
    {
        return gameMusic.getVolume();
    }
    public double getEffectsVolume()
    {
        return alertBeep.getVolume();
    }
    public static void playSound(String sound)
    {
        Media music = new Media(new File(sound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        mediaPlayer.play();
    }


}
