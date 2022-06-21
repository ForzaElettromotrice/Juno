package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;


/**
 * Defines: StatsMenuController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StatsController
{
    private static final GenView GEN_VIEW = GenView.getINSTANCE();
    private static final User USER = User.getINSTANCE();

    @FXML
    public AnchorPane statsAnchor;
    @FXML
    public Pane avatarBox;
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
    public RadioButton option1;
    @FXML
    public RadioButton option2;
    @FXML
    public RadioButton option3;
    @FXML
    public Button open;

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
        AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
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
        AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
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
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        USER.setAvatar(avatarUrl);
        save();
        GEN_VIEW.changeScene(0, statsAnchor);
    }

    @FXML
    public void changeClicked()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        avatarBox.setVisible(true);
    }

    @FXML
    public void save()
    {
        avatarBox.setVisible(false);
        if (!username.isFocused()) return;
        USER.setNickname(username.getCharacters().toString());
        USER.save();
        back.requestFocus();
    }

    @FXML
    public void keyPressed(KeyEvent key)
    {
        if (key.getCode() == KeyCode.ENTER) save();
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

    public void option1Clicked()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        avatar.setFill(new ImagePattern(new Image(System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\images\\icon1.jpg")));
    }

    public void option2Clicked()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        avatar.setFill(new ImagePattern(new Image(System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\images\\icon2.jpg")));
    }

    public void option3Clicked()
    {
        AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
        avatar.setFill(new ImagePattern(new Image(System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\images\\icon3.jpg")));
    }

    public void openEntered()
    {
        if(open.isVisible())
        {
            AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
            open.setStyle("-fx-border-color: #a00303; -fx-background-color: transparent; -fx-border-radius: 90;");
        }
    }

    public void openClicked()
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
        save();
    }

    public void openExited()
    {
        open.setStyle("-fx-border-color: transparent; -fx-background-color: transparent");
    }
}
