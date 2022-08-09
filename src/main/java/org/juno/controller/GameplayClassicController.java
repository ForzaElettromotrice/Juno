package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.juno.datapackage.*;
import org.juno.model.deck.Card;
import org.juno.model.deck.WildCard;
import org.juno.model.table.Player;
import org.juno.model.table.classic.TableClassic;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

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

	protected static final int CARD_WIDTH_SCALED = 189;
	protected static final int CARD_HEIGHT_SCALED = 264;


	@FXML
	public AnchorPane anchorPane;
	@FXML
	public GridPane colorGrid;


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
	public Circle circle1;
	@FXML
	public Circle circle2;
	@FXML
	public Circle circle3;
	@FXML
	public Circle circle4;


	@FXML
	public ImageView firstDiscarded;
	@FXML
	public ImageView secondDiscarded;
	@FXML
	public ImageView thirdDiscarded;

	public ImageView lastClicked;


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
		TABLE_CLASSIC.getUser().sayUno();
		juno.setVisible(false);
	}

	@Override
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
			case BOT1 -> drawBot(botHand1);
			case BOT2 -> drawBot(botHand2);
			case BOT3 -> drawBot(botHand3);
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
		beep();
		if (discardData.player() == null)
		{
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


	@Override
	public void turn(TurnData turnData)
	{
		pass.setDisable(true);
		circle1.setFill(Color.WHITE);
		circle2.setFill(Color.WHITE);
		circle3.setFill(Color.WHITE);
		circle4.setFill(Color.WHITE);
		switch (turnData.player())
		{
			case PLAYER -> circle1.setFill(Color.YELLOW);
			case BOT1 -> circle2.setFill(Color.YELLOW);
			case BOT2 -> circle3.setFill(Color.YELLOW);
			case BOT3 -> circle4.setFill(Color.YELLOW);
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
		userHand.getChildren().clear();
		botHand1.getChildren().clear();
		botHand2.getChildren().clear();
		botHand3.getChildren().clear();
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

}
