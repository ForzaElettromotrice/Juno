package org.juno.model.table;

import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.DrawPile;

/**
 * Defines: Table class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Table
{
    private static final Table INSTANCE = new Table();
    private static final DiscardPile DISCARD_PILE= DiscardPile.getINSTANCE();
    private static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();
    //TODO: game management

    private Table()
    {

    }

    public static Table getINSTANCE()
    {
        return INSTANCE;
    }

    public boolean isValid(Card c)
    {
        return c.isValid(DISCARD_PILE.getFirst());
    }
}
