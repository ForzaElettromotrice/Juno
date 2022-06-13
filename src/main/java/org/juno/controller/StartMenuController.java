package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;
import org.juno.view.StartMenuView;

import java.io.File;
import java.net.URL;

/**
 * Defines: startMenu, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StartMenuController
{
    private static final StartMenuView START_MENU_VIEW = StartMenuView.getINSTANCE();

    @FXML
    public ImageView lightsStats;
    @FXML
    public ImageView lightsPlay;
    @FXML
    public ImageView lightsExit;
    @FXML
    public ImageView lightsSettings;

    @FXML
    public void playClicked() throws NonexistingSceneException
    {
        START_MENU_VIEW.changeScene(1);
    }

    @FXML
    public void statsClicked() throws NonexistingSceneException
    {
        if (GenView.getStats().getUserData() instanceof StatsMenuController smc) smc.load();
        START_MENU_VIEW.changeScene(2);
    }

    @FXML
    public void settingsClicked() throws NonexistingSceneException
    {
        START_MENU_VIEW.changeScene(3);
    }

    @FXML
    public void exitClicked()
    {
        GenView.closeWindow();
    }

    @FXML
    public void statsEntered()
    {
        lightsStats.setVisible(true);
    }

    @FXML
    public void statsExited()
    {
        lightsStats.setVisible(false);
    }

    @FXML
    public void playEntered()
    {
        lightsPlay.setVisible(true);
    }

    @FXML
    public void playExited()
    {
        lightsPlay.setVisible(false);
    }

    @FXML
    public void settingsEntered()
    {
        lightsSettings.setVisible(true);
    }

    @FXML
    public void settingsExited()
    {
        lightsSettings.setVisible(false);
    }

    @FXML
    public void exitEntered()
    {
        lightsExit.setVisible(true);
    }

    @FXML
    public void exitExited()
    {
        lightsExit.setVisible(false);
    }

}
