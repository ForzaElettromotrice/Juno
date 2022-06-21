package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;
import org.juno.view.NotExistingSoundException;

/**
 * Defines: ChooseModeController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class ChooseModeController
{
    private static final GenView GEN_VIEW = GenView.getINSTANCE();
    private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

    @FXML
    public HBox classicBox;
    @FXML
    public HBox reflexBox;
    @FXML
    public HBox tradeBox;
    @FXML
    public Button back;
    @FXML
    public AnchorPane modeAnchor;

    @FXML
    public void classicClicked() throws NotExistingSoundException, NonexistingSceneException {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        GEN_VIEW.changeScene(4, modeAnchor);
    }

    @FXML
    public void classicEntered() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        classicBox.setVisible(true);
    }

    @FXML
    public void classicExited()
    {
        classicBox.setVisible(false);
    }

    @FXML
    public void tradeClicked() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        //  TODO
    }

    @FXML
    public void tradeEntered() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        tradeBox.setVisible(true);
    }

    @FXML
    public void tradeExited()
    {
        tradeBox.setVisible(false);
    }

    @FXML
    public void reflexClicked() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        //TODO
    }

    @FXML
    public void reflexEntered() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        reflexBox.setVisible(true);
    }

    @FXML
    public void reflexExited()
    {
        reflexBox.setVisible(false);
    }

    @FXML
    public void backClicked() throws NonexistingSceneException, NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
        GEN_VIEW.changeScene(0, modeAnchor);
    }

    @FXML
    public void backExited()
    {
        back.setStyle("-fx-background-color:transparent;");
    }

    public void backEntered() throws NotExistingSoundException
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
        back.setStyle("-fx-background-color:transparent; -fx-border-color:BLACK; -fx-border-radius:90; -fx-border-width:3;");
    }
}
