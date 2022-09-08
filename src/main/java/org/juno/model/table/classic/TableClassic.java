package org.juno.model.table.classic;

import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;

/**
 * Defines TableClassic class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TableClassic extends Table
{
	private static final TableClassic INSTANCE = new TableClassic();

	/**
	 * Constructor, initiate the table in Classic mode
	 */
	private TableClassic()
	{
		super(new TurnOrder(TurnOrder.MODALITY.CLASSIC));
	}

	/**
	 * @return the Instance of the class
	 */
	public static TableClassic getINSTANCE()
	{
		return INSTANCE;
	}

	/**
	 * The loop of the turn, it will be executed in a thread and will be stopped when the turn is over
	 * Initialize the turn, then it will be executed the actions until the turn is over
	 * in the beginning of the turn it will be executed the startTurn method
	 * it notifies the observers all the actions that the player has done
	 *
	 * @param player The player who is playing
	 * @return true if the player has won the match, false otherwise
	 */
	@Override
	protected boolean startTurn(Player player)
	{
		PlayerClassic currentPlayer = (PlayerClassic) player;


		resetTurn();
		currentPlayer.resetTurn();

		boolean endTurn = false;

		int delay;
		int delayUno;

		Card chosenCard;

		if (currentPlayer.getId() == BuildMP.PG.PLAYER)
		{
			delay = 100;
			delayUno = 1250;
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
				if (chosenCard.getColor() != Card.Color.BLACK)
				{
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
					checkEffects(chosenCard);
					endTurn = true;
					delay(500);
				}
			} else endTurn = currentPlayer.hasPassed();
		}
		return checkUno(currentPlayer, delayUno);
	}
}
