package org.juno.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
    public ImageView topDrawCard;
    @FXML
    public ImageView middleDrawCard;
    @FXML
    ImageView bottomDrawCard;

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
}
