package org.juno.controller;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
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
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.juno.datapackage.*;
import org.juno.model.deck.Card;
import org.juno.model.deck.WildCard;
import org.juno.model.table.Player;
import org.juno.model.table.trade.PlayerTrade;
import org.juno.model.table.trade.TableTrade;
import org.juno.model.user.User;
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

    protected static final double PARENT_PLAYER_X = 298.5;
    protected static final double PARENT_BOT1_X = -288;
    protected static final double PARENT_BOT2_X = 584;
    protected static final double PARENT_BOT3_X = 1406;

    protected static final double PARENT_PLAYER_Y = 816;
    protected static final double PARENT_BOT1_Y = 397;
    protected static final double PARENT_BOT2_Y = 26;
    protected static final double PARENT_BOT3_Y = 383;

    protected double playerX;
    protected double bot1XDiscard;
    protected double bot2XDiscard;
    protected double bot3XDiscard;
    protected double bot1XDraw;
    protected double bot2XDraw;
    protected double bot3XDraw;

    protected double playerY;
    protected double bot1YDiscard;
    protected double bot2YDiscard;
    protected double bot3YDiscard;
    protected double bot1YDraw;
    protected double bot2YDraw;
    protected double bot3YDraw;

    private final PathTransition pathDisc = new PathTransition(Duration.seconds(1), new Line(0, 0, 1147.5, 540));
    private final PathTransition pathDraw = new PathTransition(Duration.millis(500), new Line(858.5, 540, 0, 0));
    private final RotateTransition rotate = new RotateTransition(Duration.seconds(1));

    private final RotateTransition drawRotate = new RotateTransition(Duration.millis(500));


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

    public ImageView lastClicked;


    @FXML
    public HBox userHand;
    @FXML
    public HBox botHand1;
    @FXML
    public HBox botHand2;
    @FXML
    public HBox botHand3;


    @FXML
    public GridPane colorGrid;

    @FXML
    public ImageView cardDrawn;
    @FXML
    public ImageView bot1Animation;
    @FXML
    public ImageView bot2Animation;
    @FXML
    public ImageView bot3Animation;
    @FXML
    public ImageView playerAnimation;


    @FXML
    public Button pass;
    @FXML
    public Button juno;


    @FXML
    public void drawClicked()
    {
        Player player = TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.draw();
        pass.setDisable(false);
    }
    @FXML
    public void passClicked()
    {
        pass.setDisable(true);

        beep();
        TABLE_TRADE.getUser().setHasPassed();
    }
    @FXML
    public void sayUno()
    {
        buttonClick();
        TABLE_TRADE.getUser().sayUno();
        juno.setVisible(false);
    }


    @FXML
    public void redClicked()
    {
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.RED);
        colorGrid.setVisible(false);
    }
    @FXML
    public void blueClicked()
    {
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.BLUE);
        colorGrid.setVisible(false);
    }
    @FXML
    public void yellowClicked()
    {
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.YELLOW);
        colorGrid.setVisible(false);
    }
    @FXML
    public void greenClicked()
    {
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.GREEN);
        colorGrid.setVisible(false);
    }


    @FXML
    public void botHand1Clicked()
    {
        beep();
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT1);
    }
    @FXML
    public void bot2HandClicked()
    {
        beep();
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT2);
    }
    @FXML
    public void bot3HandClicked()
    {
        beep();
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT3);
    }

    @FXML
    public void exitClicked()
    {
        buttonClick();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? You will lose the match by default!", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm");
        alert.setHeaderText("Confirm");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) return;
        TABLE_TRADE.stopEarlier();
    }

    @FXML
    public void buttonEntered()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
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
            colorGrid.setVisible(true);

    }


    @Override
    public void draw(DrawData drawData)
    {
        setTransition(drawData.player(), drawData.card());
        cardFlip();

        switch (drawData.player())
        {
            case PLAYER ->
            {
                cardDrawn.setImage(new Image(drawData.card().getUrl().getPath()));
                cardDrawn.setVisible(true);

                if (userHand.getChildren().isEmpty())
                {
                    playerX = PARENT_PLAYER_X * 2;
                    playerY = PARENT_PLAYER_Y;
                } else
                {
                    playerX = userHand.getLayoutX() + PARENT_PLAYER_X + CARD_WIDTH_SCALED / 2;
                    playerY = userHand.getChildren().get(0).getLayoutY() + PARENT_PLAYER_Y + CARD_HEIGHT_SCALED / 2;
                }
                ((Line) pathDraw.getPath()).setEndX(playerX);
                ((Line) pathDraw.getPath()).setEndY(playerY);
                pathDraw.play();
                drawRotate.play();
            }
            case BOT1 ->
            {
                cardDrawn.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));
                cardDrawn.setVisible(true);

                if (botHand1.getChildren().isEmpty())
                {
                    bot1XDraw = PARENT_BOT1_X * 2;
                    bot1YDraw = PARENT_BOT1_Y;
                } else
                {
                    bot1XDraw = userHand.getLayoutX() + PARENT_BOT1_X + CARD_WIDTH_SCALED / 2;
                    bot1YDraw = userHand.getChildren().get(0).getLayoutY() + PARENT_BOT1_Y + CARD_HEIGHT_SCALED / 2;
                }
                ((Line) pathDraw.getPath()).setEndX(bot1XDraw);
                ((Line) pathDraw.getPath()).setEndY(bot1YDraw);
                pathDraw.play();
                drawRotate.play();
            }
            case BOT2 ->
            {
                cardDrawn.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));
                cardDrawn.setVisible(true);
                if (botHand2.getChildren().isEmpty())
                {
                    bot2XDraw = PARENT_BOT2_X * 2;
                    bot2YDraw = PARENT_BOT2_Y;
                } else
                {
                    bot2XDraw = userHand.getLayoutX() + PARENT_BOT2_X + CARD_WIDTH_SCALED / 2;
                    bot2YDraw = userHand.getChildren().get(0).getLayoutY() + PARENT_BOT2_Y + CARD_HEIGHT_SCALED / 2;
                }
                ((Line) pathDraw.getPath()).setEndX(bot2XDraw);
                ((Line) pathDraw.getPath()).setEndY(bot2YDraw);
                pathDraw.play();
                drawRotate.play();
            }
            case BOT3 ->
            {
                cardDrawn.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));
                cardDrawn.setVisible(true);
                if (botHand3.getChildren().isEmpty())
                {
                    bot3XDraw = PARENT_BOT3_X;
                    bot3YDraw = PARENT_BOT3_Y;
                } else
                {
                    bot3XDraw = userHand.getLayoutX() + PARENT_BOT3_X + CARD_WIDTH_SCALED / 2;
                    bot3YDraw = userHand.getChildren().get(0).getLayoutY() + PARENT_BOT3_Y + CARD_HEIGHT_SCALED / 2;
                }
                ((Line) pathDraw.getPath()).setEndX(bot3XDraw);
                ((Line) pathDraw.getPath()).setEndY(bot3YDraw);
                pathDraw.play();
                drawRotate.play();
            }
        }
    }
    @Override
    public void discard(DiscardData discardData)
    {
        colorGrid.setVisible(false);
        if (discardData.player() == null)
        {
            firstDiscarded.setImage(new Image(discardData.card().getUrl().getPath()));
            return;
        }
        preAnimation(discardData.player(), discardData.card());

        pathDisc.setOnFinished(x -> postAnimation(discardData.card()));


        animation();
    }

    protected void preAnimation(BuildMP.PG player, Card card)
    {
        setTransition(player, null);
        switch (player)
        {
            case PLAYER ->
            {
                userHand.getChildren().remove(lastClicked);
                fixWidth(userHand);
                playerAnimation.setVisible(true);
                playerAnimation.setImage(new Image(card.getUrl().getPath()));
                ((Line) pathDisc.getPath()).setStartX(lastClicked.getLayoutX() + PARENT_PLAYER_X + CARD_WIDTH_SCALED / 2);
                ((Line) pathDisc.getPath()).setStartY(lastClicked.getLayoutY() + PARENT_PLAYER_Y + CARD_HEIGHT_SCALED / 2);
            }
            case BOT1 ->
            {
                if (bot1XDiscard == 0 && bot1YDiscard == 0)
                {
                    bot1XDiscard = botHand1.getChildren().get(0).getLayoutX() + PARENT_BOT1_X + CARD_WIDTH_SCALED / 2;
                    bot1YDiscard = botHand1.getChildren().get(0).getLayoutY() + PARENT_BOT1_Y + CARD_HEIGHT_SCALED / 2;
                }
                botHand1.getChildren().remove(0);
                fixWidth(botHand1);
                bot1Animation.setVisible(true);
                bot1Animation.setImage(new Image(card.getUrl().getPath()));
                ((Line) pathDisc.getPath()).setStartX(bot1XDiscard);
                ((Line) pathDisc.getPath()).setStartY(bot1YDiscard);
            }
            case BOT2 ->
            {
                if (bot2XDiscard == 0 && bot2YDiscard == 0)
                {
                    bot2XDiscard = botHand2.getChildren().get(0).getLayoutX() + PARENT_BOT2_X + CARD_WIDTH_SCALED / 2;
                    bot2YDiscard = botHand2.getChildren().get(0).getLayoutY() + PARENT_BOT2_Y + CARD_HEIGHT_SCALED / 2;
                }
                botHand2.getChildren().remove(0);
                fixWidth(botHand2);
                bot2Animation.setVisible(true);
                bot2Animation.setImage(new Image(card.getUrl().getPath()));
                ((Line) pathDisc.getPath()).setStartX(bot2XDiscard);
                ((Line) pathDisc.getPath()).setStartY(bot2YDiscard);
            }
            case BOT3 ->
            {
                if (bot3XDiscard == 0 && bot3YDiscard == 0)
                {
                    bot3XDiscard = botHand3.getChildren().get(0).getLayoutX() + PARENT_BOT3_X + CARD_WIDTH_SCALED / 2;
                    bot3YDiscard = botHand3.getChildren().get(0).getLayoutY() + PARENT_BOT3_Y + CARD_HEIGHT_SCALED / 2;
                }
                botHand3.getChildren().remove(0);
                fixWidth(botHand3);
                bot3Animation.setVisible(true);
                bot3Animation.setImage(new Image(card.getUrl().getPath()));
                ((Line) pathDisc.getPath()).setStartX(bot3XDiscard);
                ((Line) pathDisc.getPath()).setStartY(bot3YDiscard);
            }
        }
    }
    public void animation()
    {
        pathDisc.play();
        rotate.play();
    }
    public void postAnimation(Card card)
    {
        beep();

        playerAnimation.setVisible(false);
        bot1Animation.setVisible(false);
        bot3Animation.setVisible(false);
        bot2Animation.setVisible(false);


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
            int bot1Size = botHand1.getChildren().size();
            int bot2Size = botHand2.getChildren().size();

            botHand1.getChildren().clear();
            botHand2.getChildren().clear();
            botHand3.getChildren().clear();


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
                case BOT1 -> botHand1.getChildren().clear();
                case BOT2 -> botHand2.getChildren().clear();
                case BOT3 -> botHand3.getChildren().clear();
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
        botHand1.getChildren().clear();
        botHand2.getChildren().clear();
        botHand3.getChildren().clear();
        userCircle.setFill(new ImagePattern(new Image(User.getInstance().getAvatar())));
        bot1Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot1.png", System.getProperty("user.dir")))));
        bot2Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot2.png", System.getProperty("user.dir")))));
        bot3Circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot3.png", System.getProperty("user.dir")))));
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
    public void setTransition(BuildMP.PG player, Card card)
    {
        switch (player)
        {
            case PLAYER ->
            {
                pathDisc.setNode(playerAnimation);
                rotate.setNode(playerAnimation);
                rotate.setByAngle(0);
                rotate.setOnFinished(x ->
                {
                    playerAnimation.setVisible(false);
                    playerAnimation.setRotate(0);
                });

                pathDraw.setNode(cardDrawn);
                pathDraw.setOnFinished(x -> cardDrawn.setVisible(false));

                drawRotate.setNode(cardDrawn);
                cardDrawn.setRotate(180);
                drawRotate.setByAngle(180);
                drawRotate.setOnFinished(x ->
                {
                    ImageView imageView = new ImageView(new Image(card.getUrl().getPath()));

                    imageView.setUserData(card);

                    imageView.setFitWidth(CARD_WIDTH_SCALED);
                    imageView.setFitHeight(CARD_HEIGHT_SCALED);

                    imageView.setOnMouseClicked(this::cardClicked);
                    imageView.setOnMouseEntered(this::cardEntered);
                    imageView.setOnMouseExited(this::cardExited);

                    userHand.getChildren().add(imageView);
                    fixWidth(userHand);
                    cardDrawn.setVisible(false);
                    cardDrawn.setRotate(0);
                });
            }
            case BOT1 ->
            {
                pathDisc.setNode(bot1Animation);
                rotate.setNode(bot1Animation);
                rotate.setByAngle(-90);

                rotate.setOnFinished(x ->
                {
                    bot1Animation.setVisible(false);
                    bot1Animation.setRotate(90);
                });
                pathDraw.setNode(cardDrawn);
                pathDraw.setOnFinished(x -> cardDrawn.setVisible(false));

                drawRotate.setNode(cardDrawn);
                drawRotate.setByAngle(90);

                drawRotate.setOnFinished(x ->
                {
                    ImageView imageView = new ImageView(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));

                    imageView.setUserData(card);

                    imageView.setFitWidth(CARD_WIDTH_SCALED);
                    imageView.setFitHeight(CARD_HEIGHT_SCALED);

                    botHand1.getChildren().add(imageView);
                    fixWidth(botHand1);
                    cardDrawn.setVisible(false);
                    cardDrawn.setRotate(0);
                });
            }
            case BOT2 ->
            {
                pathDisc.setNode(bot2Animation);
                rotate.setNode(bot2Animation);
                rotate.setByAngle(-180);

                rotate.setOnFinished(x ->
                {
                    bot2Animation.setVisible(false);
                    bot2Animation.setRotate(180);
                });
                pathDraw.setNode(cardDrawn);
                pathDraw.setOnFinished(x -> cardDrawn.setVisible(false));

                drawRotate.setNode(cardDrawn);
                drawRotate.setByAngle(180);
                drawRotate.setOnFinished(x ->
                {
                    ImageView imageView = new ImageView(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));

                    imageView.setUserData(card);

                    imageView.setFitWidth(CARD_WIDTH_SCALED);
                    imageView.setFitHeight(CARD_HEIGHT_SCALED);

                    botHand2.getChildren().add(imageView);
                    fixWidth(botHand2);
                    cardDrawn.setVisible(false);
                    cardDrawn.setRotate(0);
                });
            }
            case BOT3 ->
            {
                pathDisc.setNode(bot3Animation);
                rotate.setNode(bot3Animation);
                rotate.setByAngle(-90);

                rotate.setOnFinished(x ->
                {
                    bot3Animation.setVisible(false);
                    bot3Animation.setRotate(90);
                });

                pathDraw.setNode(cardDrawn);
                pathDraw.setOnFinished(x -> cardDrawn.setVisible(false));

                drawRotate.setNode(cardDrawn);
                drawRotate.setByAngle(270);

                drawRotate.setOnFinished(x ->
                {
                    ImageView imageView = new ImageView(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));

                    imageView.setUserData(card);

                    imageView.setFitWidth(CARD_WIDTH_SCALED);
                    imageView.setFitHeight(CARD_HEIGHT_SCALED);

                    botHand3.getChildren().add(imageView);
                    fixWidth(botHand3);
                    cardDrawn.setVisible(false);
                    cardDrawn.setRotate(0);
                });
            }
        }
    }
}