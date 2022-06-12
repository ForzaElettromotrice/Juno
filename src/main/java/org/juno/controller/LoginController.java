package org.juno.controller;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.juno.view.LoginView;
import org.juno.view.NonexistingSceneException;

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
	private static final LoginView LOGIN_VIEW = LoginView.getINSTANCE();

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
	public void saveClicked() throws NonexistingSceneException
	{
		if (Objects.equals(username.getText(), ""))
		{
			alert.setVisible(true);
			return;
		}

		String pathImg;
		if (firstChoice.isSelected()) pathImg = "src/main/resources/org/juno/images/icon-256x256.png";
		else
			pathImg = secondChoice.isSelected() ? "src/main/resources/org/juno/images/trasferimento.png" : "src/main/resources/org/juno/images/user-icon-trendy-flat-style-isolated-grey-background-user-symbol-user-icon-trendy-flat-style-isolated-grey-background-123663211.jpg";

		saveData(username.getText(), pathImg);


		LOGIN_VIEW.changeScene(0);
	}

	@FXML
	public void keyPressed(KeyEvent key) throws NonexistingSceneException
	{
		if (key.getCode() == KeyCode.ENTER) saveClicked();
	}

	private void saveData(String username, String pathImg)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/org/juno/model/user/user.txt")))
		{
			bw.write(String.format("%s%n%s%n0%n0%n1%n0", username, pathImg));
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
