package org.juno.view;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.juno.controller.GameplayController;
import org.juno.datapackage.*;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Defines GenView class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class GenView implements Observer
{
	private static final GenView INSTANCE = new GenView();
	private Stage window;

	private Scene login;
	private Scene startMenu;
	private Scene chooseMode;
	private Scene gameplay;
	private Scene gameplayReflex;
	private Scene stats;
	private Scene settings;
	private Scene endgame;

	public enum SCENES
	{
		LOGIN(-1),
		STARTMENU(0),
		CHOOSEMODE(1),
		STATS(2),
		SETTINGS(3),
		GAMEPLAY(4),
		ENDGAME(5),
		GAMEPLAYREFLEX(6);

		private final int value;

		SCENES(int i)
		{
			value = i;
		}
		public int getValue()
		{
			return value;
		}
	}


	private GenView()
	{
	}


	public static GenView getINSTANCE()
	{
		return INSTANCE;
	}


	public void setWindow(Stage window)
	{
		this.window = window;
	}
	public Stage getWindow()
	{
		return window;
	}

	public Scene getLogin()
	{
		return login;
	}
	public Scene getStartMenu()
	{
		return startMenu;
	}
	public Scene getChooseMode()
	{
		return chooseMode;
	}
	public Scene getGameplay()
	{
		return gameplay;
	}
	public Scene getGameplayReflex()
	{
		return gameplayReflex;
	}
	public Scene getStats()
	{
		return stats;
	}
	public Scene getSettings()
	{
		return settings;
	}
	public Scene getEndgame()
	{
		return endgame;
	}


	public void load() throws IOException
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

		loader = new FXMLLoader(GenView.class.getResource("Gameplay.fxml"));
		gameplay = new Scene(loader.load());
		gameplay.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("GameplayReflex.fxml"));
		gameplayReflex = new Scene(loader.load());
		gameplayReflex.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("Stats.fxml"));
		stats = new Scene(loader.load());
		stats.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("Settings.fxml"));
		settings = new Scene(loader.load());
		settings.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("Endgame.fxml"));
		endgame = new Scene(loader.load());
		endgame.setUserData(loader.getController());
	}
	public void closeWindow()
	{
		window.close();
	}

	private void changeScene(int n, AnchorPane anchor) throws NonexistingSceneException
	{
		Scene scene = (switch (n)
				{
					case -1 -> login;
					case 0 -> startMenu;
					case 1 -> chooseMode;
					case 2 -> stats;
					case 3 -> settings;
					case 4 -> gameplay;
					case 5 -> endgame;
					case 6 -> gameplayReflex;
					default -> throw new NonexistingSceneException("Non esiste questa scena");
				});
		makeFadeOut(scene, anchor);
		window.setMaximized(true);
	}

	public void changeScene(SCENES scene, AnchorPane anchor) throws NonexistingSceneException
	{
		changeScene(scene.getValue(), anchor);
	}

	public void makeFadeOut(Scene scene, AnchorPane anchor)
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), anchor);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) ->
		{
			window.setScene(scene);
			makeFadeIn(scene.getRoot());
		});
		fadeTransition.play();
	}

	public void makeFadeIn(Node node)
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), node);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.play();
	}


	@Override
	public void update(Observable o, Object arg)
	{

		GameplayController gameplayController = (GameplayController) gameplay.getUserData();

		if (arg instanceof DrawData drawData) Platform.runLater(() -> gameplayController.draw(drawData));
		else if (arg instanceof DiscardData discardData)
			Platform.runLater(() -> gameplayController.discard(discardData));
		else if (arg instanceof TurnData turnData) Platform.runLater(() -> gameplayController.turn(turnData));
		else if (arg instanceof EffectData effectData) Platform.runLater(() -> gameplayController.effect(effectData));

	}
}
