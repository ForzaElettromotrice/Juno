package org.juno.controller;

import javafx.fxml.FXML;
import org.juno.view.NonexistingSceneException;
import org.juno.view.SettingsView;


/**
 * Defines SettingsController class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class SettingsController
{
	private static final SettingsView SETTINGS_VIEW = SettingsView.getINSTANCE();

	@FXML
	public void backClicked() throws NonexistingSceneException
	{
		SETTINGS_VIEW.changeScene(0);
	}


}
