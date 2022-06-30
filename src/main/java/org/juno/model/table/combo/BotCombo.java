package org.juno.model.table.combo;


import org.juno.model.deck.Card;

import java.util.Random;

/**
 * Defines BotReflex class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class BotCombo extends PlayerCombo
{
	private static final Random RANDOMIZER = new Random();

	public BotCombo(int i)
	{
		super(i);
	}

	@Override
	public boolean hasChosen()
	{

		Card first = DISCARD_PILE.getFirst();
		for (Card card : hand)
		{
			if (combo ? card.getColor() != Card.Color.BLACK && card.getValue() == first.getValue() : card.isValid(first))
			{
				chosenCard = card;
				hand.remove(card);
				break;
			}
		}
		if (chosenCard == null && !combo)
		{
			Card drawn = draw();

			chosenCard = drawn;
			hand.remove(drawn);
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
