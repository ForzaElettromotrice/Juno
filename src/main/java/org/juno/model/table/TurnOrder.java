package org.juno.model.table;


import java.util.Random;

/**
 * Defines: CircularLinkedList class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TurnOrder
{
	private static final TurnOrder INSTANCE = new TurnOrder();
	private static final Random RANDOMIZER = new Random();

	Player[] players = new Player[4];

	private int currentPlayer;
	private boolean isInverted;

	private TurnOrder()
	{
		for (int i = 0; i < 3; i++)
		{
			players[i] = new Bot();
		}
		players[3] = new Player();
	}

	public static TurnOrder getINSTANCE()
	{
		return INSTANCE;
	}


	public void reset()
	{
		for (Player player : players)
		{
			player.reset();
		}
		isInverted = false;
		currentPlayer = RANDOMIZER.nextInt(4); // Serve per decidere randomicamente il primo giocatore
	}

	public Player nextPlayer()
	{
		currentPlayer = switch (currentPlayer)
				{
					case 0 -> isInverted ? 3 : currentPlayer + 1;
					case 3 -> isInverted ? currentPlayer - 1 : 0;
					default -> isInverted ? currentPlayer - 1 : currentPlayer + 1;
				};

		return players[currentPlayer];
	}

	public void reverseTurnOrder()
	{
		isInverted = !isInverted;
	}


}
