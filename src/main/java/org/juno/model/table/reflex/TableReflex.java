package org.juno.model.table.reflex;

import javafx.fxml.FXML;
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
public class TableReflex extends Table
{
	private static final TableReflex INSTANCE = new TableReflex();

	private static final int BOT_DELAY_JUMPIN = 100;
	private static final int USER_DELAY_JUMPIN = 2000;


	private final TurnOrder turnOrder = new TurnOrder(TurnOrder.MODALITY.REFLEX);


	private Player afterJump;


	private TableReflex()
	{
	}

	public static TableReflex getINSTANCE()
	{
		return INSTANCE;
	}

	@Override
	public void startMatch()
	{
		resetMatch();

		Player currentPlayer;
		boolean endMatch = false;


		while (!endMatch && !stopEarlier) //È possibile ci siano ritardi fra i thread e che il codice entri qui anche se non dovrebbe, stopEarlier evita il problema
		{

			currentPlayer = turnOrder.nextPlayer();
			setChanged();
			try
			{
				notifyObservers(BUILD_MP.createMP(BuildMP.Actions.TURN, currentPlayer.getId()));
			} catch (MessagePackageTypeNotExistsException err)
			{
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
			clearChanged();

			currentPlayer = applyEffects(currentPlayer);

			if (startTurn(currentPlayer))
			{
				endMatch = true;
				winner = currentPlayer;
			}
		}

		turnOrder.updatePoints(winner);

		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.ENDMATCH));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
	}

	@Override
	public boolean startTurn(Player currentPlayer)
	{
		System.out.println("TURNO DI " + currentPlayer.getId() + "SONO REFLEX");

		resetTurn();
		currentPlayer.resetTurn();

		boolean endTurn = false;
		boolean jumpIn = false;


		while (!endTurn && !jumpIn && !stopEarlier)
		{

			endTurn = currentPlayerCheckStatus(currentPlayer);
			if (!endTurn)
				jumpIn = checkOthers(currentPlayer);
		}

		if (jumpIn)
		{
			turnOrder.setPlayer(afterJump.getId());
			setChanged();
			try
			{
				notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.JUMPIN));
			} catch (MessagePackageTypeNotExistsException err)
			{
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
			clearChanged();

			Card chosenCard = afterJump.getChosenCard();

			discardCard(chosenCard, afterJump);

			return checkUno(afterJump);
		}

		return checkUno(currentPlayer);

	}
	private void discardCard(Card chosenCard, Player afterJump)
	{
		DISCARD_PILE.discard(chosenCard);
		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DISCARD, afterJump.getId(), chosenCard.getColor(), chosenCard.getValue()));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
	}

	private boolean currentPlayerCheckStatus(Player currentPlayer)
	{
		Card chosenCard;

		try
		{
			Thread.sleep(currentPlayer.getId() == BuildMP.PG.PLAYER ? 100 : 1000);
		} catch (InterruptedException err)
		{
			Thread.currentThread().interrupt();
		}

		if (currentPlayer.hasChosen())
		{
			chosenCard = currentPlayer.getChosenCard();
			if (chosenCard.getColor() != Card.Color.BLACK)
			{
				checkEffects(chosenCard);
				discardCard(chosenCard, currentPlayer);
				clearChanged();
				return true;
			}
		}
		return currentPlayer.hasPassed();
	}

	private boolean checkOthers(Player currentPlayer)
	{
		for (Player player : turnOrder.getPlayers())
		{
			if (player.getId() == currentPlayer.getId()) continue;
			if (((PlayerReflex) player).hasJumped())
			{
				afterJump = player;
				return true;
			}
		}
		return false;
	}

	public boolean checkUno(Player player)
	{
		int delayUno = player.getId() == BuildMP.PG.PLAYER ? 2000 : 100;
		return super.checkUno(player, delayUno);
	}


	@Override
	public void resetGame()
	{
		super.resetGame();
		turnOrder.resetGame();
	}
	@Override
	public void resetMatch()
	{
		super.resetMatch();
		turnOrder.resetMatch();
	}
	@Override
	public void resetTurn()
	{
		super.resetTurn();
		afterJump = null;
	}

}
