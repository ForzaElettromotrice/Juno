package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.juno.view.GenView;
import org.juno.view.StartMenuView;

/**
 * Defines: startMenu, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StartMenuController
{
    private static final StartMenuView START_MENU_VIEW = StartMenuView.getINSTANCE();

    @FXML
    public Button stats;

    @FXML
    public ImageView lightsStats;

    @FXML
    public void playClicked()
    {
        START_MENU_VIEW.changeScene(1);
    }

    @FXML
    public void statsClicked()
    {
        START_MENU_VIEW.changeScene(2);
    }

    @FXML
    public void settingsClicked()
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
}
