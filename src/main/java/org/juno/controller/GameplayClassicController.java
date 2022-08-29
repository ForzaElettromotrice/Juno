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


	private final PathTransition pathDisc = new PathTransition(Duration.seconds(1), new Line(0, 0, 1074.5, 540));
	private final PathTransition pathDraw = new PathTransition(Duration.millis(750), new Line(845.5, 540, 0, 0));

	private final RotateTransition discardRotate = new RotateTransition(Duration.seconds(1));
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
		colorPane.setVisible(false);
	}
	@FXML
	public void blueClicked()
	{
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.BLUE);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}
	@FXML
	public void yellowClicked()
	{
		beep();
		((WildCard) TABLE_CLASSIC.getUser().getChosenCard()).setColor(Card.Color.YELLOW);
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
	}
	@FXML
	public void greenClicked()
	{
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


	@Override
	public void draw(DrawData drawData)
	{
		ImageView newCard = createCard(drawData.card(), drawData.player());

		setPathDraw(drawData.player(), drawData.card(), newCard);
		setRotationDraw(drawData.player());

		cardFlip();

		cardDrawn.setVisible(true);
		pathDraw.play();
		drawRotate.play();
	}

	private ImageView createCard(Card card, BuildMP.PG player)
	{
		ImageView newCard = new ImageView(new Image(player == BuildMP.PG.PLAYER ? card.getUrl().getPath() : String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));

		newCard.setUserData(card);

		newCard.setFitWidth(Costants.CARD_WIDTH_SCALED.getVal());
		newCard.setFitHeight(Costants.CARD_HEIGHT_SCALED.getVal());

		if (player == BuildMP.PG.PLAYER)
		{
			newCard.setOnMouseClicked(this::cardClicked);
			newCard.setOnMouseEntered(this::cardEntered);
			newCard.setOnMouseExited(this::cardExited);
		}

		newCard.setVisible(false);


		return newCard;
	}

	@Override
	public void discard(DiscardData discardData)
	{
		colorGrid.setVisible(false);
		colorPane.setVisible(false);
		if (discardData.player() == null)
		{
			firstDiscarded.setImage(new Image(discardData.card().getFinalUrl().getPath()));
			return;
		}
		setPathDiscard(discardData.player(), discardData.card());
		setRotationDiscard(discardData.player());
		removeCard(discardData.player());

		pathDisc.play();
		discardRotate.play();

	}
	public void removeCard(BuildMP.PG player)
	{
		switch (player)
		{
			case PLAYER ->
			{
				userHand.getChildren().remove(lastClicked);
				fixWidth(userHand);
			}
			case BOT1 ->
			{
				botHand1.getChildren().remove(0);
				fixWidth(botHand1);
			}
			case BOT2 ->
			{
				botHand2.getChildren().remove(0);
				fixWidth(botHand2);
			}
			case BOT3 ->
			{
				botHand3.getChildren().remove(0);
				fixWidth(botHand3);
			}
		}
	}
	private void fixWidth(HBox box)
	{
		double spacing = ((box.getChildren().size() * Costants.CARD_WIDTH_SCALED.getVal()) - (box.getMaxWidth())) / box.getChildren().size();


		if (spacing < 0) spacing = 30;

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

	public void setPathDraw(BuildMP.PG player, Card card, ImageView newCard)
	{

		double endX = newCard.getLayoutX() + Costants.CARD_WIDTH_SCALED.getVal() / 2.0;
		double endY = newCard.getLayoutY() + Costants.CARD_HEIGHT_SCALED.getVal() / 2.0;
		HBox hand = null;

		switch (player)
		{

			case PLAYER ->
			{
				endX += Costants.PARENT_PLAYER_CENTER_X.getVal();
				endY += Costants.PARENT_PLAYER_CENTER_Y.getVal();
				hand = userHand;
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

		cardDrawn.setImage(new Image(player == BuildMP.PG.PLAYER ? card.getUrl().getPath() : String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));


		((Line) pathDraw.getPath()).setEndX(endX);
		((Line) pathDraw.getPath()).setEndY(endY);

		HBox finalHand = hand;

		pathDraw.setOnFinished(x ->
		{
			newCard.setVisible(true);
			cardDrawn.setVisible(false);

			finalHand.getChildren().add(newCard);
			fixWidth(finalHand);
		});
	}
	public void setRotationDraw(BuildMP.PG player)
	{

		cardDrawn.setRotate(0);

		switch (player)
		{
			case PLAYER ->
			{
				cardDrawn.setRotate(180);
				drawRotate.setByAngle(180);
			}
			case BOT1 -> drawRotate.setByAngle(90);
			case BOT2 -> drawRotate.setByAngle(180);
			case BOT3 -> drawRotate.setByAngle(270);
		}
	}

	public void setPathDiscard(BuildMP.PG player, Card card)
	{
		double startX = Costants.CARD_WIDTH_SCALED.getVal() / 2.0;
		double startY = Costants.CARD_HEIGHT_SCALED.getVal() / 2.0;
		ImageView animation = null;

		switch (player)
		{
			case PLAYER ->
			{
				startX += lastClicked.getLayoutX() + Costants.PARENT_PLAYER_X.getVal();
				startY += lastClicked.getLayoutY() + Costants.PARENT_PLAYER_Y.getVal();
				playerAnimation.setImage(new Image(card.getFinalUrl().getPath()));
				animation = playerAnimation;
			}
			case BOT1 ->
			{
				startX += botHand1.getChildren().get(0).getLayoutX() + Costants.PARENT_BOT1_X.getVal();
				startY += botHand1.getChildren().get(0).getLayoutY() + Costants.PARENT_BOT1_Y.getVal();
				bot1Animation.setImage(new Image(card.getFinalUrl().getPath()));
				animation = bot1Animation;
			}
			case BOT2 ->
			{
				startX += botHand2.getChildren().get(0).getLayoutX() + Costants.PARENT_BOT2_X.getVal();
				startY += botHand2.getChildren().get(0).getLayoutY() + Costants.PARENT_BOT2_Y.getVal();
				bot2Animation.setImage(new Image(card.getFinalUrl().getPath()));
				animation = bot2Animation;
			}
			case BOT3 ->
			{
				startX += botHand3.getChildren().get(0).getLayoutX() + Costants.PARENT_BOT3_X.getVal() + 189 * 2;
				startY += botHand3.getChildren().get(0).getLayoutY() + Costants.PARENT_BOT3_Y.getVal();
				bot3Animation.setImage(new Image(card.getFinalUrl().getPath()));
				animation = bot3Animation;
			}
		}

		ImageView finalAnimation = animation;
		((Line) pathDisc.getPath()).setStartX(startX);
		((Line) pathDisc.getPath()).setStartY(startY);
		pathDisc.setNode(finalAnimation);

		finalAnimation.setVisible(true);
		pathDisc.setOnFinished(x ->
		{
			finalAnimation.setVisible(false);
			beep();
			thirdDiscarded.setImage(secondDiscarded.getImage());
			secondDiscarded.setImage(firstDiscarded.getImage());
			firstDiscarded.setImage(finalAnimation.getImage());
		});
	}
	public void setRotationDiscard(BuildMP.PG player)
	{

		switch (player)
		{

			case PLAYER ->
			{
				playerAnimation.setRotate(0);
				discardRotate.setNode(playerAnimation);
				discardRotate.setByAngle(360);
			}
			case BOT1 ->
			{
				bot1Animation.setRotate(90);
				discardRotate.setNode(bot1Animation);
				discardRotate.setByAngle(270);
			}
			case BOT2 ->
			{
				bot2Animation.setRotate(180);
				discardRotate.setNode(bot2Animation);
				discardRotate.setByAngle(180);
			}
			case BOT3 ->
			{
				bot3Animation.setRotate(270);
				discardRotate.setNode(bot3Animation);
				discardRotate.setByAngle(90);
			}
		}
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		pathDraw.setNode(cardDrawn);
		drawRotate.setNode(cardDrawn);
	}
}
