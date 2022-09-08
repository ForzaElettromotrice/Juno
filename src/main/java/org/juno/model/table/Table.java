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


	protected int plus2;
	protected int plus4;
	protected int stop;

	/**
	 * Constructor, initiate the table
	 *
	 * @param to The given TurnOrder
	 */
	protected Table(TurnOrder to)
	{
		turnOrder = to;
	}
	/**
	 * @return the Array of the players
	 */
	public Player[] getPlayers()
	{
		return turnOrder.getPlayers();
	}
	/**
	 * @return the User Player instance
	 */
	public Player getUser()
	{
		return turnOrder.getUser();
	}
	/**
	 * @return true if the game has been stopped earlier else false
	 */
	public boolean getStopEarlier()
	{
		return stopEarlier;
	}
	/**
	 * @return the Player who won the game
	 */
	public Player getWinner()
	{
		return winner;
	}
	/**
	 * @return the current Player who is playing
	 */
	public Player getCurrentPlayer()
	{
		return turnOrder.getCurrentPlayer();
	}

	/**
	 * stop the game earlier
	 */
	public void stopEarlier()
	{
		stopEarlier = true;
		winner = null;
	}
	/**
	 * allow the Game loop to start a new Match
	 */
	public void canStart()
	{
		canStart = true;
	}

	/**
	 * The loop of the game, it will be executed in a thread and will be stopped when the game is over
	 * Initialize the game, then it will be executed the matches until the game is over
	 * after every match it controls if the game is over, if not it will be executed a new match
	 * in the beginning of every match it will be executed the reset of the table
	 */
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
						notifyObservers(BUILD_MP.createMP(BuildMP.Actions.GAMEFLOW, BuildMP.Gameflow.ENDMATCH));
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
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.GAMEFLOW, BuildMP.Gameflow.ENDGAME));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
	}
	/**
	 * The loop of the match, it will be executed in a thread and will be stopped when the match is over
	 * Initialize the match, then it will be executed the turns until the match is over
	 * in the beginning of the match it will be executed the resetMatch method
	 */
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

		if (!stopEarlier)
		{
			turnOrder.updatePoints(winner);
			setChanged();
			try
			{
				notifyObservers(BUILD_MP.createMP(BuildMP.Actions.POINTS, turnOrder.getPlayer(BuildMP.PG.PLAYER).getPoints(), turnOrder.getPlayer(BuildMP.PG.BOT1).getPoints(), turnOrder.getPlayer(BuildMP.PG.BOT2).getPoints(), turnOrder.getPlayer(BuildMP.PG.BOT3).getPoints()));
			} catch (MessagePackageTypeNotExistsException err)
			{
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
			clearChanged();
		}
	}
	/**
	 * The loop of the turn, it will be executed in a thread and will be stopped when the turn is over
	 * Initialize the turn, then it will be executed the actions until the turn is over
	 * in the beginning of the turn it will be executed the startTurn method
	 * it notifies the observers all the actions that the player has done
	 *
	 * @param player The player who is playing
	 * @return true if the player has won the match else false
	 */
	protected abstract boolean startTurn(Player player);

	/**
	 * check if the player has said UNO, if not the player will draw 2 cards and return false
	 * check if the player has won the match, if yes it will return true else false
	 * it notifies the observers if the player has won the match, if the player has said UNO or not
	 *
	 * @param currentPlayer The player who is playing
	 * @param delayUno      The delay to wait before the player will be forced to draw 2 cards
	 * @return true if the player has won the match else false
	 */
	protected boolean checkUno(Player currentPlayer, int delayUno)
	{
		int sizeHand = currentPlayer.getSizeHand();
		if (sizeHand == 0)
		{
			return true;
		} else if (sizeHand == 1)
		{


			if (!currentPlayer.saidUno())    // serve per passare subito in caso lo abbia detto in anticipo, altrimenti aspetta 2 secondi e poi ricontrolla
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
					delay(2000);
					currentPlayer.draw(2);
					return false;
				}
			}
			delay(1200);
			return false;
		}
		return false;
	}
	/**
	 * check the effects of the card played by the player, then it notifies the observers the effects
	 *
	 * @param chosenCard The card played by the player
	 */
	protected void checkEffects(Card chosenCard)
	{
		setChanged();
		try
		{
			switch (chosenCard.getValue())
			{
				case PLUSTWO ->
				{
					plus2 += 1;
					stop = 1;
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.PLUSTWO));
					delay(2520);
				}
				case PLUSFOUR ->
				{
					plus4 += 1;
					stop = 1;
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.PLUSFOUR));
					delay(2520);
				}
				case REVERSE ->
				{
					turnOrder.reverse();
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.REVERSE));
					delay(2080);
				}
				case STOP ->
				{
					stop += 1;
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.STOP));
					delay(2080);
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
	/**
	 * check if the player has won the match,
	 * it updates all the points of the players and notify the observers
	 *
	 * @return true if the player has won the match else false
	 */
	protected boolean checkPoints()
	{
		if (!stopEarlier)
			return winner.getPoints() >= 500;
		else return true;
	}

	/**
	 * apply the effects of the cards played by the player in the previous turn
	 *
	 * @param currentPlayer The player who should play
	 * @return The player who should play in case of a skip effect else the same player passed
	 */
	protected Player applyEffects(Player currentPlayer)
	{
		if (plus2 != 0)
		{
			currentPlayer.draw(2 * plus2);
		} else if (plus4 != 0)
		{
			currentPlayer.draw(4 * plus4);
		}
		if (stop != 0)
		{
			Player next = null;
			for (int i = 0; i < stop; i++)
			{
				next = turnOrder.nextPlayer();
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
			}

			return next;
		}
		return currentPlayer;
	}

	/**
	 * it will be executed at the start of the Game,
	 * it resets all the variables and notify the observers
	 */
	protected void resetGame()
	{
		stopEarlier = false;
		turnOrder.resetGame();
		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.GAMEFLOW, BuildMP.Gameflow.STARTGAME));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
	}
	/**
	 * it will be executed at the start of the Match,
	 * it resets all the variables and notify the observers
	 */
	protected void resetMatch()
	{
		DRAW_PILE.reset();
		DISCARD_PILE.reset();
		turnOrder.resetMatch();
		winner = null;

		DISCARD_PILE.discard(DRAW_PILE.draw());
		if (DISCARD_PILE.getFirst() instanceof WildCard wildCard) wildCard.setColor(Card.Color.RED);

		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DISCARD, null, DISCARD_PILE.getFirst()));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
	}
	/**
	 * it will be executed at the start of the Turn,
	 * it resets all the variables and notify the observers
	 */
	protected void resetTurn()
	{
		plus2 = 0;
		plus4 = 0;
		stop = 0;
	}

	/**
	 * make the Thread sleep for a certain amount of time
	 *
	 * @param millis The amount of time to sleep
	 */
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

	/**
	 * it will be executed when the Thread is started,
	 * it launches the game
	 */
	@Override
	public void run()
	{
		System.out.println("THREAD NATO");
		startGame();
		System.out.println("THREAD MORTO");
	}
}
