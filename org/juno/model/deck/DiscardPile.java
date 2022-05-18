package org.juno.model.deck;

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

    @Override
    void reset()
    {
        deck.clear();
    }
}
