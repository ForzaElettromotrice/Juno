package org.juno.model.deck;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Defines: DiscardedPile class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class DiscardPile extends Deck
{
    private static final DiscardPile INSTANCE = new DiscardPile();

    /**
     * Constructor, initiate the deck
     */
    private DiscardPile()
    {
        super();
    }

    /**
     * Returns the Instance of the class
     * @return class Instance
     */
    public static DiscardPile getINSTANCE()
    {
        return INSTANCE;
    }

    /**
     * Add a card on top of the deck
     * @param c The given Card
     */
    public void add(Card c)
    {
        deck.addFirst(c);
    }

    /**
     * Remove all the Card from the deck except the first one
     * @return all the discarded card
     */
    public Collection<Card> restart()
    {
        Card top = deck.removeFirst();
        Collection<Card> out = new LinkedList<>(deck);
        reset();
        add(top);
        return out;
    }

    public Card getFirst()
    {
        return deck.getFirst();
    }

    /**
     * Reset the deck to the initial state
     */
    @Override
    public void reset()
    {
        deck.clear();
    }
}
