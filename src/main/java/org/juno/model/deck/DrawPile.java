package org.juno.model.deck;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Defines: Deck class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class DrawPile extends Deck
{
    private static final DrawPile INSTANCE = new DrawPile();

    /**
     * Constructor, initiate the deck
     */
    private DrawPile()
    {
        super();
    }

    /**
     * Returns the Instance of the class
     * @return the Instance of the class
     */
    public static DrawPile getINSTANCE()
    {
        return INSTANCE;
    }

    /**
     * Remove the first Card of the deck
     * @return the first Card of the deck
     */
    public Card draw()
    {
        return deck.removeFirst();
    }

    /**
     * Shuffles the deck
     */
    public void shuffle()
    {
        Collections.shuffle(deck);
    }

    /**
     * Add all the given Card to the deck
     * @param c Collections of Cards
     */
    public void addAll(Collection<Card> c)
    {
        deck.addAll(c);
    }

    /**
     * Reset the deck to the initial state
     */
    @Override
    public void reset()
    {
        for (Card.Color c: Arrays.stream(Card.Color.values()).filter(x -> x != Card.Color.BLACK).toList())
        {
            for (Card.Value v : Arrays.stream(Card.Value.values()).filter(x -> x != Card.Value.ZERO && x != Card.Value.JOLLY && x != Card.Value.PLUSFOUR).toList())
            {
                deck.add(new Card(c, v));
                deck.add(new Card(c, v));
            }
            deck.add(new Card(c, Card.Value.ZERO));
            deck.add(new WildCard(Card.Value.JOLLY));
            deck.add(new WildCard(Card.Value.PLUSFOUR));
        }
        shuffle();
    }
}
