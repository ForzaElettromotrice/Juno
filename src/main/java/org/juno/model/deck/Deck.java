package org.juno.model.deck;

import java.util.LinkedList;
/**
 * Defines: Deck class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public abstract class Deck
{
    protected LinkedList<Card> deck;

    /**
     * Create the deck to the initial state
     */
    protected Deck()
    {
        deck = new LinkedList<>();
        reset();
    }

    /**
     * Resets the deck to the initial state
     */
    abstract void reset();
}
