package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.juno.model.user.User;
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
    private static final User USER = User.getINSTANCE();
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
        STATS_MENU_VIEW.changeScene(0);
    }

    @FXML
    public void changeClicked()
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle("Apri...");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Image Files", "*.jpeg", "*.bmp", "*.png", "*.webmp", "*.gif"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("WEBMP", "*.webmp"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"));
        String path = fc.showOpenDialog(STATS_MENU_VIEW.getWindow()).getPath();
        avatar.setFill(new ImagePattern(new Image(path)));
        avatarUrl=path;
        avatarExited();
    }

    @FXML
    public void saveUsername()
    {
        USER.setNickname(username.getCharacters().toString());
        USER.save();
    }

    @FXML
    public void keyPressed(KeyEvent key)
    {
        if (key.getCode() == KeyCode.ENTER) saveUsername();
    }
}
