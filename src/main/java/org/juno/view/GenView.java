package org.juno.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.juno.Juno;

import java.io.IOException;

/**
 * Defines GenView class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public abstract class GenView
{
	protected static Stage window;

	protected static Scene startMenu;
	protected static Scene chooseMode;
	protected static Scene play;
	protected static Scene stats;
	protected static Scene settings;

	public static void setWindow(Stage window)
	{
		GenView.window = window;
	}

	public static void load() throws IOException
	{
		FXMLLoader loaderStart = new FXMLLoader(GenView.class.getResource("StartMenu.fxml"));
		startMenu = new Scene(loaderStart.load());
//		FXMLLoader loader = new FXMLLoader(GenView.class.getResource("StartMenu.fxml"));
//		startMenu = new Scene(loader.load());
//		FXMLLoader loader = new FXMLLoader(GenView.class.getResource("StartMenu.fxml"));
//		startMenu = new Scene(loader.load());
		FXMLLoader loaderStats = new FXMLLoader(GenView.class.getResource("StatsMenu.fxml"));
		stats = new Scene(loaderStats.load());
		FXMLLoader loader = new FXMLLoader(GenView.class.getResource("Settings.fxml"));
		settings = new Scene(loader.load());
	}

	public static Scene getStartMenu()
	{
		return startMenu;
	}

	public static void closeWindow()
	{
		window.close();
	}

	public void changeScene(int n)
	{
		window.setScene(switch (n)
				{
					case 0 -> startMenu;
					case 1 -> chooseMode;
					case 2 -> stats;
					case 3 -> settings;
					case 4 -> play;
					default -> throw new RuntimeException("Non esiste questa scena");
				});
	}

	public static Scene getLogin()
	{
		return null;
	}

}
