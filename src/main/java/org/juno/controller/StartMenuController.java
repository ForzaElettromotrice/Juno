package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;



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
    public ImageView blueFrame;
    @FXML
    public ImageView redFrame;
    @FXML
    public ImageView greenFrame;
    @FXML
    public ImageView yellowFrame;
    @FXML
    public AnchorPane menuAnchor;

    @FXML
    public void playClicked()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        GEN_VIEW.changeScene(GenView.SCENES.CHOOSEMODE, menuAnchor);
    }

    @FXML
    public void statsClicked()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        if (GEN_VIEW.getStats().getUserData() instanceof StatsController smc) smc.load();
        GEN_VIEW.changeScene(GenView.SCENES.STATS, menuAnchor);
    }

    @FXML
    public void settingsClicked()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        if (GEN_VIEW.getSettings().getUserData() instanceof SettingsController sc) sc.load();
        GEN_VIEW.changeScene(GenView.SCENES.SETTINGS, menuAnchor);
    }

    @FXML
    public void exitClicked()
    {
        User.getINSTANCE().save();
        GEN_VIEW.closeWindow();
    }

    @FXML
    public void statsEntered()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        blueFrame.setVisible(true);
    }

    @FXML
    public void statsExited()
    {
        blueFrame.setVisible(false);
    }

    @FXML
    public void playEntered()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        redFrame.setVisible(true);
    }

    @FXML
    public void playExited()
    {
        redFrame.setVisible(false);
    }

    @FXML
    public void settingsEntered()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        yellowFrame.setVisible(true);
    }

    @FXML
    public void settingsExited()
    {
        yellowFrame.setVisible(false);
    }

    @FXML
    public void exitEntered()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        greenFrame.setVisible(true);
    }

    @FXML
    public void exitExited()
    {
        greenFrame.setVisible(false);
    }

}
