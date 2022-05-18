package org.juno.model.deck;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

    public void shuffle()
    {
        Collections.shuffle(deck);
    }

    public void addAll(Collection<Card> c)
    {
        deck.addAll(c);
    }

    @Override
    public void reset()
    {
        for (Card.Color c: Arrays.stream(Card.Color.values()).filter(x -> x != Card.Color.BLACK).toList())
        {
            for (Card.Value v: Arrays.stream(Card.Value.values()).filter(x-> x!=Card.Value.ZERO&&x!=Card.Value.JOLLY&&x!=Card.Value.PLUSFOUR).toList())
            {
                deck.add(new Card(c, v));
                deck.add(new Card(c, v));
            }
            deck.add(new Card(c, Card.Value.ZERO));
            deck.add(new WildCard(Card.Color.BLACK, Card.Value.JOLLY));
            deck.add(new WildCard(Card.Color.BLACK, Card.Value.PLUSFOUR));
        }
    }
}
