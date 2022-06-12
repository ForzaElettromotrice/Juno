package org.juno.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.juno.model.user.User;
import org.juno.view.NonexistingSceneException;
import org.juno.view.StatsMenuView;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Defines: StatsMenuController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StatsMenuController implements Initializable
{
    private static final StatsMenuView STATS_MENU_VIEW = StatsMenuView.getINSTANCE();
    private static final User USER = User.getINSTANCE();

    @FXML
    public Button change;
    @FXML
    public Button back;
    @FXML
    public Circle avatar;

    @FXML
    public TextField username;
    @FXML
    public Label victories;
    @FXML
    public Label defeats;
    @FXML
    public Label matches;
    @FXML
    public Label level;
    @FXML
    public ProgressBar progressBar;

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
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        username.setText(USER.getNickname());
        victories.setText("" + USER.getVictories());
        defeats.setText("" + USER.getDefeats());
        matches.setText("" + USER.getTotalMatches());
        level.setText("" + USER.getLevel());
        avatar.setFill(new ImagePattern(new Image(System.getProperty("user.dir") + "\\" + USER.getAvatar())));
        progressBar.setProgress(USER.getProgress());
    }
}
