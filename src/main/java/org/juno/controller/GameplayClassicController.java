package org.juno.controller;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
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

	private static final String USER_DIR = System.getProperty("user.dir");
	private final PathTransition pathDraw = new PathTransition(Duration.millis(750), new Line(845.5, 540, 0, 0));
	private final RotateTransition drawRotate = new RotateTransition(Duration.millis(750));

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
		{
			colorGrid.setVisible(true);
			colorPane.setVisible(true);
		}
	}

	@FXML
	public void drawClicked()
	{
		Player player = TABLE_CLASSIC.getCurrentPlayer();

		if (player.getId() != BuildMP.PG.PLAYER) return;

		Thread thread = new Thread(player::draw);
		thread.start();

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
		zoomAnimation(BuildMP.Effects.RED);
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.RED);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}
	@FXML
	public void blueClicked()
	{
		zoomAnimation(BuildMP.Effects.BLUE);
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.BLUE);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}
	@FXML
	public void yellowClicked()
	{
		zoomAnimation(BuildMP.Effects.YELLOW);
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.YELLOW);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}
	@FXML
	public void greenClicked()
	{
		zoomAnimation(BuildMP.Effects.GREEN);
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.GREEN);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
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
	private void fixWidth(HBox box)
	{
		double spacing = ((box.getChildren().size() * Costants.CARD_WIDTH_SCALED.getVal()) - (box.getMaxWidth())) / box.getChildren().size();


		if (spacing <= 0) spacing = 30;

		box.setSpacing(-spacing);
	}

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

	private ImageView createDrawTransitionCard(Card card)
	{
		ImageView newCard = new ImageView(new Image("file:\\" + USER_DIR + "\\src\\main\\resources\\org\\juno\\images\\back.png"));
		newCard.setFitWidth(Costants.CARD_WIDTH_SCALED.getVal());
		newCard.setFitHeight(Costants.CARD_HEIGHT_SCALED.getVal());
		newCard.setUserData(card);

		anchorPane.getChildren().add(newCard);

		return newCard;
	}
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
			node.setVisible(false);
		});

		return pathTransition;


	}
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
	private ImageView createDiscardTransitionCard(Card card)
	{
		ImageView newCard = new ImageView(new Image(card.getFinalUrl().getPath()));
		newCard.setFitWidth(Costants.CARD_WIDTH_SCALED.getVal());
		newCard.setFitHeight(Costants.CARD_HEIGHT_SCALED.getVal());
		newCard.setUserData(card);

		anchorPane.getChildren().add(newCard);

		return newCard;
	}
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

			node.setVisible(false);
		});

		return pathTransition;
	}
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
			case STOP -> zoomAnimation(BuildMP.Effects.STOP);
			case REVERSE -> zoomAnimation(BuildMP.Effects.REVERSE);
			case JOLLY -> zoomAnimation(BuildMP.Effects.JOLLY);
			case PLUSTWO -> zoomAnimation(BuildMP.Effects.PLUSTWO);
			case PLUSFOUR -> zoomAnimation(BuildMP.Effects.PLUSFOUR);
			case SAIDUNO -> zoomAnimation(BuildMP.Effects.SAIDUNO);
			case DIDNTSAYUNO ->
			{
				juno.setVisible(false);
				zoomAnimation(BuildMP.Effects.DIDNTSAYUNO);
			}
			case ONECARD -> juno.setVisible(true);
			default ->
					throw new RuntimeException("Questo messaggio non dovrebbe essere arrivato " + effectData.effect());
		}
	}

	private ImageView createZoomImage(BuildMP.Effects effect)
	{
		ImageView imageView = new ImageView(new Image("file:\\" + USER_DIR + "\\src\\main\\resources\\org\\juno\\images\\" + switch (effect)
				{
					case STOP -> "stopPopUp.png";
					case REVERSE -> "reversePopUp.png";
					case PLUSTWO -> "plusTwoPopUp.png";
					case PLUSFOUR -> "plusFourPopUp.png";
					case JOLLY -> "jollyPopUp.png";
					case SAIDUNO -> "saidUnoPopUp.png";
					case DIDNTSAYUNO -> "didntSayUnoPopUp.png";
					case ONECARD -> "oneCardPopUp.png";
					case RED -> "redPopUp.png";
					case BLUE -> "bluePopUp.png";
					case YELLOW -> "yellowPopUp.png";
					case GREEN -> "greenPopUp.png";
				}));
		anchorPane.getChildren().add(imageView);
		imageView.setFitWidth(320);
		imageView.setFitHeight(180);
		imageView.setX(800);
		imageView.setY(450);

		return imageView;

	}
	private void zoomAnimation(BuildMP.Effects effect)
	{
		ImageView imageView = createZoomImage(effect);
		ScaleTransition st = new ScaleTransition(Duration.millis(500), imageView);
		ScaleTransition st1 = new ScaleTransition(Duration.millis(500), imageView);
		st.setToX(5);
		st.setToY(5);
		st1.setToX(0.5);
		st1.setToY(0.5);
		st.setOnFinished(x -> st1.play());
		st1.setOnFinished(x -> imageView.setVisible(false));
		st.play();
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
		userTurn.setVisible(false);
		bot1Turn.setVisible(false);
		bot2Turn.setVisible(false);
		bot3Turn.setVisible(false);

		firstDiscarded.setImage(null);
		secondDiscarded.setImage(null);
		thirdDiscarded.setImage(null);
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
		pathDraw.setNode(cardDrawn);
		drawRotate.setNode(cardDrawn);
	}
}
