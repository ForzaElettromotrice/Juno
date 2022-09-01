package org.juno.model.table.trade;

import org.juno.datapackage.BuildMP;
import org.juno.model.deck.Card;
import org.juno.model.table.Player;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Defines PlayerTrade class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class PlayerTrade extends Player
{

	protected BuildMP.PG targetTrade;

	/**
	 * Constructor, initiate the player
	 *
	 * @param i The id of the player
	 */
	public PlayerTrade(int i)
	{
		super(i);
	}
	/**
	 * @return if the player has choosen his target trade
	 */
	public boolean readyToTrade()
	{
		if (getSizeHand() == 0)
			return true;
		return targetTrade != null;
	}

	/**
	 * @return the target trade of the player
	 */
	public BuildMP.PG getTargetTrade()
	{
		return targetTrade;
	}
	/**
	 * @return the hand of the player
	 */
	public Collection<Card> getHand()
	{
		return new LinkedList<>(hand);
	}

	/**
	 * set the hand of the player to the hand given in input
	 *
	 * @param newHand the new hand of the player
	 */
	public void setHand(Collection<Card> newHand)
	{
		hand.clear();
		hand.addAll(newHand);
	}
	/**
	 * set the target trade of the player to the target trade given in input
	 *
	 * @param target the target trade of the player
	 */
	public void setTargetTrade(BuildMP.PG target)
	{
		targetTrade = target;
	}

	/**
	 * reset the player to its initial state of a turn
	 */
	@Override
	public void resetTurn()
	{
		super.resetTurn();
		targetTrade = null;
	}


}
