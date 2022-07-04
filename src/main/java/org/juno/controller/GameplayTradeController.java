package org.juno.controller;

import javafx.event.ActionEvent;
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
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.WildCard;
import org.juno.model.table.Player;
import org.juno.model.table.trade.PlayerTrade;
import org.juno.model.table.trade.TableTrade;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Defines GameplayTradeController ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class GameplayTradeController implements Gameplay
{
    private static final GenView GEN_VIEW = GenView.getINSTANCE();
    private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
    private static final TableTrade TABLE_TRADE = TableTrade.getINSTANCE();

    protected static final int CARD_WIDTH_SCALED = 189;
    protected static final int CARD_HEIGHT_SCALED = 264;


    @FXML
    public AnchorPane anchor;


    @FXML
    public Circle userCircle;
    @FXML
    public Circle bot1Circle;
    @FXML
    public Circle bot2Circle;
    @FXML
    public Circle bot3Circle;


    @FXML
    public ImageView firstDiscarded;
    @FXML
    public ImageView secondDiscarded;
    @FXML
    public ImageView thirdDiscarded;


    @FXML
    public HBox userHand;
    @FXML
    public HBox bot1Hand;
    @FXML
    public HBox bot2Hand;
    @FXML
    public HBox bot3Hand;


    @FXML
    public GridPane colorGridPane;


    @FXML
    public Button passButton;
    @FXML
    public Button junoButton;


    @FXML
    public void deckClicked()
    {
        Player player = TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.draw();
        passButton.setDisable(false);
    }
    @FXML
    public void passClicked()
    {
        passButton.setDisable(true);

        beep();
        TABLE_TRADE.getUser().setHasPassed();
    }
    @FXML
    public void junoClicked()
    {
        TABLE_TRADE.getUser().sayUno();
        junoButton.setVisible(false);
    }


    @FXML
    public void redClicked()
    {
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.RED);
        colorGridPane.setVisible(false);
    }
    @FXML
    public void blueClicked()
    {
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.BLUE);
        colorGridPane.setVisible(false);
    }
    @FXML
    public void yellowClicked()
    {
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.YELLOW);
        colorGridPane.setVisible(false);
    }
    @FXML
    public void greenClicked()
    {
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.GREEN);
        colorGridPane.setVisible(false);
    }


    @FXML
    public void bot1HandClicked()
    {
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT1);
    }
    @FXML
    public void bot2HandClicked()
    {
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT2);
    }
    @FXML
    public void bot3HandClicked()
    {
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT3);
    }


    public void cardEntered(MouseEvent mouseEvent)
    {
        if (TABLE_TRADE.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

        cardFlip();
        ImageView card = (ImageView) mouseEvent.getSource();
        card.setTranslateY(-30);
    }
    public void cardExited(MouseEvent mouseEvent)
    {
        if (TABLE_TRADE.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

        ImageView card = (ImageView) mouseEvent.getSource();
        card.setTranslateY(0);
    }
    public void cardClicked(MouseEvent mouseEvent)
    {
        if (TABLE_TRADE.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;
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
        TABLE_TRADE.getUser().chooseCard(color, value);
        if (color == Card.Color.BLACK && TABLE_TRADE.getCurrentPlayer().getId() == BuildMP.PG.PLAYER)
            colorGridPane.setVisible(true);

    }


    @Override
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
    @Override
    public void discard(DiscardData discardData)
    {
        beep();
        if (discardData.player() == null)
        {
            thirdDiscarded.setImage(secondDiscarded.getImage());
            secondDiscarded.setImage(firstDiscarded.getImage());
            firstDiscarded.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), discardData.color().toString(), discardData.value().getVal())));
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
        firstDiscarded.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), discardData.color().toString(), discardData.value().getVal())));
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
        passButton.setDisable(true);
        userCircle.setFill(Color.WHITE);
        bot1Circle.setFill(Color.WHITE);
        bot2Circle.setFill(Color.WHITE);
        bot3Circle.setFill(Color.WHITE);
        switch (turnData.player())
        {
            case PLAYER -> userCircle.setFill(Color.YELLOW);
            case BOT1 -> bot1Circle.setFill(Color.YELLOW);
            case BOT2 -> bot2Circle.setFill(Color.YELLOW);
            case BOT3 -> bot3Circle.setFill(Color.YELLOW);
        }

    }
    @Override
    public void effect(EffectData effectData)
    {
        switch (effectData.effect())
        {
            case ONECARD -> junoButton.setVisible(true);
            case ENDMATCH -> nextMatch();
            case STARTGAME -> reset();
            case ENDGAME -> goEndgame();
            case DIDNTSAYUNO -> junoButton.setVisible(false);
        }
    }
    @Override
    public void doSwitch(SwitchData switchData)
    {
        System.out.println("HAND TO SWITCH " + switchData.newHand());
        System.out.println("FROM " + switchData.fromPg());
        System.out.println("TO " + switchData.toPg());

        int userSize = userHand.getChildren().size();
        userHand.getChildren().clear();

        for (Card card : switchData.newHand())
        {
            draw(new DrawData(BuildMP.PG.PLAYER, card.getColor(), card.getValue()));
        }


        if (switchData.fromPg() == null)
        {
            int bot1Size = bot1Hand.getChildren().size();
            int bot2Size = bot2Hand.getChildren().size();

            bot1Hand.getChildren().clear();
            bot2Hand.getChildren().clear();
            bot3Hand.getChildren().clear();


            for (int i = 0; i < userSize; i++)
            {
                draw(new DrawData(BuildMP.PG.BOT1, null, null));
            }
            for (int i = 0; i < bot1Size; i++)
            {
                draw(new DrawData(BuildMP.PG.BOT2, null, null));
            }
            for (int i = 0; i < bot2Size; i++)
            {
                draw(new DrawData(BuildMP.PG.BOT3, null, null));
            }
        } else
        {
            if (switchData.fromPg() != BuildMP.PG.PLAYER)
            {
                switch (switchData.fromPg())
                {
                    case BOT1 -> bot1Hand.getChildren().clear();
                    case BOT2 -> bot2Hand.getChildren().clear();
                    case BOT3 -> bot3Hand.getChildren().clear();
                }
                for (int i = 0; i < userSize; i++)
                {
                    draw(new DrawData(switchData.fromPg(), null, null));
                }
            } else
            {
                switch (switchData.toPg())
                {
                    case BOT1 -> bot1Hand.getChildren().clear();
                    case BOT2 -> bot2Hand.getChildren().clear();
                    case BOT3 -> bot3Hand.getChildren().clear();
                }
                for (int i = 0; i < userSize; i++)
                {
                    draw(new DrawData(switchData.toPg(), null, null));
                }
            }
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
        for (Player player : TABLE_TRADE.getPlayers())
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
            TABLE_TRADE.stopEarlier();
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
        GEN_VIEW.changeScene(GenView.SCENES.ENDGAME, anchor);

        ((EndgameController) GEN_VIEW.getEndgame().getUserData()).load(TABLE_TRADE);
    }
    private void reset()
    {
        userHand.getChildren().clear();
        bot1Hand.getChildren().clear();
        bot2Hand.getChildren().clear();
        bot3Hand.getChildren().clear();
        TABLE_TRADE.canStart();
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