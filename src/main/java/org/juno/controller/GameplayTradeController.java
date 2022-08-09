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
import org.juno.model.table.trade.PlayerTrade;
import org.juno.model.table.trade.TableTrade;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

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

    public ImageView lastClicked;


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
        lastClicked = (ImageView) mouseEvent.getSource();

        Card.Value value = ((Card) lastClicked.getUserData()).getValue();
        Card.Color color = ((Card) lastClicked.getUserData()).getColor();

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
    @Override
    public void discard(DiscardData discardData)
    {
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
        System.out.println("HAND TO SWITCH " + switchData.newHand());
        System.out.println("FROM " + switchData.fromPg());
        System.out.println("TO " + switchData.toPg());

        int userSize = userHand.getChildren().size();
        userHand.getChildren().clear();

        for (Card card : switchData.newHand())
        {
            draw(new DrawData(BuildMP.PG.PLAYER, card));
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
                draw(new DrawData(BuildMP.PG.BOT1, null));
            }
            for (int i = 0; i < bot1Size; i++)
            {
                draw(new DrawData(BuildMP.PG.BOT2, null));
            }
            for (int i = 0; i < bot2Size; i++)
            {
                draw(new DrawData(BuildMP.PG.BOT3, null));
            }
        } else
        {
            BuildMP.PG pg = switchData.fromPg() != BuildMP.PG.PLAYER ? switchData.fromPg() : switchData.toPg();

            switch (pg)
            {
                case BOT1 -> bot1Hand.getChildren().clear();
                case BOT2 -> bot2Hand.getChildren().clear();
                case BOT3 -> bot3Hand.getChildren().clear();
            }
            for (int i = 0; i < userSize; i++)
            {
                draw(new DrawData(pg, null));
            }
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
        ((EndMatchTradeController) GEN_VIEW.getEndMatchTrade().getUserData()).load(pointsData);
    }


    private void nextMatch()
    {
        GEN_VIEW.changeScene(GenView.SCENES.ENDMATCHTRADE, anchor);
    }
    private void goEndgame()
    {
        GEN_VIEW.changeScene(GenView.SCENES.ENDGAME, anchor);

        ((EndgameController) GEN_VIEW.getEndgame().getUserData()).load(TABLE_TRADE);
    }
    public void reset()
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