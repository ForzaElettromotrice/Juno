package org.juno.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.juno.model.user.DataCorruptedException;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;
import org.juno.view.NotExistingSoundException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * Defines Login class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class LoginController
{
	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

	@FXML
	public TextField username;

	@FXML
	public RadioButton firstChoice;
	@FXML
	public RadioButton secondChoice;
	@FXML
	public RadioButton thirdChoice;

	@FXML
	public Label alert;

	@FXML
	public Button save;
	@FXML
	public AnchorPane loginAnchor;

	@FXML
	public void saveClicked() throws NonexistingSceneException, DataCorruptedException, NotExistingSoundException
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		if (Objects.equals(username.getText(), ""))
		{
			alert.setVisible(true);
			return;
		}

		String pathImg;
		if (firstChoice.isSelected()) pathImg = "src\\main\\resources\\org\\juno\\images\\icon1.jpg";
		else
			pathImg = secondChoice.isSelected() ? "src\\main\\resources\\org\\juno\\images\\icon2.jpg" : "src\\main\\resources\\org\\juno\\images\\icon3.jpg";

		saveData(username.getText(), pathImg);


		GEN_VIEW.changeScene(0, loginAnchor);
	}

	@FXML
	public void keyPressed(KeyEvent key) throws NonexistingSceneException, DataCorruptedException, NotExistingSoundException
	{
		if (key.getCode() == KeyCode.ENTER) saveClicked();
	}

	private void saveData(String username, String pathImg) throws DataCorruptedException
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/org/juno/model/user/user.txt")))
		{
			bw.write(String.format("%s%n%s%n0%n0%n1%n0", username, pathImg));
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			User.getINSTANCE().load();
		}
	}

	public void saveEntered() throws NotExistingSoundException
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
		save.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-border-radius: 90; -fx-background-radius: 90;");
	}

	public void saveExited()
	{
		save.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
	}
}
