package org.juno.controller;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.util.Duration;
import org.juno.datapackage.*;
import org.juno.model.deck.Card;
import org.juno.model.deck.WildCard;
import org.juno.model.table.Player;
import org.juno.model.table.classic.TableClassic;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Defines GameplayControllerNew class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class GameplayClassicController implements Gameplay, Initializable
{
	protected static final TableClassic TABLE_CLASSIC = TableClassic.getINSTANCE();
	protected static final GenView GEN_VIEW = GenView.getINSTANCE();
	protected static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

	protected static final int CARD_WIDTH_SCALED = 189;
	protected static final int CARD_HEIGHT_SCALED = 264;

	private static final PathTransition PLAYER_PATH = new PathTransition(Duration.seconds(1), new Line(1147.5, 948, 1147.5, 540));
	private static final PathTransition BOT1_PATH = new PathTransition(Duration.seconds(1), new Line(0, 408, 1147.5, 540));
	private static final PathTransition BOT2_PATH = new PathTransition(Duration.seconds(1), new Line(1053, 0, 1147.5, 540));
	private static final PathTransition BOT3_PATH = new PathTransition(Duration.seconds(1), new Line(1693.5, 408, 1147.5, 540));

	private static final PathTransition PLAYER_DRAW_PATH = new PathTransition(Duration.seconds(1), new Line(858.5, 540, 1147.5, 948));
	private static final PathTransition BOT1_DRAW_PATH = new PathTransition(Duration.seconds(1), new Line(858.5, 540, 0, 408));
	private static final PathTransition BOT2_DRAW_PATH = new PathTransition(Duration.seconds(1), new Line(858.5, 540, 1053, 0));
	private static final PathTransition BOT3_DRAW_PATH = new PathTransition(Duration.seconds(1), new Line(858.5, 540, 1693.5, 408));

	private static final RotateTransition PLAYER_ROTATE = new RotateTransition(Duration.seconds(1));
	private static final RotateTransition BOT1_ROTATE = new RotateTransition(Duration.seconds(1));
	private static final RotateTransition BOT2_ROTATE = new RotateTransition(Duration.seconds(1));
	private static final RotateTransition BOT3_ROTATE = new RotateTransition(Duration.seconds(1));

	private static final RotateTransition DRAW_ROTATE = new RotateTransition(Duration.seconds(1));


	@FXML
	public AnchorPane anchorPane;
	@FXML
	public GridPane colorGrid;


	@FXML
	public Button pass;
	@FXML
	public Button juno;
	@FXML
	public Button exit;


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
	@FXML
	public ImageView playerAnimation;
	@FXML
	public ImageView bot1Animation;
	@FXML
	public ImageView bot2Animation;
	@FXML
	public ImageView bot3Animation;
	@FXML
	public ImageView cardDrawn;
	@FXML
	public ImageView testTestoso;

	private ImageView lastClicked;


	@FXML
	public void cardEntered(MouseEvent mouseEvent)
	{
		if (TABLE_CLASSIC.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

		cardFlip();
		ImageView card = (ImageView) mouseEvent.getSource();
		card.setTranslateY(-30);
	}
	@FXML
	public void cardExited(MouseEvent mouseEvent)
	{
		if (TABLE_CLASSIC.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

		ImageView card = (ImageView) mouseEvent.getSource();
		card.setTranslateY(0);
	}
	@FXML
	public void cardClicked(MouseEvent mouseEvent)
	{
		if (TABLE_CLASSIC.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;
		lastClicked = (ImageView) mouseEvent.getSource();

		Card.Value value = ((Card) lastClicked.getUserData()).getValue();
		Card.Color color = ((Card) lastClicked.getUserData()).getColor();

		System.out.println(color + " " + value);
		TABLE_CLASSIC.getUser().chooseCard(color, value);
		if (color == Card.Color.BLACK && TABLE_CLASSIC.getCurrentPlayer().getId() == BuildMP.PG.PLAYER)
			colorGrid.setVisible(true);
	}


	@FXML
	public void drawClicked()
	{
		Player player = TABLE_CLASSIC.getCurrentPlayer();

		if (player.getId() != BuildMP.PG.PLAYER) return;

		player.draw();
		pass.setDisable(false);
	}
	@FXML
	public void passClicked()
	{
		pass.setDisable(true);

		beep();
		TABLE_CLASSIC.getUser().setHasPassed();
	}


	@FXML
	public void redClicked()
	{
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.RED);
		colorGrid.setVisible(false);
	}
	@FXML
	public void blueClicked()
	{
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.BLUE);
		colorGrid.setVisible(false);
	}
	@FXML
	public void yellowClicked()
	{
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.YELLOW);
		colorGrid.setVisible(false);
	}
	@FXML
	public void greenClicked()
	{
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.GREEN);
		colorGrid.setVisible(false);
	}


	@FXML
	public void sayUno()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		TABLE_CLASSIC.getUser().sayUno();
		juno.setVisible(false);
	}

	@FXML
	public void buttonEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}

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


	@Override
	public void draw(DrawData drawData)
	{
		cardFlip();
		switch (drawData.player())
		{
			case PLAYER ->
			{
				cardDrawn.setImage(new Image(drawData.card().getUrl().getPath()));
				cardDrawn.setVisible(true);
				PLAYER_DRAW_PATH.play();
				ImageView imageView = new ImageView(new Image(drawData.card().getUrl().getPath()));

				imageView.setUserData(drawData.card());

				imageView.setFitWidth(CARD_WIDTH_SCALED);
				imageView.setFitHeight(CARD_HEIGHT_SCALED);

				imageView.setOnMouseClicked(this::cardClicked);
				imageView.setOnMouseEntered(this::cardEntered);
				imageView.setOnMouseExited(this::cardExited);

				userHand.getChildren().add(imageView);
				fixWidth(userHand);
			}
			case BOT1 ->
			{
				drawBot(botHand1);
				cardDrawn.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));
				cardDrawn.setVisible(true);
				BOT1_DRAW_PATH.play();
				DRAW_ROTATE.play();
			}
			case BOT2 ->
			{
				drawBot(botHand2);
				cardDrawn.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));
				cardDrawn.setVisible(true);
				BOT2_DRAW_PATH.play();
			}
			case BOT3 ->
			{
				drawBot(botHand3);
				cardDrawn.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));
				cardDrawn.setVisible(true);
				BOT3_DRAW_PATH.play();
				DRAW_ROTATE.play();
			}
		}
	}

	private void drawBot(HBox botHand)
	{
		ImageView imageView = new ImageView(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));

		imageView.setFitWidth(CARD_WIDTH_SCALED);
		imageView.setFitHeight(CARD_HEIGHT_SCALED);

		botHand.getChildren().add(imageView);
		fixWidth(botHand);
	}
	@Override
	public void discard(DiscardData discardData)
	{
		if (discardData.player() == null)
		{
			firstDiscarded.setImage(new Image(discardData.card().getUrl().getPath()));
			return;
		}
		preAnimation(discardData.player(), discardData.card());
		PathTransition path = switch (discardData.player())
				{
					case PLAYER -> PLAYER_PATH;
					case BOT1 -> BOT1_PATH;
					case BOT2 -> BOT2_PATH;
					case BOT3 -> BOT3_PATH;
				};

		path.setOnFinished(x -> postAnimation(discardData.card()));


		animation(discardData.player());


	}
	protected void preAnimation(BuildMP.PG player, Card card)
	{

		switch (player)
		{
			case PLAYER ->
			{
//				System.out.printf("Layout X = %f%n%n", lastClicked.getLayoutX());
//				playerAnimation.setX(lastClicked.getLayoutX() + 298.5);
				userHand.getChildren().remove(lastClicked);
				fixWidth(userHand);

				playerAnimation.setVisible(true);
				playerAnimation.setImage(new Image(card.getUrl().getPath()));
			}
			case BOT1 ->
			{
				botHand1.getChildren().remove(0);
				fixWidth(botHand1);
				bot1Animation.setVisible(true);
				bot1Animation.setImage(new Image(card.getUrl().getPath()));
			}
			case BOT2 ->
			{
				botHand2.getChildren().remove(0);
				fixWidth(botHand2);
				bot2Animation.setVisible(true);
				bot2Animation.setImage(new Image(card.getUrl().getPath()));
			}
			case BOT3 ->
			{
				botHand3.getChildren().remove(0);
				fixWidth(botHand3);
				bot3Animation.setVisible(true);
				bot3Animation.setImage(new Image(card.getUrl().getPath()));
			}
		}
	}
	public void animation(BuildMP.PG pg)
	{
		switch (pg)
		{
			case PLAYER ->
			{
				PLAYER_PATH.play();
				PLAYER_ROTATE.play();
			}
			case BOT1 ->
			{
				BOT1_PATH.play();
				BOT1_ROTATE.play();
			}
			case BOT2 ->
			{
				BOT2_PATH.play();
				BOT2_ROTATE.play();
			}
			case BOT3 ->
			{
				BOT3_PATH.play();
				BOT3_ROTATE.play();
			}
		}
	}
	public void postAnimation(Card card)
	{
		beep();

		thirdDiscarded.setImage(secondDiscarded.getImage());
		secondDiscarded.setImage(firstDiscarded.getImage());
		firstDiscarded.setImage(new Image(card.getFinalUrl().getPath()));
	}
	private void fixWidth(HBox box)
	{
		double spacing = ((box.getChildren().size() * CARD_WIDTH_SCALED) - (box.getMaxWidth())) / box.getChildren().size();

		if (spacing < 0) spacing = 0;

		box.setSpacing(-spacing);
	}


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

		for (Node card : userHand.getChildren())
		{
			card.setTranslateY(0);
		}

	}
	@Override
	public void effect(EffectData effectData)
	{
		switch (effectData.effect())
		{
			case STOP ->
			{/*todo*/}
			case REVERSE ->
			{/*todo*/}
			case PLUSTWO ->
			{/*todo*/}
			case PLUSFOUR ->
			{/*todo*/}
			case JOLLY ->
			{/*todo*/}
			case SAIDUNO ->
			{/*todo*/}
			case DIDNTSAYUNO -> juno.setVisible(false);
			case JUMPIN ->
			{/*todo*/}
			case ONECARD -> juno.setVisible(true);
		}
	}

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

	@Override
	public void getPoints(PointsData pointsData)
	{
		((EndMatchClassicController) GEN_VIEW.getEndMatchClassic().getUserData()).load(pointsData);
	}


	private void nextMatch()
	{
		GEN_VIEW.changeScene(GenView.SCENES.ENDMATCHCLASSIC, anchorPane);
	}
	private void goEndgame()
	{
		GEN_VIEW.changeScene(GenView.SCENES.ENDGAME, anchorPane);

		((EndgameController) GEN_VIEW.getEndgame().getUserData()).load(TABLE_CLASSIC);
	}
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

		TABLE_CLASSIC.canStart();
	}


	protected void beep()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
	}
	protected void cardFlip()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CARDFLIP);
	}
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		PLAYER_PATH.setNode(playerAnimation);
		PLAYER_ROTATE.setNode(playerAnimation);
		PLAYER_ROTATE.setByAngle(0);


		PLAYER_ROTATE.setOnFinished(x ->
		{
			playerAnimation.setVisible(false);
			playerAnimation.setRotate(0);
		});

		BOT1_PATH.setNode(bot1Animation);
		BOT1_ROTATE.setNode(bot1Animation);
		BOT1_ROTATE.setByAngle(-90);


		BOT1_ROTATE.setOnFinished(x ->
		{
			bot1Animation.setVisible(false);
			bot1Animation.setRotate(90);
		});

		BOT2_PATH.setNode(bot2Animation);
		BOT2_ROTATE.setNode(bot2Animation);
		BOT2_ROTATE.setByAngle(-180);


		BOT2_ROTATE.setOnFinished(x ->
		{
			bot2Animation.setVisible(false);
			bot2Animation.setRotate(180);
		});

		BOT3_PATH.setNode(bot3Animation);
		BOT3_ROTATE.setNode(bot3Animation);
		BOT3_ROTATE.setByAngle(90);


		BOT3_ROTATE.setOnFinished(x ->
		{
			bot3Animation.setVisible(false);
			bot3Animation.setRotate(-90);
		});

		PLAYER_DRAW_PATH.setNode(cardDrawn);
		PLAYER_DRAW_PATH.setOnFinished(x -> cardDrawn.setVisible(false));

		BOT1_DRAW_PATH.setNode(cardDrawn);
		DRAW_ROTATE.setNode(cardDrawn);
		DRAW_ROTATE.setByAngle(90);

		DRAW_ROTATE.setOnFinished(x ->
		{
			cardDrawn.setVisible(false);
			cardDrawn.setRotate(0);
		});

		BOT2_DRAW_PATH.setNode(cardDrawn);
		BOT2_DRAW_PATH.setOnFinished(x -> cardDrawn.setVisible(false));

		BOT3_DRAW_PATH.setNode(cardDrawn);
		BOT3_DRAW_PATH.setOnFinished(x -> cardDrawn.setVisible(false));

		/**
		 * Partenza = partenza + meta carta + layour del parent
		 * Destinazione finale = destinazione + meta carta + layout del parent
		 *
		 *
		 * Ogni volta che voglio fare un' animazione, devo settare nuovamente la partenza (o l'arrivo a seconda dell'animazione)
		 */

	}
}
