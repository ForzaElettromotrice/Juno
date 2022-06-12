package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;
import org.juno.view.StatsMenuView;

/**
 * Defines: StatsMenuController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StatsMenuController
{
    private static final StatsMenuView STATS_MENU_VIEW = StatsMenuView.getINSTANCE();

    @FXML
    public Button change;
    @FXML
    public Button back;
    @FXML
    public Circle avatar;

    @FXML
    public void avatarEntered()
    {
        change.setVisible(true);
    }

    @FXML
    public void avatarExited()
    {
        change.setVisible(false);
    }

    @FXML
    public void backEntered()
    {
        back.setStyle("-fx-border-color: BLACK; -fx-background-color: transparent; -fx-border-radius: 90;");
    }

    @FXML
    public void backExited()
    {
        back.setStyle("-fx-border-color: transparent; -fx-background-color: transparent");
    }

    @FXML
    public void backClicked() throws NonexistingSceneException
    {
        //TODO: dare i nuovi valori a User e fare il save()

        STATS_MENU_VIEW.changeScene(0);
    }

    @FXML
    public void changeClicked()
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle("Apri...");
        //TODO: controlla bene cosa è successo prima di caricare cose(controlla che non sia pedopornografia)

        avatar.setFill(new ImagePattern(new Image(fc.showOpenDialog(STATS_MENU_VIEW.getWindow()).getPath())));
        avatarExited();
    }
}
