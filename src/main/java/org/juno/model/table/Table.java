package org.juno.model.table;

import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.DrawPile;
import org.juno.model.deck.WildCard;

import java.util.Observable;

/**
 * Defines Table class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public abstract class Table extends Observable implements Runnable
{
	protected static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();
	protected static final DiscardPile DISCARD_PILE = DiscardPile.getINSTANCE();
	protected static final BuildMP BUILD_MP = BuildMP.getINSTANCE();

	protected final TurnOrder turnOrder;

	protected boolean stopEarlier;
	protected boolean canStart = true;
	protected Player winner;


	protected boolean plus2;
	protected boolean plus4;
	protected boolean stop;


	protected Table(TurnOrder to)
	{
		turnOrder = to;
	}

	public Player[] getPlayers()
	{
		return turnOrder.getPlayers();
	}
	public Player getUser()
	{
		return turnOrder.getUser();
	}
	public boolean getStopEarlier()
	{
		return stopEarlier;
	}
	public Player getWinner()
	{
		return winner;
	}
	public Player getCurrentPlayer()
	{
		return turnOrder.getCurrentPlayer();
	}


	public void stopEarlier()
	{
		stopEarlier = true;
	}
	public void canStart()
	{
		canStart = true;
	}


	protected void startGame()
	{
		resetGame();
		boolean endGame = false;


		while (!endGame && !stopEarlier)
		{
			delay(200);


			if (canStart)
			{
				canStart = false;
				startMatch();
				endGame = checkPoints();
				if (!endGame)
				{
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
			}
		}

		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.ENDGAME));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
	}
	protected void startMatch()
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
	}
	protected abstract boolean startTurn(Player player);


	protected boolean checkUno(Player currentPlayer, int delayUno)
	{
		int sizeHand = currentPlayer.getSizeHand();
		if (sizeHand == 0)
		{
			return true;
		} else if (sizeHand == 1)
		{
			delay(delayUno);

			if (!currentPlayer.saidUno())
			{
				setChanged();
				try
				{
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.DIDNTSAYUNO));
				} catch (MessagePackageTypeNotExistsException err)
				{
					System.out.println(err.getMessage());
					err.printStackTrace();
				}
				clearChanged();
				currentPlayer.draw(2);
			}
			return false;
		}
		return false;
	}
	protected void checkEffects(Card chosenCard)
	{
		setChanged();
		try
		{
			switch (chosenCard.getValue())
			{
				case PLUSTWO ->
				{
					plus2 = true;
					stop = true;
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.PLUSTWO));
				}
				case PLUSFOUR ->
				{
					plus4 = true;
					stop = true;
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.PLUSFOUR));
				}
				case REVERSE ->
				{
					turnOrder.reverse();
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.REVERSE));
				}
				case STOP ->
				{
					stop = true;
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.STOP));
				}
				case JOLLY -> notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.JOLLY));
				default ->
				{/*non deve fare niente*/}
			}
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
	}
	protected boolean checkPoints()
	{
		return winner.getPoints() >= 500;
	}


	protected Player applyEffects(Player currentPlayer)
	{
		if (plus2) currentPlayer.draw(2);
		else if (plus4) currentPlayer.draw(4);
		if (stop)
		{
			Player next = turnOrder.nextPlayer();
			setChanged();
			try
			{
				notifyObservers(BUILD_MP.createMP(BuildMP.Actions.TURN, next.getId()));
			} catch (MessagePackageTypeNotExistsException err)
			{
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
			clearChanged();
			return next;
		}
		return currentPlayer;
	}


	protected void resetGame()
	{
		stopEarlier = false;
		turnOrder.resetGame();
		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.STARTGAME));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
	}
	protected void resetMatch()
	{
		turnOrder.resetMatch();
		DRAW_PILE.reset();
		DISCARD_PILE.reset();

		DISCARD_PILE.discard(DRAW_PILE.draw());
		if (DISCARD_PILE.getFirst() instanceof WildCard wildCard) wildCard.setColor(Card.Color.RED);

		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DISCARD, null, DISCARD_PILE.getFirst().getColor(), DISCARD_PILE.getFirst().getValue()));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
	}
	protected void resetTurn()
	{
		plus2 = false;
		plus4 = false;
		stop = false;
	}


	protected void delay(int millis)
	{
		try
		{
			Thread.sleep(millis);
		} catch (InterruptedException err)
		{
			Thread.currentThread().interrupt();
		}
	}


	@Override
	public void run()
	{
		System.out.println("THREAD NATO");
		startGame();
		System.out.println("THREAD MORTO");
	}
}
