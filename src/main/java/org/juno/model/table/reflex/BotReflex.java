package org.juno.model.table.reflex;


import org.juno.model.deck.Card;

import java.util.Random;

/**
 * Defines BotReflex class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class BotReflex extends PlayerReflex
{
	private static final Random RANDOMIZER = new Random();

	public BotReflex(int i)
	{
		super(i);
	}

	@Override
	public boolean hasJumped()
	{
		Card first = DISCARD_PILE.getFirst();

		for (Card card : hand)
		{
			if (card.getColor() != Card.Color.BLACK && card.equals(first) && RANDOMIZER.nextInt(100) > 40)
			{
				chosenCard = card;
				hand.remove(card);
				return true;
			}
		}
		return false;
	}

}
