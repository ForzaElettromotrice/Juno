package org.juno.model.table.combo;


import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;

/**
 * Defines TableReflex class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TableCombo extends Table
{
	private static final TableCombo INSTANCE = new TableCombo();

	private TableCombo()
	{
		super(new TurnOrder(TurnOrder.MODALITY.COMBO));
	}


	public static TableCombo getINSTANCE()
	{
		return INSTANCE;
	}


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
					System.out.println("HA GIOCATO QUESTA CARTA " + chosenCard);
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
					endTurn = !currentPlayer.canPlay();
				}
			} else endTurn = currentPlayer.hasPassed();
		}

		return checkUno(currentPlayer, delayUno);
	}

}
