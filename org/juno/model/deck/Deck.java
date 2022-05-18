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

    protected Deck()
    {
        deck = new LinkedList<>();
        reset();
    }

    abstract void reset();
}
