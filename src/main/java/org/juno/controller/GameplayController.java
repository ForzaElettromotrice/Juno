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

    private static final int CARD_WIDTH=352;
    private static final int CARD_HEIGHT=500;

    @FXML
    public void cardAdded()
    {
        addCardBig(userHand);
        addCardBig(botHand1);
        addCardBig(botHand2);
        addCardBig(botHand3);
    }

    public void addCardBig(HBox box)
    {
        double space = (box.getChildren().size()*CARD_WIDTH)-(box.getWidth());
        space = space/box.getChildren().size();
        for (Node child:box.getChildren())
        {
            if (child instanceof ImageView imageView)
                imageView.setViewport(new Rectangle2D(0, 0, CARD_WIDTH-space, CARD_HEIGHT));
        }
    }
}
