package org.juno.controller;

import javafx.fxml.FXML;
import org.juno.view.SettingsView;
import org.juno.view.StartMenuView;

/**
 * Defines SettingsController class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class SettingsController
{
	private static final SettingsView SETTINGS_VIEW = SettingsView.getINSTANCE();

	@FXML
	public void backClicked()
	{
		SETTINGS_VIEW.changeScene(0);
	}
}
