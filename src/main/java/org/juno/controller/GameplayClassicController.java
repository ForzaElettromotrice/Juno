package org.juno.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.juno.datapackage.*;
import org.juno.model.deck.Card;
import org.juno.model.deck.WildCard;
import org.juno.model.table.Player;
import org.juno.model.table.classic.TableClassic;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Defines GameplayControllerNew class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class GameplayClassicController implements Gameplay
{
	protected static final TableClassic TABLE_CLASSIC = TableClassic.getINSTANCE();
	protected static final GenView GEN_VIEW = GenView.getINSTANCE();
	protected static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

	private static final String USER_DIR = System.getProperty("user.dir");

	@FXML
	public AnchorPane anchorPane;


	@FXML
	public Button pass;
	@FXML
	public Button juno;


	@FXML
	public HBox userHand;
	@FXML
	public HBox botHand1;
	@FXML
	public HBox botHand2;
	@FXML
	public HBox botHand3;


	@FXML
	public Circle userCircle;
	@FXML
	public Circle bot1Circle;
	@FXML
	public Circle bot2Circle;
	@FXML
	public Circle bot3Circle;


	@FXML
	public ImageView colorGrid;


	@FXML
	public ImageView userTurn;
	@FXML
	public ImageView bot1Turn;
	@FXML
	public ImageView bot2Turn;
	@FXML
	public ImageView bot3Turn;


	@FXML
	public ImageView firstDiscarded;
	@FXML
	public ImageView secondDiscarded;
	@FXML
	public ImageView thirdDiscarded;
	public ImageView cardDrawn;

	@FXML
	public GridPane colorPane;
	private ImageView lastClicked;

	/**
	 * called when the mouse enters a card
	 *
	 * @param mouseEvent the mouse event
	 */
	public void cardEntered(MouseEvent mouseEvent)
	{
		if (TABLE_CLASSIC.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

		cardFlip();
		ImageView card = (ImageView) mouseEvent.getSource();
		card.setTranslateY(-30);
	}

	/**
	 * called when the mouse exits a card
	 *
	 * @param mouseEvent the mouse event
	 */
	public void cardExited(MouseEvent mouseEvent)
	{
		if (TABLE_CLASSIC.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

		ImageView card = (ImageView) mouseEvent.getSource();
		card.setTranslateY(0);
	}

	/**
	 * called when the mouse clicks a card
	 *
	 * @param mouseEvent the mouse event
	 */
	public void cardClicked(MouseEvent mouseEvent)
	{
		if (TABLE_CLASSIC.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

		lastClicked = (ImageView) mouseEvent.getSource();

		Card.Value value = ((Card) lastClicked.getUserData()).getValue();
		Card.Color color = ((Card) lastClicked.getUserData()).getColor();

		System.out.println(color + " " + value);
		TABLE_CLASSIC.getUser().chooseCard(color, value);
		if (color == Card.Color.BLACK && TABLE_CLASSIC.getCurrentPlayer().getId() == BuildMP.PG.PLAYER)
		{
			colorGrid.setVisible(true);
			colorPane.setVisible(true);
		}
	}

	/**
	 * called when the mouse clicks the draw pile
	 */
	@FXML
	public void drawClicked()
	{
		Player player = TABLE_CLASSIC.getCurrentPlayer();

		if (player.getId() != BuildMP.PG.PLAYER) return;

		Thread thread = new Thread(player::draw);
		thread.start();

		pass.setDisable(false);
	}
	/**
	 * called when the mouse clicks the pass button
	 */
	@FXML
	public void passClicked()
	{
		pass.setDisable(true);

		beep();
		TABLE_CLASSIC.getUser().setHasPassed();
	}

	/**
	 * called when the mouse clicks the red button
	 */
	@FXML
	public void redClicked()
	{
		zoomAnimation(BuildMP.Effects.RED);
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.RED);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}

	/**
	 * called when the mouse clicks the blue button
	 */
	@FXML
	public void blueClicked()
	{
		zoomAnimation(BuildMP.Effects.BLUE);
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.BLUE);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}

	/**
	 * called when the mouse clicks the yellow button
	 */
	@FXML
	public void yellowClicked()
	{
		zoomAnimation(BuildMP.Effects.YELLOW);
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.YELLOW);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}

	/**
	 * called when the mouse clicks the green button
	 */
	@FXML
	public void greenClicked()
	{
		zoomAnimation(BuildMP.Effects.GREEN);
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.GREEN);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}

	/**
	 * called when the mouse clicks the juno button
	 */
	@FXML
	public void sayUno()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		TABLE_CLASSIC.getUser().sayUno();
		juno.setVisible(false);
	}

	/**
	 * play button entered sound
	 */
	@FXML
	public void buttonEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}

	/**
	 * called when the user clicks the exit button
	 */
	@FXML
	public void exitClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? You will lose the match by default!", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.setHeaderText("Confirm");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) return;
		TABLE_CLASSIC.stopEarlier();
	}

	/**
	 * fix the overlapping of the cards
	 *
	 * @param box the box who contain the cards
	 */
	private void fixWidth(HBox box)
	{
		double spacing = ((box.getChildren().size() * Costants.CARD_WIDTH_SCALED.getVal()) - (box.getMaxWidth())) / box.getChildren().size();


		if (spacing <= 0) spacing = 30;

		box.setSpacing(-spacing);
	}

	/**
	 * create the animation of the card draw
	 *
	 * @param drawData the data of the card draw
	 */
	@Override
	public void draw(DrawData drawData)
	{
		cardFlip();
		ImageView newCard = createDrawTransitionCard(drawData.card());

		PathTransition pathTransition = createDrawPathTransition(drawData.player(), newCard);
		RotateTransition rotateTransition = drawData.player() == BuildMP.PG.PLAYER ? createPlayerDrawRotateTransition(newCard) : createBotDrawRotateTransition(drawData.player(), newCard);


		pathTransition.play();
		rotateTransition.play();
	}

	/**
	 * create the imageview of the card draw for the animation
	 *
	 * @param card the card draw
	 * @return the imageview of the card draw
	 */
	private ImageView createDrawTransitionCard(Card card)
	{
		ImageView newCard = new ImageView(new Image("file:\\" + USER_DIR + "\\src\\main\\resources\\org\\juno\\images\\back.png"));
		newCard.setFitWidth(Costants.CARD_WIDTH_SCALED.getVal());
		newCard.setFitHeight(Costants.CARD_HEIGHT_SCALED.getVal());
		newCard.setUserData(card);

		anchorPane.getChildren().add(newCard);

		return newCard;
	}
	/**
	 * create the imageview of the card draw for the hand of the player
	 *
	 * @param image    the image of the card draw
	 * @param card     the card draw
	 * @param isPlayer true if the card is for the player
	 * @return the imageview of the card draw
	 */
	private ImageView createCard(Image image, Card card, boolean isPlayer)
	{
		ImageView newCard = new ImageView(image);
		newCard.setFitWidth(Costants.CARD_WIDTH_SCALED.getVal());
		newCard.setFitHeight(Costants.CARD_HEIGHT_SCALED.getVal());
		newCard.setUserData(card);


		if (isPlayer)
		{
			newCard.setOnMouseEntered(this::cardEntered);
			newCard.setOnMouseExited(this::cardExited);
			newCard.setOnMouseClicked(this::cardClicked);
		}


		return newCard;
	}

	/**
	 * create the path transition of the card draw
	 *
	 * @param pg   the player who draw the card
	 * @param node the imageview of the card draw
	 * @return the path transition of the card draw
	 */
	private PathTransition createDrawPathTransition(BuildMP.PG pg, ImageView node)
	{
		double endX = Costants.CARD_WIDTH_SCALED.getVal() / 2;
		double endY = Costants.CARD_HEIGHT_SCALED.getVal() / 2;
		HBox hand = userHand;

		switch (pg)
		{

			case PLAYER ->
			{
				endX += Costants.PARENT_PLAYER_CENTER_X.getVal();
				endY += Costants.PARENT_PLAYER_CENTER_Y.getVal();
			}
			case BOT1 ->
			{
				endX += Costants.PARENT_BOT1_CENTER_X.getVal();
				endY += Costants.PARENT_BOT1_CENTER_Y.getVal();
				hand = botHand1;
			}
			case BOT2 ->
			{
				endX += Costants.PARENT_BOT2_CENTER_X.getVal();
				endY += Costants.PARENT_BOT2_CENTER_Y.getVal();
				hand = botHand2;
			}
			case BOT3 ->
			{
				endX += Costants.PARENT_BOT3_CENTER_X.getVal();
				endY += Costants.PARENT_BOT3_CENTER_Y.getVal();
				hand = botHand3;
			}
		}

		PathTransition pathTransition = new PathTransition(Duration.millis(750), new Line(845.5, 540, endX, endY), node);

		HBox finalHand = hand;

		pathTransition.setOnFinished(x ->
		{
			ImageView imageView = createCard(node.getImage(), (Card) node.getUserData(), pg == BuildMP.PG.PLAYER);
			finalHand.getChildren().add(imageView);
			fixWidth(finalHand);
			anchorPane.getChildren().remove(node);
		});

		return pathTransition;


	}

	/**
	 * create the rotation transition of the card draw for the bot
	 *
	 * @param pg   the player who draw the card
	 * @param node the imageview of the card draw
	 * @return the rotation transition of the card draw
	 */
	private RotateTransition createBotDrawRotateTransition(BuildMP.PG pg, ImageView node)
	{
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(750), node);

		switch (pg)
		{

			case PLAYER ->
			{
				rotateTransition.setByAngle(180);
				node.setRotate(180);
			}
			case BOT1 -> rotateTransition.setByAngle(90);
			case BOT2 -> rotateTransition.setByAngle(180);
			case BOT3 -> rotateTransition.setByAngle(270);
		}
		return rotateTransition;
	}

	/**
	 * create the rotation transition of the card draw for the player
	 *
	 * @param node the imageview of the card draw
	 * @return the rotation transition of the card draw
	 */
	private RotateTransition createPlayerDrawRotateTransition(ImageView node)
	{
		node.setRotate(180);

		RotateTransition rotateTransition = new RotateTransition(Duration.millis(375), node);
		RotateTransition rotateTransition2 = new RotateTransition(Duration.millis(375), node);

		rotateTransition.setInterpolator(Interpolator.LINEAR);
		rotateTransition2.setInterpolator(Interpolator.LINEAR);

		rotateTransition.setAxis(Rotate.X_AXIS);
		rotateTransition2.setAxis(Rotate.X_AXIS);

		rotateTransition.setByAngle(90);
		rotateTransition2.setByAngle(90);

		rotateTransition.setOnFinished(x ->
		{
			node.setImage(new Image(((Card) node.getUserData()).getUrl().getPath()));
			rotateTransition2.play();
		});

		return rotateTransition;
	}

	/**
	 * make the animation of the card played by the player
	 *
	 * @param discardData the data of the card played
	 */
	@Override
	public void discard(DiscardData discardData)
	{
		beep();
		if (discardData.player() == null)
		{
			firstDiscarded.setImage(new Image(discardData.card().getFinalUrl().getPath()));
			return;
		}
		ImageView transitionCard = createDiscardTransitionCard(discardData.card());

		PathTransition pathTransition = createDiscardPathTransition(discardData.player(), transitionCard);
		RotateTransition rotateTransition = createDiscardRotateTransition(discardData.player(), transitionCard);
		removeCard(discardData.player());

		pathTransition.play();
		rotateTransition.play();
	}

	/**
	 * create the imageview of the card played for the animation
	 *
	 * @param card the card played
	 * @return the imageview of the card played
	 */
	private ImageView createDiscardTransitionCard(Card card)
	{
		ImageView newCard = new ImageView(new Image(card.getFinalUrl().getPath()));
		newCard.setFitWidth(Costants.CARD_WIDTH_SCALED.getVal());
		newCard.setFitHeight(Costants.CARD_HEIGHT_SCALED.getVal());
		newCard.setUserData(card);

		anchorPane.getChildren().add(newCard);

		return newCard;
	}
	/**
	 * create the path transition of the card played
	 *
	 * @param pg   the player who played the card
	 * @param node the imageview of the card played
	 * @return the path transition of the card played
	 */
	private PathTransition createDiscardPathTransition(BuildMP.PG pg, ImageView node)
	{
		double startX = Costants.CARD_WIDTH_SCALED.getVal() / 2;
		double startY = Costants.CARD_HEIGHT_SCALED.getVal() / 2;

		ImageView cardInHand;

		switch (pg)
		{

			case PLAYER ->
			{
				cardInHand = (ImageView) userHand.getChildren().stream().filter(x -> x.getUserData().equals(node.getUserData())).toList().get(0);

				startX += cardInHand.getLayoutX() + Costants.PARENT_PLAYER_X.getVal();
				startY += cardInHand.getLayoutY() + Costants.PARENT_PLAYER_Y.getVal();
			}
			case BOT1 ->
			{
				cardInHand = (ImageView) botHand1.getChildren().get(0);
				startX += cardInHand.getLayoutX() + Costants.PARENT_BOT1_X.getVal();
				startY += cardInHand.getLayoutY() + Costants.PARENT_BOT1_Y.getVal();
			}
			case BOT2 ->
			{
				cardInHand = (ImageView) botHand2.getChildren().get(0);
				startX += cardInHand.getLayoutX() + Costants.PARENT_BOT2_X.getVal();
				startY += cardInHand.getLayoutY() + Costants.PARENT_BOT2_Y.getVal();
			}
			case BOT3 ->
			{
				cardInHand = (ImageView) botHand3.getChildren().get(0);
				startX += cardInHand.getLayoutX() + Costants.PARENT_BOT3_X.getVal();
				startY += cardInHand.getLayoutY() + Costants.PARENT_BOT3_Y.getVal();
			}
		}


		PathTransition pathTransition = new PathTransition(Duration.seconds(1), new Line(startX, startY, 1074.5, 540), node);

		pathTransition.setOnFinished(x ->
		{
			thirdDiscarded.setImage(secondDiscarded.getImage());
			secondDiscarded.setImage(firstDiscarded.getImage());
			firstDiscarded.setImage(node.getImage());

			anchorPane.getChildren().remove(node);
		});

		return pathTransition;
	}

	/**
	 * create the rotation transition of the card played
	 *
	 * @param pg   the player who played the card
	 * @param node the imageview of the card played
	 * @return the rotation transition of the card played
	 */
	private RotateTransition createDiscardRotateTransition(BuildMP.PG pg, ImageView node)
	{
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), node);

		switch (pg)
		{

			case PLAYER -> rotateTransition.setByAngle(360);
			case BOT1 ->
			{
				rotateTransition.setByAngle(270);
				node.setRotate(90);
			}
			case BOT2 ->
			{
				rotateTransition.setByAngle(180);
				node.setRotate(180);
			}
			case BOT3 ->
			{
				rotateTransition.setByAngle(90);
				node.setRotate(270);
			}
		}


		return rotateTransition;
	}
	/**
	 * remove the card played from the hand of the player
	 *
	 * @param pg the player who played the card
	 */
	private void removeCard(BuildMP.PG pg)
	{
		HBox hand = switch (pg)
				{
					case PLAYER -> userHand;
					case BOT1 -> botHand1;
					case BOT2 -> botHand2;
					case BOT3 -> botHand3;
				};

		if (pg == BuildMP.PG.PLAYER)
			hand.getChildren().remove(lastClicked);
		else
			hand.getChildren().remove(0);
	}

	/**
	 * make the animation of the "start turn" of the player
	 * @param turnData the data of the turn
	 */
	@Override
	public void turn(TurnData turnData)
	{
		pass.setDisable(true);
		userTurn.setVisible(false);
		bot1Turn.setVisible(false);
		bot2Turn.setVisible(false);
		bot3Turn.setVisible(false);

		switch (turnData.player())
		{
			case PLAYER -> userTurn.setVisible(true);
			case BOT1 -> bot1Turn.setVisible(true);
			case BOT2 -> bot2Turn.setVisible(true);
			case BOT3 -> bot3Turn.setVisible(true);
		}

		userHand.getChildren().forEach(card -> card.setTranslateY(0));

	}

	/**
	 * make the animation of the given effect
	 *
	 * @param effectData the data of the effect
	 */
	@Override
	public void effect(EffectData effectData)
	{
		if (effectData.effect() == BuildMP.Effects.DIDNTSAYUNO) juno.setVisible(false);
		else if (effectData.effect() == BuildMP.Effects.ONECARD)
		{
			juno.setVisible(true);
			return;
		}

		zoomAnimation(effectData.effect());

	}

	/**
	 * create the imageview of the effect for the animation
	 *
	 * @param effect the effect
	 * @return the imageview of the effect
	 */
	private ImageView createZoomImage(BuildMP.Effects effect)
	{
		ImageView imageView = new ImageView(new Image("file:\\" + USER_DIR + "\\src\\main\\resources\\org\\juno\\images\\" + effect.getPath()));
		anchorPane.getChildren().add(imageView);

		if (effect == BuildMP.Effects.SAIDUNO || effect == BuildMP.Effects.DIDNTSAYUNO)
		{
			imageView.setFitWidth(1000);
			imageView.setFitHeight(1000);
			imageView.setX(460);
			imageView.setY(40);
		} else
		{
			imageView.setFitWidth(500);
			imageView.setFitHeight(500);
			imageView.setX(710);
			imageView.setY(290);
		}

		return imageView;

	}

	/**
	 * make the animation of the given effect
	 *
	 * @param effect the effect
	 */
	private void zoomAnimation(BuildMP.Effects effect)
	{
		ImageView imageView = createZoomImage(effect);

		FadeTransition ft = new FadeTransition(Duration.millis(200), imageView);

		ft.setFromValue(1);
		ft.setToValue(0);
		ft.setOnFinished(x -> anchorPane.getChildren().remove(imageView));

		Timer timer = new Timer();

		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				ft.play();
				timer.cancel();
			}
		}, 1966);

	}


	/**
	 * this method should never be called in this mode
	 *
	 * @param switchData the data of the switch
	 */
	@Override
	public void doSwitch(SwitchData switchData)
	{
		//Has to be void because this modality doesn't support the switch
		try
		{
			throw new UnsupportedModalityException("CLASSIC can't do SWITCH operation");
		} catch (UnsupportedModalityException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
	}

	/**
	 * manage the start, endmatch and endgame actions
	 *
	 * @param gameflowData the data of the gameflow
	 */
	@Override
	public void gameflow(GameflowData gameflowData)
	{
		switch (gameflowData.gf())
		{
			case ENDGAME -> goEndgame();
			case ENDMATCH -> nextMatch();
			case STARTGAME -> reset();
		}
	}

	/**
	 * load the points of the players
	 *
	 * @param pointsData the data of the points
	 */
	@Override
	public void getPoints(PointsData pointsData)
	{
		((EndMatchClassicController) GEN_VIEW.getEndMatchClassic().getUserData()).load(pointsData);
	}

	/**
	 * load the endmatch scene
	 */
	private void nextMatch()
	{
		GEN_VIEW.changeScene(GenView.SCENES.ENDMATCHCLASSIC, anchorPane);
	}
	/**
	 * load the endgame scene
	 */
	private void goEndgame()
	{
		GEN_VIEW.changeScene(GenView.SCENES.ENDGAME, anchorPane);

		((EndgameController) GEN_VIEW.getEndgame().getUserData()).load(TABLE_CLASSIC);
	}
	/**
	 * reset the scene
	 */
	public void reset()
	{
		anchorPane.requestFocus();
		userHand.getChildren().clear();
		botHand1.getChildren().clear();
		botHand2.getChildren().clear();
		botHand3.getChildren().clear();
		userCircle.setFill(new ImagePattern(new Image(User.getInstance().getAvatar())));
		bot1Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot1.png", System.getProperty("user.dir")))));
		bot2Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot2.png", System.getProperty("user.dir")))));
		bot3Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot3.png", System.getProperty("user.dir")))));
		userTurn.setVisible(false);
		bot1Turn.setVisible(false);
		bot2Turn.setVisible(false);
		bot3Turn.setVisible(false);

		colorGrid.setVisible(false);
		colorPane.setVisible(false);

		firstDiscarded.setImage(null);
		secondDiscarded.setImage(null);
		thirdDiscarded.setImage(null);
		TABLE_CLASSIC.canStart();
	}

	/**
	 * play the "beep" sound
	 */
	protected void beep()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
	}
	/**
	 * play the "card flip" sound
	 */
	protected void cardFlip()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CARDFLIP);
	}

}
