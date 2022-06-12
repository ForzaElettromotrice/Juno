package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.juno.view.LoginView;
import org.juno.view.SettingsView;

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
	public void saveClicked()
	{
		if (Objects.equals(username.getText(), ""))
		{
			alert.setVisible(true);
			return;
		}

		//TODO: settare il path dell'immagine profilo

		LOGIN_VIEW.changeScene(0);
	}

}
