package org.juno.model.table.player;

import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.DrawPile;
import org.juno.model.table.Table;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * Defines: Player class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Player
{
    private static final DiscardPile DISCARD_PILE= DiscardPile.getINSTANCE();
    private static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();
    private static final Table TABLE=Table.getINSTANCE();
    protected LinkedList<Card> hand;

    private boolean saidUno;

    public Player()
    {
        hand = new LinkedList<>();
        draw(7);
    }

    //TO TEST
    public void draw(int n)
    {
        for (int i = 0; i < n; i++)
        {
            hand.addFirst(DRAW_PILE.draw());
        }

        hand.sort(new Comparator<Card>()
        {
            @Override
            public int compare(Card o1, Card o2)
            {
                return o1.getValue().getVal()-o2.getValue().getVal();
            }
        });

        notUno();
    }

    //sort by color?

    public boolean discard(Card c)
    {
        if (TABLE.isValid(c))
        {
            DISCARD_PILE.add(c);
            hand.remove(c);
            return true;
        }
        return false;
    }

    public void sayUno()
    {
        saidUno=true;
    }

    public void notUno()
    {
        saidUno=false;
    }
}
