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

    public enum Sounds
    {
        MENUMUSIC(0),
        GAMEMUSIC(1),
        BUTTONCLICK(2),
        CURSORSELECT(3),
        ALERTBEEP(4),
        CARDFLIP(5);

        private final MediaPlayer sound;

        Sounds(int val)
        {
            sound = new MediaPlayer(new Media(new File(
                    switch(val)
                            {
                                case 0 -> "src/main/resources/org/juno/sounds/menuMusic.mp3";
                                case 1 -> "src/main/resources/org/juno/sounds/gameMusic.mp3";
                                case 2 -> "src/main/resources/org/juno/sounds/buttonClick.mp3";
                                case 3 -> "src/main/resources/org/juno/sounds/cursorSelect.mp3";
                                case 4 -> "src/main/resources/org/juno/sounds/alertBeep.mp3";
                                case 5 -> "src/main/resources/org/juno/sounds/cardFlip.mp3";
                                default -> "";
                            }
            ).toURI().toString()));
        }

        public MediaPlayer getSound()
        {
            return sound;
        }
    }

    private AudioPlayer()
    {
    }

    public static AudioPlayer getINSTANCE()
    {
        return INSTANCE;
    }

    public void play(Sounds s)
    {
        s.getSound().play();
        s.getSound().setOnEndOfMedia(() ->
        {
            s.getSound().seek(Duration.ZERO);
            s.getSound().play();
        });
    }

    public void stop(Sounds s)
    {
        s.getSound().stop();
    }

    public void setMusicVolume(double n)
    {
        Sounds.MENUMUSIC.getSound().setVolume(n);
        Sounds.GAMEMUSIC.getSound().setVolume(n);
    }
    public void setEffectsVolume(double n)
    {
        Sounds.BUTTONCLICK.getSound().setVolume(n);
        Sounds.CURSORSELECT.getSound().setVolume(n);
        Sounds.ALERTBEEP.getSound().setVolume(n);
        Sounds.CARDFLIP.getSound().setVolume(n);
    }
    public double getMusicVolume()
    {
        return Sounds.MENUMUSIC.getSound().getVolume();
    }
    public double getEffectsVolume()
    {
        return Sounds.ALERTBEEP.getSound().getVolume();
    }
    public static void playSound(Sounds s)
    {
        s.getSound().play();
        s.getSound().seek(Duration.ZERO);
    }


}
