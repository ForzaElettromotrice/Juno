package org.juno.model.table;

/**
 * Defines: CircularLinkedList class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TurnOrder
{
	private static final TurnOrder INSTANCE = new TurnOrder();
	Player[] players = new Player[4];

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
}
