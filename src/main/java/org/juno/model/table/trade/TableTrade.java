package org.juno.model.table.trade;

import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;

import java.util.Collection;

/**
 * Defines TableTrade class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TableTrade extends Table
{
    private static final TableTrade INSTANCE = new TableTrade();


    private boolean tradeAll;
    private boolean tradeOne;

    /**
     * Constructor, initiate the table in Trade mode
     */
    private TableTrade()
    {
        super(new TurnOrder(TurnOrder.MODALITY.TRADE));
    }

    /**
     * @return the Instance of the class
     */
    public static TableTrade getINSTANCE()
    {
        return INSTANCE;
    }


    /**
     * The loop of the turn, it will be executed in a thread and will be stopped when the turn is over
     * Initialize the turn, then it will be executed the actions until the turn is over
     * in the beginning of the turn it will be executed the startTurn method
     * it notifies the observers all the actions that the player has done
     * if player plays 7 or 0 it will be done a trade
     *
     * @param player The player who is playing
     * @return true if the player has won the match, false otherwise
     */
    @Override
    protected boolean startTurn(Player player)
    {
        PlayerTrade currentPlayer = (PlayerTrade) player;

        System.out.println("TURNO DI " + currentPlayer.getId());
        System.out.println("MANO INIZIALE = " + currentPlayer.getHand());

        resetTurn();
        currentPlayer.resetTurn();

        boolean endTurn = false;

        int delay;
        int delayUno;

        Card chosenCard;

        if (currentPlayer.getId() == BuildMP.PG.PLAYER)
        {
            delay = 100;
            delayUno = 2000;
        } else
        {
            delay = 1000;
            delayUno = 100;
        }

        while (!endTurn && !stopEarlier) //È possibile ci siano ritardi fra i thread e che il codice entri qui anche se non dovrebbe, stopEarlier evita il problema
        {
            delay(delay);

            if (currentPlayer.hasChosen())
            {
                chosenCard = currentPlayer.getChosenCard();
                if ((chosenCard.getColor() != Card.Color.BLACK && chosenCard.getValue() != Card.Value.SEVEN) || (chosenCard.getValue() == Card.Value.SEVEN && currentPlayer.readyToTrade()))
                {
                    if (currentPlayer.getSizeHand() != 0)
                        checkEffects(chosenCard);
                    DISCARD_PILE.discard(chosenCard);
                    setChanged();
                    try
                    {
                        notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DISCARD, currentPlayer.getId(), chosenCard));
                    } catch (MessagePackageTypeNotExistsException err)
                    {
                        System.out.println(err.getMessage());
                        err.printStackTrace();
                    }
                    clearChanged();
                    delay(1000);
                    endTurn = true;
                }
            } else endTurn = currentPlayer.hasPassed();
        }

        if (tradeAll)
        {
            doTrade0();
            tradeAll = false;

            setChanged();
            try
            {
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.SWITCH, ((PlayerTrade) turnOrder.getUser()).getHand(), null, null));
            } catch (MessagePackageTypeNotExistsException err)
            {
                System.out.println(err.getMessage());
                err.printStackTrace();
            }
            clearChanged();
        } else if (tradeOne)
        {
            doTrade7(currentPlayer, currentPlayer.getTargetTrade());
            setChanged();
            try
            {
                notifyObservers(BUILD_MP.createMP(BuildMP.Actions.SWITCH, ((PlayerTrade) turnOrder.getUser()).getHand(), currentPlayer.getId(), currentPlayer.getTargetTrade()));
            } catch (MessagePackageTypeNotExistsException err)
            {
                System.out.println(err.getMessage());
                err.printStackTrace();
            }
            clearChanged();
        }

        System.out.println("MANO finale = " + currentPlayer.getHand());
        System.out.println("\n");

        return checkUno(currentPlayer, delayUno);
    }


    /**
     * check the effects of the card played by the player, then it notifies the observers the effects
     *
     * @param cardPlayed The card played by the player
     */
    @Override
    protected void checkEffects(Card cardPlayed)
    {
        if (cardPlayed.getValue() == Card.Value.ZERO)
        {
            tradeAll = true;
        } else if (cardPlayed.getValue() == Card.Value.SEVEN)
        {
            tradeOne = true;
        } else
        {
            super.checkEffects(cardPlayed);
        }
    }

    /**
     * do the trade of the player with the target player
     *
     * @param from     The player who is doing the trade
     * @param toPlayer The player who is receiving the trade
     */
    private void doTrade7(PlayerTrade from, BuildMP.PG toPlayer)
    {
        Collection<Card> hand1 = from.getHand();

        PlayerTrade player2 = (PlayerTrade) turnOrder.getPlayer(toPlayer);

        Collection<Card> hand2 = player2.getHand();

        from.setHand(hand2);
        player2.setHand(hand1);
    }

    /**
     * do the trade with all the players in clockwise order
     */
    private void doTrade0()
    {
        Collection<Card> newHand = ((PlayerTrade) turnOrder.getCurrentPlayer()).getHand();
        Collection<Card> aux;

        for (int i = 0; i < 4; i++)
        {
            PlayerTrade player = (PlayerTrade) turnOrder.nextPlayer();
            aux = player.getHand();
            player.setHand(newHand);
            newHand = aux;
        }
    }

    /**
     * it will be executed at the start of the Turn,
     * it resets all the variables and notify the observers
     */
    @Override
    protected void resetTurn()
    {
        super.resetTurn();
        tradeOne = false;
        tradeAll = false;
    }


}