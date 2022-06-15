package org.juno.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.juno.controller.SettingsController;
import org.juno.controller.StatsMenuController;

import java.io.IOException;

/**
 * Defines GenView class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class GenView
{
	private static final GenView INSTANCE = new GenView();
	private static Stage window;

	private static Scene login;
	private static Scene startMenu;
	private static Scene chooseMode;
	private static Scene gameplay;
	private static Scene stats;
	private static Scene settings;


	private GenView()
	{
	}


	public static void setWindow(Stage window)
	{
		GenView.window = window;
	}
	public static GenView getINSTANCE()
	{
		return INSTANCE;
	}
	public Stage getWindow()
	{
		return window;
	}

	public static Scene getLogin()
	{
		return login;
	}
	public static Scene getStartMenu()
	{
		return startMenu;
	}
	public static Scene getChooseMode()
	{
		return chooseMode;
	}
	public static Scene getGameplay()
	{
		return gameplay;
	}
	public static Scene getStats()
	{
		return stats;
	}
	public static Scene getSettings()
	{
		return settings;
	}


	public static void load() throws IOException
	{
		FXMLLoader loader = new FXMLLoader(GenView.class.getResource("Login.fxml"));
		login = new Scene(loader.load());
		login.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("StartMenu.fxml"));
		startMenu = new Scene(loader.load());
		startMenu.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("ChooseMode.fxml"));
		chooseMode = new Scene(loader.load());
		chooseMode.setUserData(loader.getController());

//		loader = new FXMLLoader(GenView.class.getResource("Gameplay.fxml"));
//		gameplay = new Scene(loader.load());
//		gameplay.setUserData(loader.getController());
		
		loader = new FXMLLoader(GenView.class.getResource("StatsMenu.fxml"));
		stats = new Scene(loader.load());
		stats.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("Settings.fxml"));
		settings = new Scene(loader.load());
		settings.setUserData(loader.getController());
	}


	public static void closeWindow()
	{
		window.close();
	}

	public void changeScene(int n) throws NonexistingSceneException
	{
		window.setScene(switch (n)
				{
					case -1 -> login;
					case 0 -> startMenu;
					case 1 -> chooseMode;
					case 2 -> stats;
					case 3 -> settings;
					case 4 -> gameplay;
					default -> throw new NonexistingSceneException("Non esiste questa scena");
				});

//		window.setMaximized(true);
//		window.setFullScreen(true);
	}


}
