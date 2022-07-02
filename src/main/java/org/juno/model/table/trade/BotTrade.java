package org.juno.model.table.trade;

import org.juno.datapackage.BuildMP;
import org.juno.model.deck.Card;

import java.util.Random;


/**
 * Defines BotTrade class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class BotTrade extends PlayerTrade
{
	private static final Random RANDOMIZER = new Random();

	public BotTrade(int i)
	{
		super(i);
	}


	@Override
	public boolean hasChosen()
	{
		Card first = DISCARD_PILE.getFirst();
		for (Card card : hand)
		{
			if (card.isValid(first))
			{
				chosenCard = card;
				hand.remove(card);
				break;
			}
		}
		if (chosenCard == null)
		{
			Card drawn = draw();
			if (drawn.isValid(first))
			{
				chosenCard = drawn;
				hand.remove(drawn);
			} else
			{
				hasPassed = true;
				return false;
			}
		}
		chooseColor(Card.Color.RED);
		return true;
	}

	@Override
	public boolean saidUno()
	{
		if (RANDOMIZER.nextInt(100) > 20) sayUno();
		return super.saidUno();
	}

	@Override
	public boolean readyToTrade()
	{
		targetTrade = BuildMP.PG.PLAYER;
		return true;
	}
}
