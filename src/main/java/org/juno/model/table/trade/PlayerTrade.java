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

	public PlayerTrade(int i)
	{
		super(i);
	}
	public boolean readyToTrade()
	{
		return targetTrade != null;
	}

	public BuildMP.PG getTargetTrade()
	{
		return targetTrade;
	}
	public Collection<Card> getHand()
	{
		return new LinkedList<>(hand);
	}

	public void setHand(Collection<Card> newHand)
	{
		hand.clear();
		hand.addAll(newHand);
	}
	public void setTargetTrade(BuildMP.PG target)
	{
		targetTrade = target;
	}


	@Override
	public void resetTurn()
	{
		super.resetTurn();
		targetTrade = null;
	}


}
