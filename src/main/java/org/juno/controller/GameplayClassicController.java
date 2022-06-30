package org.juno.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.juno.datapackage.*;
import org.juno.model.deck.Card;
import org.juno.model.deck.WildCard;
import org.juno.model.table.Player;
import org.juno.model.table.classic.TableClassic;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

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
	public ImageView cardDiscarded;


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
		ImageView imageView = (ImageView) mouseEvent.getSource();

		String name = "";

		try
		{
			name = new File(new URI(imageView.getImage().getUrl())).getName();
		} catch (URISyntaxException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}

		Card.Color color = switch (name.charAt(0))
				{
					case 'r' -> Card.Color.RED;
					case 'b' -> Card.Color.BLUE;
					case 'g' -> Card.Color.GREEN;
					case 'y' -> Card.Color.YELLOW;
					default -> Card.Color.BLACK;
				};

		Card.Value value = color == Card.Color.BLACK ? switch (name.substring(0, 2))
				{
					case "13" -> Card.Value.JOLLY;
					default -> Card.Value.PLUSFOUR;
				} :
				switch (name.substring(1, 3))
						{
							case "0." -> Card.Value.ZERO;
							case "1." -> Card.Value.ONE;
							case "2." -> Card.Value.TWO;
							case "3." -> Card.Value.THREE;
							case "4." -> Card.Value.FOUR;
							case "5." -> Card.Value.FIVE;
							case "6." -> Card.Value.SIX;
							case "7." -> Card.Value.SEVEN;
							case "8." -> Card.Value.EIGHT;
							case "9." -> Card.Value.NINE;
							case "10" -> Card.Value.PLUSTWO;
							case "11" -> Card.Value.REVERSE;
							default -> Card.Value.STOP;
						};

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


	public void draw(DrawData drawData)
	{
		cardFlip();
		switch (drawData.player())
		{
			case PLAYER ->
			{
				ImageView imageView = new ImageView(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), drawData.color().toString(), drawData.value().getVal())));

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
	public void discard(DiscardData discardData)
	{
		beep();
		if (discardData.player() == null)
		{
			cardDiscarded.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), discardData.color().toString(), discardData.value().getVal())));
			return;
		}
		switch (discardData.player())
		{
			case PLAYER ->
			{
				String name = String.format("%s%d.png", (discardData.value().getVal() == 13 || discardData.value().getVal() == 14) ? "" : discardData.color().toString(), discardData.value().getVal());

				for (Node child : userHand.getChildren())
				{
					if (((ImageView) child).getImage().getUrl().endsWith(name))
					{
						userHand.getChildren().remove(child);
						break;
					}
				}
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
		cardDiscarded.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), discardData.color().toString(), discardData.value().getVal())));
	}
	private void fixWidth(HBox box)
	{
		double spacing = ((box.getChildren().size() * CARD_WIDTH_SCALED) - (box.getMaxWidth())) / box.getChildren().size();

		if (spacing < 0) spacing = 0;

		box.setSpacing(-spacing);
	}


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
	public void effect(EffectData effectData)
	{
		switch (effectData.effect())
		{
			case ONECARD -> juno.setVisible(true);
			case ENDMATCH -> nextMatch();
			case STARTGAME -> reset();
			case ENDGAME -> goEndgame();
			case DIDNTSAYUNO -> juno.setVisible(false);
		}
	}


	private void nextMatch()
	{
		Stage win = new Stage();
		win.initModality(Modality.APPLICATION_MODAL);
		win.setTitle("End-match points");
		win.setMinHeight(500);
		win.setMinWidth(500);
		win.setResizable(false);

		win.setOnCloseRequest(x ->
		{
			win.close();
			reset();
		});

		Label label;

		VBox layout = new VBox();
		for (Player player : TABLE_CLASSIC.getPlayers())
		{
			label = new Label(String.format("\t%s:\t\t\t%d/500", player.getId(), player.getPoints()));
			label.setPrefSize(500, 100);
			label.setStyle("-fx-border-color: BLACK");
			label.setFont(new Font(30));
			layout.getChildren().add(label);
		}

		Button next = new Button("Next match");
		Button exit = new Button("Exit game");


		next.setMinSize(250, 100);
		next.setFont(new Font(25));
		next.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 3");

		next.setOnAction(x ->
		{
			buttonClick();
			reset();
			win.close();
		});


		exit.setMinSize(250, 100);
		exit.setFont(new Font(25));
		exit.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 3");

		exit.setOnAction(x ->
		{
			buttonClick();
			TABLE_CLASSIC.stopEarlier();
			win.close();
		});


		HBox buttons = new HBox(exit, next);
		buttons.setAlignment(Pos.CENTER);
		layout.getChildren().add(buttons);

		Scene scene = new Scene(layout);

		win.setScene(scene);
		win.showAndWait();
	}
	private void goEndgame()
	{
		GEN_VIEW.changeScene(GenView.SCENES.ENDGAME, anchorPane);

		((EndgameController) GEN_VIEW.getEndgame().getUserData()).load(TABLE_CLASSIC);
	}
	private void reset()
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
	protected void buttonClick()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
	}

}
