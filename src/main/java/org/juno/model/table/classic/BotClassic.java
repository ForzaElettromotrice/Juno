package org.juno.model.table.classic;


import org.juno.model.deck.Card;

import java.util.Random;

/**
 * Defines BotClassic class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class BotClassic extends PlayerClassic
{
	private static final Random RANDOMIZER = new Random();

	public BotClassic(int i)
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
}
