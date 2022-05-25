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

    protected boolean chosenCard;
    protected boolean endTurn;
    protected LinkedList<Card> hand;

    private boolean saidUno;

    public Player()
    {
        hand = new LinkedList<>();
        draw(7);
    }

    public void sort()
    {
        hand.sort(Comparator.comparingInt(o -> o.getValue().getVal()));
    }

    //TO TEST
    public void draw(int n)
    {
        for (int i = 0; i < n; i++)
        {
            hand.addFirst(DRAW_PILE.draw());
        }
        sort();
        notUno();
    }

    public Card draw()
    {
        Card c = DRAW_PILE.draw();
        hand.addFirst(c);
        sort();
        return c;
    }

    //sort by color?

    public void discard(Card c)
    {
        if (TABLE.isValid(c))
        {
            DISCARD_PILE.discard(c);
            hand.remove(c);
            setChosenCard(true);
        }
    }

    public void sayUno()
    {
        saidUno=true;
    }

    public void notUno()
    {
        saidUno=false;
    }

    public void setChosenCard(boolean b)
    {
        chosenCard=b;
    }

    public void setEndTurn(boolean b)
    {
        endTurn=b;
    }

    public boolean getChosenCard()
    {
        return chosenCard;
    }

    public boolean getEndTurn()
    {
        return endTurn;
    }
}
