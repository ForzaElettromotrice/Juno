package org.juno.model.deck;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Defines: DiscardedPile class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class DiscardPile extends GenDeck
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
     * @return the instance of the DiscardPile
     */
    public static DiscardPile getINSTANCE()
    {
        return INSTANCE;
    }

    /**
     * Add a card on top of the deck
     * @param c The given Card
     */
    public void discard(Card c)
    {
        deck.addFirst(c);
    }

    /**
     * Remove all the Card from the deck except the first one
     * @return all the discarded card
     */
    public Collection<Card> reshuffle()
    {
        Card top = deck.removeFirst();
        List<Card> out = new LinkedList<>(deck);
        reset();
        discard(top);

        for (Card card : out)
        {
            if (card instanceof WildCard wildCard) wildCard.setColor(Card.Color.BLACK);
        }

        Collections.shuffle(out);
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
