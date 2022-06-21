package org.juno.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.juno.datapackage.*;
import org.juno.model.deck.Card;

/**
 * Defines: GameplayController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class GameplayController
{
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
    public ImageView topDrawCard;
    @FXML
    public ImageView middleDrawCard;
    @FXML
    public ImageView bottomDrawCard;
    @FXML
    public ImageView cardDiscarded;

    private static final int CARD_WIDTH = 352;
    private static final int CARD_HEIGHT = 500;
    private static final int CARD_WIDTH_SCALED = 189;
    private static final int CARD_HEIGHT_SCALED = 264;
    private static final double RATIO_FACTOR = 0.536931818;

    @FXML
    public void cardAdded()
    {
        addCard(userHand);
        addCard(botHand1);
        addCard(botHand2);
        addCard(botHand3);
    }

    public void addCard(HBox box)
    {

        double space = (box.getChildren().size() * CARD_WIDTH_SCALED) - (box.getWidth());
        space = space / box.getChildren().size();
        space = space / RATIO_FACTOR;
        if (space < 0) space = 0;
        for (Node child : box.getChildren())
        {
            if (child instanceof ImageView imageView)
                imageView.setViewport(new Rectangle2D(0, 0, CARD_WIDTH - space, CARD_HEIGHT));
        }
    }

    @FXML
    public void middleReached()
    {
        topDrawCard.setVisible(false);
    }

    @FXML
    public void quarterReached()
    {
        middleDrawCard.setVisible(false);
    }

    public void draw(DrawData drawData)
    {
        switch (drawData.player())
        {
            case PLAYER ->
                    userHand.getChildren().add(new ImageView(new Image(String.format("%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), drawData.color().toString(), drawData.value().getVal()))));
            case BOT1 ->
                    botHand1.getChildren().add(new ImageView(new Image(String.format("%s\\src\\main\\resources\\org\\juno\\images\\backcard.jpg", System.getProperty("user.dir")))));
            case BOT2 ->
                    botHand2.getChildren().add(new ImageView(new Image(String.format("%s\\src\\main\\resources\\org\\juno\\images\\backcard.jpg", System.getProperty("user.dir")))));
            case BOT3 ->
                    botHand3.getChildren().add(new ImageView(new Image(String.format("%s\\src\\main\\resources\\org\\juno\\images\\backcard.jpg", System.getProperty("user.dir")))));
        }
    }

    public void discard(DiscardData discardData)
    {
        switch (discardData.player())
        {
            case PLAYER ->
            {
                String name = String.format("%s%d.png", discardData.color().toString(), discardData.value().getVal());
                for (Node node: userHand.getChildren())
                {
                    if (node instanceof ImageView iV && iV.getImage().getUrl().endsWith(name))
                    {
                        userHand.getChildren().remove(node);
                        break;
                    }
                }
            }
            case BOT1 -> botHand1.getChildren().remove(0);
            case BOT2 -> botHand2.getChildren().remove(0);
            case BOT3 -> botHand3.getChildren().remove(0);
        }
        cardDiscarded.setImage(new Image(String.format("%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), discardData.color().toString(), discardData.value().getVal())));
    }

    public void turn(TurnData turnData)
    {
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

    }
}
