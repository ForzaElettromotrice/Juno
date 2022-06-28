package org.juno.model.table;

import org.juno.model.table.reflex.BotReflex;
import org.juno.model.table.reflex.PlayerReflex;
import org.juno.model.table.trade.BotTrade;
import org.juno.model.table.trade.PlayerTrade;

/**
 * Defines TurnOrder class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TurnOrder
{
	private final Player[] players = new Player[4];
	private int currentPlayer;

	private boolean isInverted;


	public enum MODALITY
	{
		CLASSIC,
		REFLEX,
		TRADE
	}

	public TurnOrder(MODALITY modality)
	{
		players[0] = switch (modality)
				{
					case CLASSIC -> new Player(0);
					case REFLEX -> new PlayerReflex(0);
					case TRADE -> new PlayerTrade(0);
				};

		for (int i = 1; i < 4; i++)
		{
			players[i] = switch (modality)
					{
						case CLASSIC -> new Bot(i);
						case REFLEX -> new BotReflex(i);
						case TRADE -> new BotTrade(i);
					};
		}


	}


	public Player[] getPlayers()
	{
		return players;
	}

	public Player getCurrentPlayer()
	{
		return players[currentPlayer];
	}

	public Player getUser()
	{
		return players[0];
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

	public void reverse()
	{
		isInverted = !isInverted;
	}


	public void updatePoints(Player winner)
	{
		for (Player player : players)
		{
			winner.addPoints(player.calculatePoints());
		}
	}


	public void resetGame()
	{
		for (Player player : players)
		{
			player.resetGame();
		}
	}

	public void resetMatch()
	{
		for (Player player : players)
		{
			player.resetMatch();
			player.draw(2);
		}
		isInverted = false;
	}


}
