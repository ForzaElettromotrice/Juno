package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.juno.model.user.User;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;


/**
 * Defines: StatsMenuController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StatsMenuController
{
    private static final GenView GEN_VIEW = GenView.getINSTANCE();
    private static final User USER = User.getINSTANCE();

    @FXML
    public AnchorPane statsAnchor;
    private String avatarUrl = USER.getAvatar();

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
        USER.setAvatar(avatarUrl);
        saveUsername();
        GEN_VIEW.changeScene(0, statsAnchor);
    }

    @FXML
    public void changeClicked()
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle("Apri...");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Image Files", "*.jpeg", "*.bmp", "*.png", "*.webmp", "*.gif", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("WEBMP", "*.webmp"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"));
        String path = fc.showOpenDialog(GEN_VIEW.getWindow()).getPath();
        avatar.setFill(new ImagePattern(new Image(path)));
        avatarUrl=path;
        avatarExited();
    }

    @FXML
    public void saveUsername()
    {
        if (!username.isFocused()) return;
        USER.setNickname(username.getCharacters().toString());
        USER.save();
        back.requestFocus();
    }

    @FXML
    public void keyPressed(KeyEvent key)
    {
        if (key.getCode() == KeyCode.ENTER) saveUsername();
    }

    public void load()
    {
        username.setText(USER.getNickname());
        victories.setText("" + USER.getVictories());
        defeats.setText("" + USER.getDefeats());
        matches.setText("" + USER.getTotalMatches());
        level.setText("" + USER.getLevel());
        progressBar.setProgress(USER.getProgress());
        avatar.setFill(new ImagePattern(new Image("" + System.getProperty("user.dir") + "\\" + USER.getAvatar())));
    }

}
