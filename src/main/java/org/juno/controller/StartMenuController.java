package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;
import org.juno.view.NotExistingSoundException;

import java.io.IOException;


/**
 * Defines: startMenu, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StartMenuController
{
    private static final GenView GEN_VIEW = GenView.getINSTANCE();
    private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
    @FXML
    public ImageView lightsStats;
    @FXML
    public ImageView lightsPlay;
    @FXML
    public ImageView lightsExit;
    @FXML
    public ImageView lightsSettings;
    @FXML
    public AnchorPane menuAnchor;

    @FXML
    public void playClicked() throws NonexistingSceneException, NotExistingSoundException, IOException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        GEN_VIEW.changeScene(1, menuAnchor);

    }

    @FXML
    public void statsClicked() throws NonexistingSceneException, NotExistingSoundException, IOException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        if (GenView.getStats().getUserData() instanceof StatsController smc) smc.load();
        GEN_VIEW.changeScene(2, menuAnchor);
    }

    @FXML
    public void settingsClicked() throws NonexistingSceneException, NotExistingSoundException, IOException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        if (GenView.getSettings().getUserData() instanceof SettingsController sc) sc.load();
        GEN_VIEW.changeScene(3, menuAnchor);
    }

    @FXML
    public void exitClicked()
    {
        GenView.closeWindow();
    }

    @FXML
    public void statsEntered() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        lightsStats.setVisible(true);
    }

    @FXML
    public void statsExited()
    {
        lightsStats.setVisible(false);
    }

    @FXML
    public void playEntered() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        lightsPlay.setVisible(true);
    }

    @FXML
    public void playExited()
    {
        lightsPlay.setVisible(false);
    }

    @FXML
    public void settingsEntered() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        lightsSettings.setVisible(true);
    }

    @FXML
    public void settingsExited()
    {
        lightsSettings.setVisible(false);
    }

    @FXML
    public void exitEntered() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        lightsExit.setVisible(true);
    }

    @FXML
    public void exitExited()
    {
        lightsExit.setVisible(false);
    }

}
