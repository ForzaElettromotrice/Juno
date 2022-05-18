package org.juno.model.deck;

import java.util.Collection;

/**
 * Defines: Discarded class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class DiscardPile extends Deck
{
    private static final DiscardPile INSTANCE = new DiscardPile();

    private DiscardPile()
    {
        super();
    }

    public static DiscardPile getINSTANCE()
    {
        return INSTANCE;
    }

    public void add(Card c)
    {
        deck.addFirst(c);
    }

    public Collection<Card> restart()
    {
        Card top = deck.removeFirst();
        Collection<Card> out = (Collection<Card>) deck.clone();
        reset();
        add(top);
        return out;
    }

    @Override
    public void reset()
    {
        deck.clear();
    }
}
