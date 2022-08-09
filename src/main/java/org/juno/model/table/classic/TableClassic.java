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


	private TableClassic()
	{
		super(new TurnOrder(TurnOrder.MODALITY.CLASSIC));
	}

	public static TableClassic getINSTANCE()
	{
		return INSTANCE;
	}

	@Override
	protected boolean startTurn(Player player)
	{
		PlayerClassic currentPlayer = (PlayerClassic) player;

		System.out.println("TURNO DI " + currentPlayer.getId());

		resetTurn();
		currentPlayer.resetTurn();

		boolean endTurn = false;

		int delay;
		int delayUno;

		Card chosenCard = null;

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
				if (chosenCard.getColor() != Card.Color.BLACK)
				{
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
					endTurn = true;
				}
			} else endTurn = currentPlayer.hasPassed();
		}
		

		return checkUno(currentPlayer, delayUno);
	}
}
