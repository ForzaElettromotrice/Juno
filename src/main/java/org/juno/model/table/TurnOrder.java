package org.juno.model.table;

import org.juno.datapackage.BuildMP;
import org.juno.model.table.classic.BotClassic;
import org.juno.model.table.classic.PlayerClassic;
import org.juno.model.table.combo.BotCombo;
import org.juno.model.table.combo.PlayerCombo;
import org.juno.model.table.trade.BotTrade;
import org.juno.model.table.trade.PlayerTrade;

import java.util.Arrays;
import java.util.stream.IntStream;

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
		COMBO,
		TRADE
	}

	/**
	 * Constructor, initiate the turn order
	 *
	 * @param modality The given modality
	 */
	public TurnOrder(MODALITY modality)
	{
		players[0] = switch (modality)
				{
					case CLASSIC -> new PlayerClassic(0);
					case COMBO -> new PlayerCombo(0);
					case TRADE -> new PlayerTrade(0);
				};

		IntStream.range(1, 4).forEach(i -> players[i] = switch (modality)
				{
					case CLASSIC -> new BotClassic(i);
					case COMBO -> new BotCombo(i);
					case TRADE -> new BotTrade(i);
				});


	}

	/**
	 * @return the array of the players
	 */
	public Player[] getPlayers()
	{
		return players;
	}

	/**
	 * @return the current player
	 */
	public Player getCurrentPlayer()
	{
		return players[currentPlayer];
	}

	/**
	 * @return the User Player instance
	 */
	public Player getUser()
	{
		return players[0];
	}

	/**
	 * @param id The id of a player
	 * @return the player with the given id
	 */
	public Player getPlayer(BuildMP.PG id)
	{
		return switch (id)
				{
					case PLAYER -> players[0];
					case BOT1 -> players[1];
					case BOT2 -> players[2];
					case BOT3 -> players[3];
				};
	}

	/**
	 * @return the next player who has to play
	 */
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

	/**
	 * reverse the turn order
	 */
	public void reverse()
	{
		isInverted = !isInverted;
	}

	/**
	 * update the points of the winner player
	 *
	 * @param winner The winner player
	 */
	public void updatePoints(Player winner)
	{
		Arrays.stream(players).mapToInt(Player::calculatePoints).forEach(winner::addPoints);
	}

	/**
	 * reset the turn order for the next game
	 */
	public void resetGame()
	{
		Arrays.stream(players).forEach(Player::resetGame);
	}
	/**
	 * reset the turn order for the next match
	 */
	public void resetMatch()
	{
		Arrays.stream(players).forEach(player ->
		{
			player.resetMatch();
			player.draw(2);
		});
		isInverted = false;
	}


}
