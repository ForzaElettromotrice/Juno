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

	/**
	 * Constructor, initiate the bot
	 *
	 * @param i The bot id
	 */
	public BotCombo(int i)
	{
		super(i);
	}

	/**
	 * Choose a Card to play, if the card is a WildCard, choose a color
	 * if it can't play, draws card until it can play it if it can
	 *
	 * @return true
	 */
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
		for (Card card : hand)
		{
			if (card.getValue() == chosenCard.getValue())
			{
				combo = true;
				return true;
			}
		}
		combo = false;
		return true;
	}

	/**
	 * Say UNO if the bot has only one card with a percentage of 20%
	 *
	 * @return true if the bot said UNO, false otherwise
	 */
	@Override
	public boolean saidUno()
	{
		if (RANDOMIZER.nextInt(100) > 20) sayUno();
		return super.saidUno();
	}

}
