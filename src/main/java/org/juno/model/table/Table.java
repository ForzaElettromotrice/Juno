package org.juno.model.table;

import javafx.event.ActionEvent;
import org.juno.datapackage.BuildMP;
import org.juno.datapackage.Data;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.DrawPile;
import org.juno.model.deck.WildCard;

import java.util.Observable;

/**
 * Defines: Table class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Table extends Observable implements Runnable
{
    private static final Table INSTANCE = new Table();
    private static final DiscardPile DISCARD_PILE = DiscardPile.getINSTANCE();
    private static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();
    private static final TurnOrder TURN_ORDER = TurnOrder.getINSTANCE();
    private static final BuildMP BUILD_MP = BuildMP.getINSTANCE();

    private boolean canStart = true;

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

    public void startGame() throws MessagePackageTypeNotExistsException
    {
        reset();
        boolean endGame = false;

        while (!endGame)
        {
            if (canStart)
            {
                canStart = false;
                startMatch();
                endGame = checkPoints();
            }
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

    public void startMatch() throws MessagePackageTypeNotExistsException
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
        setChanged();
        notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DISCARD, null, firstCard.getColor(), firstCard.getValue()));
        clearChanged();

        while (!endMatch)
        {
            currentPlayer = TURN_ORDER.nextPlayer();
            setChanged();
            notifyObservers(BUILD_MP.createMP(BuildMP.Actions.TURN, currentPlayer.getID()));
            clearChanged();

            if (plus2) currentPlayer.draw(2);
            if (plus4) currentPlayer.draw(4);
            if (stop)
            {
                currentPlayer = TURN_ORDER.nextPlayer();
                setChanged();
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.TURN, currentPlayer.getID()));
                clearChanged();
            }

            plus2 = false;
            plus4 = false;
            stop = false;

            endMatch = turn(currentPlayer);
        }

        updatePoints(currentPlayer);
        setChanged();
        notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.RESETMATCH));
        clearChanged();
    }

    private void checkEffect(Card card) throws MessagePackageTypeNotExistsException
    {
        switch (card.getValue().getVal())
        {
            case 10 ->
            {
                plus2 = true;
                stop = true;
                setChanged();
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.PLUSTWO));
                clearChanged();
            }
            case 11 ->
            {
                TURN_ORDER.reverseTurnOrder();
                setChanged();
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.REVERSE));
                clearChanged();
            }
            case 12 ->
            {
                stop = true;
                setChanged();
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.STOP));
                clearChanged();
            }
            case 14 ->
            {
                plus4 = true;
                stop = true;
                setChanged();
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.PLUSFOUR));
                clearChanged();
            }
            default ->
            {
                setChanged();
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.JOLLY));
                clearChanged();
            }
        }

    }

    public boolean turn(Player player) throws MessagePackageTypeNotExistsException
    {
        boolean endTurn = false;

        while (!endTurn)
        {
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            if (player.hasChosen())
            {
                Card chosenCard = player.getChosenCard();
                setChanged();
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DISCARD, player.getID(), chosenCard.getColor(), chosenCard.getValue()));
                clearChanged();

                if (chosenCard instanceof WildCard wildCard)
                {
                    while (wildCard.getColor().getVal() == 4)
                    {
                    }

                }
                checkEffect(chosenCard);
                DISCARD_PILE.discard(chosenCard);
                endTurn = true;
            } else if (player.hasPassed()) endTurn = true;


        }

        if (player.getSizeHand() == 1)
        {

            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            if (!player.saidUno())
            {
                setChanged();
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.DIDNTSAYUNO));
                clearChanged();
                player.draw(2);
            }
        }
        player.resetTurn();
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

    @Override
    public void run()
    {
        try
        {
            startGame();
        } catch (MessagePackageTypeNotExistsException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void canStart()
    {
        canStart = true;
    }
}
