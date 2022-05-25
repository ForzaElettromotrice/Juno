package org.juno.model.table.player;

import org.juno.model.deck.Card;

import java.util.Random;

/**
 * Defines: Bot class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Bot extends Player
{
    private static final Random rand = new Random();
    public Card.Color randColor()
    {
        return hand.isEmpty() ? Card.Color.RED : hand.getFirst().getColor();
    }

    @Override
    public void discard(Card c)
    {
        if (hand.size()==2 && rand.nextInt(100)>= 20) sayUno();
        super.discard(c);
    }

    public void chooseCard(Card c)
    {
        for (Card card : hand)
        {
            if(card.isValid(c))
            {
                discard(card);
                return;
            }
        }
        Card card = draw();
        if (card.isValid(c))
        {
            discard(card);
            return;
        }
        setEndTurn(true);
    }
}
