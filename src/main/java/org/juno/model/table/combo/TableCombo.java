package org.juno.model.table.combo;


import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;

/**
 * Defines TableCombo class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TableCombo extends Table
{
	private static final TableCombo INSTANCE = new TableCombo();

	/**
	 * Constructor, initiate the table in Combo mode
	 */
	private TableCombo()
	{
		super(new TurnOrder(TurnOrder.MODALITY.COMBO));
	}

	/**
	 * @return the Instance of the class
	 */
	public static TableCombo getINSTANCE()
	{
		return INSTANCE;
	}


	/**
	 * The loop of the turn, it will be executed in a thread and will be stopped when the turn is over
	 * Initialize the turn, then it will be executed the actions until the turn is over
	 * in the beginning of the turn it will be executed the startTurn method
	 * it notifies the observers all the actions that the player has done
	 * after the player plays a card, check if the player can play another card, if not, the turn is over
	 *
	 * @param player The player who is playing
	 * @return true if the player has won the match, false otherwise
	 */
	@Override
	protected boolean startTurn(Player player)
	{
		PlayerCombo currentPlayer = (PlayerCombo) player;

		System.out.println("\nTURNO DI " + currentPlayer.getId());

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

		while (!endTurn && !stopEarlier)
		{
			delay(delay);

			if (currentPlayer.hasChosen())
			{
				chosenCard = player.getChosenCard();

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
					checkEffects(chosenCard);
					clearChanged();
					endTurn = !currentPlayer.canPlay();
					delay(500);
				}
			} else endTurn = currentPlayer.hasPassed();
		}

		return checkUno(currentPlayer, delayUno);
	}

}
