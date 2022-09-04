package org.juno.model.deck;

import java.util.LinkedList;
/**
 * Defines: Deck class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public abstract class GenDeck
{
    protected final LinkedList<Card> deck;

    /**
     * Create the deck to the initial state
     */
    protected GenDeck()
    {
        deck = new LinkedList<>();
        reset();
    }

    /**
     * Resets the deck to the initial state
     */
    abstract void reset();
}
