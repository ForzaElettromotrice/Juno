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
import org.juno.model.user.User;

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
	private Scene register;
	private Scene startMenu;
	private Scene chooseMode;
	private Scene gameplayClassic;
	private Scene gameplayCombo;
	private Scene gameplayTrade;
	private Scene stats;
	private Scene settings;
	private Scene endMatchClassic;
	private Scene endMatchCombo;
	private Scene endMatchTrade;
	private Scene endgame;

	private Gameplay currentGameController;

	public enum SCENES
	{
		LOGIN,
		REGISTER,
		STARTMENU,
		CHOOSEMODE,
		STATS,
		SETTINGS,
		GAMEPLAYCLASSIC,
		ENDGAME,
		GAMEPLAYCOMBO,
		GAMEPLAYTRADE,
		ENDMATCHCLASSIC,
		ENDMATCHCOMBO,
		ENDMATCHTRADE
	}

	/**
	 * Private constructor for singleton pattern
	 */
	private GenView()
	{
	}

	/**
	 * @return the instance of the class
	 */
	public static GenView getINSTANCE()
	{
		return INSTANCE;
	}

	/**
	 * @return the primary stage of the application
	 */
	public Stage getWindow()
	{
		return window;
	}

	/**
	 * @return the scene of the login screen
	 */
	public Scene getLogin()
	{
		return login;
	}
	/**
	 * @return the scene of the register screen
	 */
	public Scene getRegister()
	{
		return register;
	}
	/**
	 * @return the scene of the classic mode screen
	 */
	public Scene getGameplayClassic()
	{
		return gameplayClassic;
	}
	/**
	 * @return the scene of the combo mode screen
	 */
	public Scene getGameplayCombo()
	{
		return gameplayCombo;
	}
	/**
	 * @return the scene of the trade mode screen
	 */
	public Scene getGameplayTrade()
	{
		return gameplayTrade;
	}
	/**
	 * @return the scene of the stats screen
	 */
	public Scene getStats()
	{
		return stats;
	}
	/**
	 * @return the scene of the settings screen
	 */
	public Scene getSettings()
	{
		return settings;
	}
	/**
	 * @return the scene of the end match classic screen
	 */
	public Scene getEndMatchClassic()
	{
		return endMatchClassic;
	}
	/**
	 * @return the scene of the end match combo screen
	 */
	public Scene getEndMatchCombo()
	{
		return endMatchCombo;
	}
	/**
	 * @return the scene of the end match trade screen
	 */
	public Scene getEndMatchTrade()
	{
		return endMatchTrade;
	}
	/**
	 * @return the scene of the end game screen
	 */
	public Scene getEndgame()
	{
		return endgame;
	}

	/**
	 * set the primary stage of the application
	 *
	 * @param stage the primary stage of the application
	 */
	public void setWindow(Stage stage)
	{
		window = stage;
	}
	/**
	 * set the current game controller
	 *
	 * @param modality the current game mode
	 */
	public void setCurrentGameController(TurnOrder.MODALITY modality)
	{
		currentGameController = switch (modality)
				{
					case CLASSIC -> (GameplayClassicController) gameplayClassic.getUserData();
					case COMBO -> (GameplayComboController) gameplayCombo.getUserData();
					case TRADE -> (GameplayTradeController) gameplayTrade.getUserData();
				};
	}

	/**
	 * load all the scenes of the application
	 *
	 * @throws IOException if the FXML file is not found or is corrupted
	 */
	public void load() throws IOException
	{
		FXMLLoader loader = new FXMLLoader(GenView.class.getResource("Login.fxml"));
		login = new Scene(loader.load());
		login.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("Register.fxml"));
		register = new Scene(loader.load());
		register.setUserData(loader.getController());

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

		loader = new FXMLLoader(GenView.class.getResource("EndMatchClassic.fxml"));
		endMatchClassic = new Scene(loader.load());
		endMatchClassic.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("EndMatchCombo.fxml"));
		endMatchCombo = new Scene(loader.load());
		endMatchCombo.setUserData(loader.getController());

		loader = new FXMLLoader(GenView.class.getResource("EndMatchTrade.fxml"));
		endMatchTrade = new Scene(loader.load());
		endMatchTrade.setUserData(loader.getController());
	}
	/**
	 * close the application and save
	 */
	public void closeWindow()
	{
		User.save();
		window.close();
		System.exit(0);
	}

	/**
	 * change the current scene of the application
	 *
	 * @param s      the new scene of the application
	 * @param anchor the anchor pane of the old scene
	 */
	public void changeScene(SCENES s, AnchorPane anchor)
	{
		Scene scene = (switch (s)
				{
					case LOGIN -> login;
					case REGISTER -> register;
					case STARTMENU -> startMenu;
					case CHOOSEMODE -> chooseMode;
					case STATS -> stats;
					case SETTINGS -> settings;
					case GAMEPLAYCLASSIC -> gameplayClassic;
					case ENDGAME -> endgame;
					case GAMEPLAYCOMBO -> gameplayCombo;
					case GAMEPLAYTRADE -> gameplayTrade;
					case ENDMATCHCLASSIC -> endMatchClassic;
					case ENDMATCHCOMBO -> endMatchCombo;
					case ENDMATCHTRADE -> endMatchTrade;
				});

		makeFadeOut(scene, anchor);
	}

	/**
	 * make the scene fade out animation
	 *
	 * @param newScene  the new scene of the application
	 * @param oldAnchor the anchor pane of the old scene
	 */
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
	/**
	 * make the scene fade in animation and change the current scene of the application
	 *
	 * @param node tha anchor pane of the new scene
	 */
	private void makeFadeIn(Node node)
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), node);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.play();
	}

	/**
	 * receive the messages of the model and send them to the current controller
	 *
	 * @param o   the observable object.
	 * @param arg an argument passed to the {@code notifyObservers}
	 *            method.
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		if (arg instanceof DrawData drawData)
			Platform.runLater(() -> currentGameController.draw(drawData));

		else if (arg instanceof DiscardData discardData)
			Platform.runLater(() -> currentGameController.discard(discardData));

		else if (arg instanceof TurnData turnData)
			Platform.runLater(() -> currentGameController.turn(turnData));

		else if (arg instanceof EffectData effectData)
			Platform.runLater(() -> currentGameController.effect(effectData));

		else if (arg instanceof GameflowData gameflowData)
			Platform.runLater(() -> currentGameController.gameflow(gameflowData));

		else if (arg instanceof SwitchData switchData)
			Platform.runLater(() -> currentGameController.doSwitch(switchData));

		else if (arg instanceof PointsData pointsData)
			Platform.runLater(() -> currentGameController.getPoints(pointsData));
	}
}
