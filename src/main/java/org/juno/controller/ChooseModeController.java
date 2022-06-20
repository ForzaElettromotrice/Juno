package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;

/**
 * Defines: ChooseModeController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class ChooseModeController
{
    private static final GenView GEN_VIEW = GenView.getINSTANCE();

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
    public void classicClicked()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        //TODO
    }

    @FXML
    public void classicEntered()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
        classicBox.setVisible(true);
    }

    @FXML
    public void classicExited()
    {
        classicBox.setVisible(false);
    }

    @FXML
    public void tradeClicked()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        //  TODO
    }

    @FXML
    public void tradeEntered()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
        tradeBox.setVisible(true);
    }

    @FXML
    public void tradeExited()
    {
        tradeBox.setVisible(false);
    }

    @FXML
    public void reflexClicked()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        //TODO
    }

    @FXML
    public void reflexEntered()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
        reflexBox.setVisible(true);
    }

    @FXML
    public void reflexExited()
    {
        reflexBox.setVisible(false);
    }

    @FXML
    public void backClicked() throws NonexistingSceneException
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        GEN_VIEW.changeScene(0, modeAnchor);
    }

    @FXML
    public void backExited()
    {
        back.setStyle("-fx-background-color:transparent;");
    }

    public void backEntered()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
        back.setStyle("-fx-background-color:transparent; -fx-border-color:BLACK; -fx-border-radius:90; -fx-border-width:3;");
    }
}
