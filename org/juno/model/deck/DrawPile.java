package org.juno.model.deck;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Defines: Deck class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class DrawPile extends Deck
{
    private static final DrawPile INSTANCE = new DrawPile();

    private DrawPile()
    {
        super();
    }

    public static DrawPile getINSTANCE()
    {
        return INSTANCE;
    }

    public Card draw()
    {
        return deck.removeFirst();
    }

    @Override
    void reset()
    {
        for (Card.Color c: Arrays.stream(Card.Color.values()).filter(x -> x != Card.Color.BLACK).toList())
        {
            for (Card.Value v: Arrays.stream(Card.Value.values()).filter(x-> x!=Card.Value.ZERO&&x!=Card.Value.JOLLY&&x!=Card.Value.PLUSFOUR).toList())
            {
                deck.add(new Card(c, v));
                deck.add(new Card(c, v));
            }
            deck.add(new Card(c, Card.Value.ZERO));
            deck.add(new Card(Card.Color.BLACK, Card.Value.JOLLY));
            deck.add(new Card(Card.Color.BLACK, Card.Value.PLUSFOUR));
        }
        System.out.println(deck);
    }

    public static void main(String[] args)
    {
        new DrawPile().reset();
    }
}
