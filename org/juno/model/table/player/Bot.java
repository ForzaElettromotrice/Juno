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
    public Card.Color randColor()
    {
        return hand.isEmpty() ? Card.Color.RED : hand.getFirst().getColor();
    }

    @Override
    public boolean discard(Card c)
    {
        if (hand.size()==2 && new Random().nextInt(100)>= 20) sayUno();
        return super.discard(c);
    }
}
