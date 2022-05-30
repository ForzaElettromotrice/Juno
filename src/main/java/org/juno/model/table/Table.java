package org.juno.model.table;

import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.DrawPile;
import org.juno.model.deck.WildCard;

/**
 * Defines: Table class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Table
{
    private static final Table INSTANCE = new Table();
    private static final DiscardPile DISCARD_PILE = DiscardPile.getINSTANCE();
    private static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();
    private static final TurnOrder TURN_ORDER = TurnOrder.getINSTANCE();


    private boolean plus2;
    private boolean plus4;
    private boolean stop;
    private Player winner;


    private Table()
    {
    }

    public static Table getINSTANCE()
    {
        return INSTANCE;
    }

    public void startGame()
    {
        reset();
        boolean endGame = false;

        while (!endGame)
        {
            startMatch();
            endGame = checkPoints();
        }
    }
    private boolean checkPoints()
    {
        for (Player player : TURN_ORDER.getPlayers())
        {
            if (player.getPoints() >= 500)
            {
                winner = player;
                return true;
            }
        }
        return false;
    }

    public void startMatch()
    {
        TURN_ORDER.resetMatch();
        DISCARD_PILE.reset();
        DRAW_PILE.reset();

        boolean endMatch = false;
        Player currentPlayer = null;

        for (int i = 0; i < 4; i++)
        {
            currentPlayer = TURN_ORDER.nextPlayer();
            currentPlayer.draw(7);
        }

        Card firstCard = DRAW_PILE.draw();
        if (firstCard instanceof WildCard wildCard) wildCard.setColor(Card.Color.RED);

        DISCARD_PILE.discard(firstCard);

        while (!endMatch)
        {
            currentPlayer = TURN_ORDER.nextPlayer();

            if (plus2) currentPlayer.draw(2);
            if (plus4) currentPlayer.draw(4);
            if (stop) currentPlayer = TURN_ORDER.nextPlayer();

            plus2 = false;
            plus4 = false;
            stop = false;

            endMatch = turn(currentPlayer);
        }

        updatePoints(currentPlayer);
    }

    private void checkEffect(Card card)
    {
        switch (card.getValue().getVal())
        {
            case 10 ->
            {
                plus2 = true;
                stop = true;
            }
            case 11 -> TURN_ORDER.reverseTurnOrder();
            case 12 -> stop = true;
            case 14 ->
            {
                plus4 = true;
                stop = true;
            }
            default ->
            {
            }
        }

    }

    public boolean turn(Player player)
    {
        boolean endTurn = false;

        while (!endTurn)
        {


            if (player.hasChosen())
            {
                Card chosenCard = player.getChosenCard();


                if (chosenCard instanceof WildCard wildCard)
                {
                    while (wildCard.getColor().getVal() == 4)
                    {
                    }

                }
                checkEffect(chosenCard);
                endTurn = true;
            } else if (player.hasPassed()) endTurn = true;

        }

        player.resetTurn();

        if (player.getSizeHand() == 1)
        {

            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            if (!player.saidUno()) player.draw(2);
        }
        return player.getSizeHand() == 0;
    }

    private void updatePoints(Player winner)
    {
        for (Player player : TURN_ORDER.getPlayers())
        {
            winner.addPoints(player.calculatePoints());
        }
    }

    private void reset()
    {
        TURN_ORDER.resetGame();
        winner = null;
        plus2 = false;
        plus4 = false;
        stop = false;
    }
}