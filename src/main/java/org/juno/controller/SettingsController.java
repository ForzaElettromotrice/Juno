package org.juno.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.juno.view.SettingsView;
import org.juno.view.StartMenuView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Defines SettingsController class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class SettingsController implements Initializable
{
	private static final SettingsView SETTINGS_VIEW = SettingsView.getINSTANCE();

	@FXML
	public void backClicked()
	{
		SETTINGS_VIEW.changeScene(0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
}
