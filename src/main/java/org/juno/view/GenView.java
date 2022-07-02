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
import org.juno.controller.Gameplay;
import org.juno.controller.GameplayClassicController;
import org.juno.controller.GameplayComboController;
import org.juno.controller.GameplayTradeController;
import org.juno.datapackage.*;
import org.juno.model.table.TurnOrder;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Defines Genview class,
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
	private Scene gameplayClassic;
	private Scene gameplayCombo;
	private Scene gameplayTrade;
	private Scene stats;
	private Scene settings;
	private Scene endgame;

	private Gameplay currentGameController;

	public enum SCENES
	{
		LOGIN,
		STARTMENU,
		CHOOSEMODE,
		STATS,
		SETTINGS,
		GAMEPLAYCLASSIC,
		ENDGAME,
		GAMEPLAYCOMBO,
		GAMEPLAYTRADE
	}


	private GenView()
	{
	}

	public static GenView getINSTANCE()
	{
		return INSTANCE;
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
	public Scene getGameplayClassic()
	{
		return gameplayClassic;
	}
	public Scene getGameplayCombo()
	{
		return gameplayCombo;
	}
	public Scene getGameplayTrade()
	{
		return gameplayTrade;
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


	public void setWindow(Stage stage)
	{
		window = stage;
	}
	public void setCurrentGameController(TurnOrder.MODALITY modality)
	{
		currentGameController = switch (modality)
				{
					case CLASSIC -> (GameplayClassicController) gameplayClassic.getUserData();
					case COMBO -> (GameplayComboController) gameplayCombo.getUserData();
					case TRADE -> (GameplayTradeController) gameplayTrade.getUserData();
				};
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

		loader = new FXMLLoader(GenView.class.getResource("GameplayClassic.fxml"));
		gameplayClassic = new Scene(loader.load());
		gameplayClassic.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("GameplayCombo.fxml"));
		gameplayCombo = new Scene(loader.load());
		gameplayCombo.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("GameplayTrade.fxml"));
		gameplayTrade = new Scene(loader.load());
		gameplayTrade.setUserData(loader.getController());

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

	public void changeScene(SCENES s, AnchorPane anchor)
	{
		Scene scene = (switch (s)
				{
					case LOGIN -> login;
					case STARTMENU -> startMenu;
					case CHOOSEMODE -> chooseMode;
					case STATS -> stats;
					case SETTINGS -> settings;
					case GAMEPLAYCLASSIC -> gameplayClassic;
					case ENDGAME -> endgame;
					case GAMEPLAYCOMBO -> gameplayCombo;
					case GAMEPLAYTRADE -> gameplayTrade;
				});

		makeFadeOut(scene, anchor);
	}

	private void makeFadeOut(Scene newScene, AnchorPane oldAnchor)
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), oldAnchor);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) ->
		{
			window.setScene(newScene);
			makeFadeIn(newScene.getRoot());
		});
		fadeTransition.play();
	}
	private void makeFadeIn(Node node)
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), node);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.play();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (arg instanceof DrawData drawData)
		{
			Platform.runLater(() -> currentGameController.draw(drawData));
		} else if (arg instanceof DiscardData discardData)
		{
			Platform.runLater(() -> currentGameController.discard(discardData));
		} else if (arg instanceof TurnData turnData) Platform.runLater(() -> currentGameController.turn(turnData));
		else if (arg instanceof EffectData effectData)
			Platform.runLater(() -> currentGameController.effect(effectData));
		else if (arg instanceof SwitchData switchData)
			Platform.runLater(() -> currentGameController.doSwitch(switchData));
	}
}
