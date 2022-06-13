package org.juno;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Defines: AudioPlayer, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class AudioPlayer
{
    public static void playSound(String sound)
    {
        Media music = new Media(new File(sound).toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(music);
        mediaPlayer.play();
    }

    //playSound("src/main/resources/org/juno/sounds/If Im Fading Tonight (Instrumental) – Props (No Copyright Music).mp3");
}
