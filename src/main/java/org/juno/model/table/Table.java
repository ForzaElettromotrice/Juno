package org.juno.model.table;

import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.DrawPile;
import org.juno.model.table.player.Bot;
import org.juno.model.table.player.Player;

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
    private static final TurnOrder TURN_ORDER = TurnOrder.getINSTANCE();

    private boolean isInverted;
    private boolean endGame;


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

//    public void reset()
//    {
//        turnOrder.clear();
//        turnOrder.addFirst(new Player());
//        turnOrder.addLast(new Bot());
//        turnOrder.addLast(new Bot());
//        turnOrder.addLast(new Bot());
//        turnOrder.setFirst();
//        DISCARD_PILE.reset();
//        DRAW_PILE.reset();
//        isInverted=false;
//        endGame=false;
//    }

//    public void startMatch()
//    {
//        reset();
//        DISCARD_PILE.discard(DRAW_PILE.draw());
//        Player currentPlayer = turnOrder.getFirst();
//        while (!endGame)
//        {
//            if (currentPlayer instanceof Bot)
//            {
//
//            }
//            currentPlayer=turnOrder.getNext();
//        }
//    }
//
//    private void turn(Player p)
//    {
//        while(!p.getEndTurn()||!p.getChosenCard())
//        {
//
//        }
//    }
}
