package org.juno.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.juno.datapackage.*;
import org.juno.model.deck.Card;
import org.juno.model.deck.WildCard;
import org.juno.model.table.combo.TableCombo;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

/**
 * Defines GameplayComboController ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class GameplayComboController implements Gameplay
{

	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final TableCombo TABLE_COMBO = TableCombo.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
	private static final int CARD_WIDTH_SCALED = 189;
	private static final int CARD_HEIGHT_SCALED = 264;


	@FXML
	public AnchorPane anchor;


	@FXML
	public ImageView userTurn;
	@FXML
	public ImageView bot1Turn;
	@FXML
	public ImageView bot2Turn;
	@FXML
	public ImageView bot3Turn;


	@FXML
	public HBox userHand;
	@FXML
	public HBox bot2Hand;
	@FXML
	public HBox bot1Hand;
	@FXML
	public HBox bot3Hand;


	@FXML
	public Circle bot2Circle;
	@FXML
	public Circle bot1Circle;
	@FXML
	public Circle bot3Circle;
	@FXML
	public Circle userCircle;


	@FXML
	public ImageView firstDiscarded;
	@FXML
	public ImageView secondDiscarded;
	@FXML
	public ImageView thirdDiscarded;
	@FXML
	public ImageView cardDrawn;


	@FXML
	public Button passButton;
	@FXML
	public Button junoButton;


	@FXML
	public GridPane colorGridPane;


	private ImageView lastClicked;

	@FXML
	public void deckClicked()
	{
		if (TABLE_COMBO.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;


		TABLE_COMBO.getUser().draw();
	}
	@FXML
	public void passClicked()
	{
		beep();
		passButton.setDisable(true);
		TABLE_COMBO.getUser().setHasPassed();
	}
	@FXML
	public void junoClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		junoButton.setVisible(false);
		TABLE_COMBO.getUser().sayUno();
	}


	@FXML
	public void redClicked()
	{
		beep();
		((WildCard) TABLE_COMBO.getUser().getChosenCard()).setColor(Card.Color.RED);
		colorGridPane.setVisible(false);
	}
	@FXML
	public void blueClicked()
	{
		beep();
		((WildCard) TABLE_COMBO.getUser().getChosenCard()).setColor(Card.Color.BLUE);
		colorGridPane.setVisible(false);
	}
	@FXML
	public void yellowClicked()
	{
		beep();
		((WildCard) TABLE_COMBO.getUser().getChosenCard()).setColor(Card.Color.YELLOW);
		colorGridPane.setVisible(false);
	}
	@FXML
	public void greenClicked()
	{
		beep();
		((WildCard) TABLE_COMBO.getUser().getChosenCard()).setColor(Card.Color.GREEN);
		colorGridPane.setVisible(false);
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
		TABLE_COMBO.stopEarlier();
	}

	@FXML
	public void buttonEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}


	public void draw(DrawData drawData)
	{
		cardFlip();


		switch (drawData.player())
		{
			case PLAYER ->
			{
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
			case BOT1 -> drawBot(bot1Hand);
			case BOT2 -> drawBot(bot2Hand);
			case BOT3 -> drawBot(bot3Hand);
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
	public void discard(DiscardData discardData)
	{
		colorGridPane.setVisible(false);
		beep();
		if (discardData.player() == null)
		{
			thirdDiscarded.setImage(secondDiscarded.getImage());
			secondDiscarded.setImage(firstDiscarded.getImage());
			firstDiscarded.setImage(new Image(discardData.card().getUrl().getPath()));
			return;
		}
		switch (discardData.player())
		{
			case PLAYER ->
			{
				userHand.getChildren().remove(lastClicked);
				fixWidth(userHand);
			}
			case BOT1 ->
			{
				bot1Hand.getChildren().remove(0);
				fixWidth(bot1Hand);
			}
			case BOT2 ->
			{
				bot2Hand.getChildren().remove(0);
				fixWidth(bot2Hand);
			}
			case BOT3 ->
			{
				bot3Hand.getChildren().remove(0);
				fixWidth(bot3Hand);
			}
		}
		thirdDiscarded.setImage(secondDiscarded.getImage());
		secondDiscarded.setImage(firstDiscarded.getImage());
		firstDiscarded.setImage(new Image(discardData.card().getFinalUrl().getPath()));
	}
	private void fixWidth(HBox box)
	{
		double spacing = ((box.getChildren().size() * CARD_WIDTH_SCALED) - (box.getMaxWidth())) / box.getChildren().size();

		if (spacing < 0) spacing = 0;

		box.setSpacing(-spacing);
	}


	private void cardEntered(MouseEvent mouseEvent)
	{
		if (TABLE_COMBO.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

		cardFlip();
		ImageView card = (ImageView) mouseEvent.getSource();
		card.setTranslateY(-30);
	}
	private void cardExited(MouseEvent mouseEvent)
	{
		if (TABLE_COMBO.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

		ImageView card = (ImageView) mouseEvent.getSource();
		card.setTranslateY(0);
	}
	private void cardClicked(MouseEvent mouseEvent)
	{
		passButton.setDisable(false);
		if (TABLE_COMBO.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;
		lastClicked = (ImageView) mouseEvent.getSource();

		Card.Value value = ((Card) lastClicked.getUserData()).getValue();
		Card.Color color = ((Card) lastClicked.getUserData()).getColor();

		TABLE_COMBO.getUser().chooseCard(color, value);
		if (color == Card.Color.BLACK && TABLE_COMBO.getCurrentPlayer().getId() == BuildMP.PG.PLAYER)
			colorGridPane.setVisible(true);
	}


	@Override
	public void turn(TurnData turnData)
	{
		passButton.setDisable(true);
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
			case DIDNTSAYUNO -> junoButton.setVisible(false);
			case JUMPIN ->
			{/*todo*/}
			case ONECARD -> junoButton.setVisible(true);
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
		((EndMatchComboController) GEN_VIEW.getEndMatchCombo().getUserData()).load(pointsData);
	}

	private void nextMatch()
	{
		GEN_VIEW.changeScene(GenView.SCENES.ENDMATCHCOMBO, anchor);
	}

	private void goEndgame()
	{
		GEN_VIEW.changeScene(GenView.SCENES.ENDGAME, anchor);

		((EndgameController) GEN_VIEW.getEndgame().getUserData()).load(TABLE_COMBO);
	}
	public void reset()
	{
		passButton.setDisable(true);
		junoButton.setVisible(false);
		userHand.getChildren().clear();
		bot1Hand.getChildren().clear();
		bot2Hand.getChildren().clear();
		bot3Hand.getChildren().clear();
		userCircle.setFill(new ImagePattern(new Image(User.getInstance().getAvatar())));
		bot1Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot1.png", System.getProperty("user.dir")))));
		bot2Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot2.png", System.getProperty("user.dir")))));
		bot3Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot3.png", System.getProperty("user.dir")))));
		TABLE_COMBO.canStart();
	}


	protected void beep()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
	}
	protected void cardFlip()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CARDFLIP);
	}
}
