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

	/**
	 * Constructor, initiate the bot
	 *
	 * @param i The bot id
	 */
	public BotClassic(int i)
	{
		super(i);
	}

	/**
	 * Choose a Card to play, if the card is a WildCard, choose a color
	 * if it can't play, draw a card and play it if it can
	 *
	 * @return true if the bot played a card, false otherwise
	 */
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
			delay(750);
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
