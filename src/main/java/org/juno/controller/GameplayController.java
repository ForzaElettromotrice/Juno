package org.juno.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.juno.datapackage.*;
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

    public void fixWidth(HBox box)
    {

//        double space = (box.getChildren().size() * CARD_WIDTH_SCALED) - (box.getMaxWidth());
//        System.out.println(space);
//        space = space / box.getChildren().size();
//        System.out.println(space);
//
//        System.out.println(box.getMaxWidth());
//
//        double scaledspace = space / RATIO_FACTOR;
//
//        System.out.println(space);
//        if (space < 0) space = 0;
//        System.out.println(CARD_WIDTH - space);
//
//        for (Node child : box.getChildren())
//        {
//            if (child instanceof ImageView imageView)
//            {
//                imageView.setViewport(new Rectangle2D(0, 0, CARD_WIDTH - scaledspace, CARD_HEIGHT));
//                imageView.setFitWidth(space);
//            }
//        }
        //TODO  rifai sto metodo di merda


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
            {
                ImageView iv = new ImageView(new Image(String.format("%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), drawData.color().toString(), drawData.value().getVal())));
                iv.setFitWidth(CARD_WIDTH_SCALED);
                iv.setFitHeight(CARD_HEIGHT_SCALED);
                userHand.getChildren().add(iv);
                fixWidth(userHand);
            }
            case BOT1 -> test(botHand1);
            case BOT2 -> test(botHand2);
            case BOT3 -> test(botHand3);
        }


    }
    private void test(HBox botHand)
    {
        ImageView iv = new ImageView(new Image(String.format("%s\\src\\main\\resources\\org\\juno\\images\\bempty.png", System.getProperty("user.dir"))));
        iv.setFitWidth(CARD_WIDTH_SCALED);
        iv.setFitHeight(CARD_HEIGHT_SCALED);
        botHand.getChildren().add(iv);
        fixWidth(botHand);
    }

    public void discard(DiscardData discardData)
    {
        switch (discardData.player())
        {
            case PLAYER ->
            {
                String name = String.format("%s%d.png", discardData.color().toString(), discardData.value().getVal());
                for (Node node : userHand.getChildren())
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
