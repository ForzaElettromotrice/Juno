package org.juno.controller;


import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
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

import java.util.stream.IntStream;

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

    private static final String USER_DIR = System.getProperty("user.dir");


    @FXML
    public AnchorPane anchorPane;


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
    public ImageView colorGrid;
    @FXML
    public GridPane colorPane;


    @FXML
    public Button pass;
    @FXML
    public Button juno;


    /**
     * called when is the user click on the draw pile
     */
    @FXML
    public void drawClicked()
    {
        Player player = TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.draw();
        pass.setDisable(false);
    }
    /**
     * called when is the user click on the "pass" button
     */
    @FXML
    public void passClicked()
    {
        pass.setDisable(true);

        beep();
        TABLE_TRADE.getUser().setHasPassed();
    }
    /**
     * called when is the user click on the "say juno" button
     */
    @FXML
    public void sayUno()
    {
        buttonClick();
        TABLE_TRADE.getUser().sayUno();
        juno.setVisible(false);
    }

    /**
     * called when is the user click on the "red" button
     */
    @FXML
    public void redClicked()
    {
        zoomAnimation(BuildMP.Effects.RED);
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.RED);
        colorGrid.setVisible(false);
        colorPane.setVisible(false);
    }
    /**
     * called when is the user click on the "blue" button
     */
    @FXML
    public void blueClicked()
    {
        zoomAnimation(BuildMP.Effects.BLUE);
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.BLUE);
        colorGrid.setVisible(false);
        colorPane.setVisible(false);
    }
    /**
     * called when is the user click on the "yellow" button
     */
    @FXML
    public void yellowClicked()
    {
        zoomAnimation(BuildMP.Effects.YELLOW);
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.YELLOW);
        colorGrid.setVisible(false);
        colorPane.setVisible(false);
    }
    /**
     * called when is the user click on the "green" button
     */
    @FXML
    public void greenClicked()
    {
        zoomAnimation(BuildMP.Effects.GREEN);
        beep();
        ((WildCard) TABLE_TRADE.getUser().getChosenCard()).setColor(Card.Color.GREEN);
        colorGrid.setVisible(false);
        colorPane.setVisible(false);
    }

    /**
     * called when the user click on the "bot1 hand" button
     */
    @FXML
    public void bot1HandClicked()
    {
        beep();
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT1);
    }
    /**
     * called when the user click on the "bot2 hand" button
     */
    @FXML
    public void bot2HandClicked()
    {
        beep();
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT2);
    }
    /**
     * called when the user click on the "bot3 hand" button
     */
    @FXML
    public void bot3HandClicked()
    {
        beep();
        PlayerTrade player = (PlayerTrade) TABLE_TRADE.getCurrentPlayer();

        if (player.getId() != BuildMP.PG.PLAYER) return;

        player.setTargetTrade(BuildMP.PG.BOT3);
    }

    /**
     * called when the user click "exit" button
     */
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

    /**
     * plays the button entered sounds
     */
    @FXML
    public void buttonEntered()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
    }

    /**
     * called when the mouse enter on a card
     *
     * @param mouseEvent the mouse event
     */
    public void cardEntered(MouseEvent mouseEvent)
    {
        if (TABLE_TRADE.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

        cardFlip();
        ImageView card = (ImageView) mouseEvent.getSource();
        card.setTranslateY(-30);
    }
    /**
     * called when the mouse exit from a card
     *
     * @param mouseEvent the mouse event
     */
    public void cardExited(MouseEvent mouseEvent)
    {
        if (TABLE_TRADE.getCurrentPlayer().getId() != BuildMP.PG.PLAYER) return;

        ImageView card = (ImageView) mouseEvent.getSource();
        card.setTranslateY(0);
    }
    /**
     * called when the mouse click on a card
     *
     * @param mouseEvent the mouse event
     */
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

    /**
     * fix the overlapping of the cards
     *
     * @param box the box where the cards are
     */
    private void fixWidth(HBox box)
    {
        double spacing = ((box.getChildren().size() * Costants.CARD_WIDTH_SCALED.getVal()) - (box.getMaxWidth())) / box.getChildren().size();


        if (spacing <= 0) spacing = 30;

        box.setSpacing(-spacing);
    }

    /**
     * make the animation of the card drawn
     * then it add the card to the hand of the player given
     *
     * @param drawData the data of the card drawn (color, value) and the player who draw it
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
     * create the imageview of the card drawn for the animation
     *
     * @param card the card drawn (color, value)
     * @return the imageview of the card drawn
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
     * create the imageview of the card drawn for the hand of the player
     *
     * @param image    the image of the card drawn
     * @param card     the card drawn (color, value)
     * @param isPlayer true if the player is the user, false otherwise
     * @return the imageview of the card drawn
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
     * create the path transition of the card drawn
     *
     * @param pg   the player who draw the card
     * @param node the imageview of the card drawn
     * @return the path transition of the card drawn
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
            node.setVisible(false);
        });

        return pathTransition;


    }
    /**
     * create the rotatation transition of the card drawn by the bot
     *
     * @param node the imageview of the card drawn
     * @return the rotatation transition of the card drawn
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
     * create the rotatation transition of the card drawn by the user
     *
     * @param node the imageview of the card drawn
     * @return the rotatation transition of the card drawn
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
     * make the animation of the card played by the player or the bot and add the card to the table
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

            node.setVisible(false);
        });

        return pathTransition;
    }
    /**
     * create the rotatation transition of the card played
     *
     * @param pg   the player who played the card
     * @param node the imageview of the card played
     * @return the rotatation transition of the card played
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
     * make the animation of the "start turn" of a player
     *
     * @param turnData the data of the player who start the turn
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
     * make the animation of the effect given
     *
     * @param effectData the data of the effect given
     */
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

    /**
     * create the imageview of the effect given
     *
     * @param effect the effect given
     * @return the imageview of the effect given
     */
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

    /**
     * make the animation of the effect given
     *
     * @param effect the effect given
     */
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
    /**
     * make the animation of the switch atcion
     *
     * @param switchData the data of the switch action
     */
    @Override
    public void doSwitch(SwitchData switchData)
    {
        System.out.println("HAND TO SWITCH " + switchData.newHand());
        System.out.println("FROM " + switchData.fromPg());
        System.out.println("TO " + switchData.toPg());

        int userSize = userHand.getChildren().size();
        userHand.getChildren().clear();

        switchData.newHand().stream().map(card -> new DrawData(BuildMP.PG.PLAYER, card)).forEach(this::draw);


        if (switchData.fromPg() == null)
        {
            int bot1Size = botHand1.getChildren().size();
            int bot2Size = botHand2.getChildren().size();

            botHand1.getChildren().clear();
            botHand2.getChildren().clear();
            botHand3.getChildren().clear();


            IntStream.range(0, userSize).mapToObj(i -> new DrawData(BuildMP.PG.BOT1, null)).forEach(this::draw);
            IntStream.range(0, bot1Size).mapToObj(i -> new DrawData(BuildMP.PG.BOT2, null)).forEach(this::draw);
            IntStream.range(0, bot2Size).mapToObj(i -> new DrawData(BuildMP.PG.BOT3, null)).forEach(this::draw);
        } else
        {
            BuildMP.PG pg = switchData.fromPg() != BuildMP.PG.PLAYER ? switchData.fromPg() : switchData.toPg();

            switch (pg)
            {
                case PLAYER ->
                {
                }
                case BOT1 -> botHand1.getChildren().clear();
                case BOT2 -> botHand2.getChildren().clear();
                case BOT3 -> botHand3.getChildren().clear();
            }
            IntStream.range(0, userSize).mapToObj(i -> new DrawData(pg, null)).forEach(this::draw);
        }
    }

    /**
     * manage the start, endmatch and endgame actions
     *
     * @param gameflowData the data of the action
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
     * loads on the view the point earned by the players
     *
     * @param pointsData the data of the points earned
     */
    @Override
    public void getPoints(PointsData pointsData)
    {
        ((EndMatchTradeController) GEN_VIEW.getEndMatchTrade().getUserData()).load(pointsData);
    }

    /**
     * loads the scene of the endMatch
     */
    private void nextMatch()
    {
        GEN_VIEW.changeScene(GenView.SCENES.ENDMATCHTRADE, anchorPane);
    }
    /**
     * loads the scene of the endGame
     */
    private void goEndgame()
    {
        GEN_VIEW.changeScene(GenView.SCENES.ENDGAME, anchorPane);

        ((EndgameController) GEN_VIEW.getEndgame().getUserData()).load(TABLE_TRADE);
    }
    /**
     * reset the view for the next match
     */
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
        userTurn.setVisible(false);
        bot1Turn.setVisible(false);
        bot2Turn.setVisible(false);
        bot3Turn.setVisible(false);

        firstDiscarded.setImage(null);
        secondDiscarded.setImage(null);
        thirdDiscarded.setImage(null);
        TABLE_TRADE.canStart();
    }

    /**
     * plays the sound of the "beep"
     */
    protected void beep()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
    }
    /**
     * plays the sound of the "card flip"
     */
    protected void cardFlip()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.CARDFLIP);
    }
    /**
     * plays the sound of the "button click"
     */
    protected void buttonClick()
    {
        AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
    }

}