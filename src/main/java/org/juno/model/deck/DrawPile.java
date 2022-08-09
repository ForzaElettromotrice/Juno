package org.juno.model.deck;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Defines: Deck class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class DrawPile extends GenDeck
{
    private static final DrawPile INSTANCE = new DrawPile();
    private static final DiscardPile DISCARD_PILE = DiscardPile.getINSTANCE();

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
        System.out.println(deck.size());
        try
        {
            return deck.removeFirst();
        } catch (NoSuchElementException err)
        {
            addAll(DISCARD_PILE.reshuffle());
            return deck.removeFirst();
        }
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
        deck.clear();
        for (Card.Color c : Arrays.stream(Card.Color.values()).filter(x -> x != Card.Color.BLACK).toList())
        {
            for (Card.Value v : Arrays.stream(Card.Value.values()).filter(x -> x != Card.Value.ZERO && x != Card.Value.JOLLY && x != Card.Value.PLUSFOUR).toList())
            {
                deck.add(new Card(c, v));
                deck.add(new Card(c, v));
            }
            deck.add(new Card(c, Card.Value.ZERO));
        }
        deck.add(new WildCard(Card.Value.JOLLY));
        deck.add(new WildCard(Card.Value.JOLLY));
        deck.add(new WildCard(Card.Value.PLUSFOUR));
        deck.add(new WildCard(Card.Value.PLUSFOUR));
        shuffle();
    }
}
