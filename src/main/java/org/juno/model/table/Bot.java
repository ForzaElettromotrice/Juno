package org.juno.model.table;

import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.WildCard;

import java.util.Random;

/**
 * Defines: Bot class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class Bot extends Player
{
	private static final DiscardPile DISCARD_PILE = DiscardPile.getINSTANCE();
	private static final Random RANDOMIZER = new Random();


	public Bot(int n)
	{
		super(n);
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
				break;
			}
		}
		if (chosenCard == null)
		{
			Card drawn = draw();
			if (drawn.isValid(first))
				chosenCard = drawn;
			else
			{
				hasPassed = true;
				return false;
			}
		}

		if (chosenCard instanceof WildCard)
			chooseColor(Card.Color.RED);

		return true;
	}

	@Override
	public boolean saidUno() throws MessagePackageTypeNotExistsException
	{
		if (RANDOMIZER.nextInt(100) > 20)
		{
			sayUno();
		}
		return super.saidUno();
	}

	public Card draw()
	{
		Card card = DRAW_PILE.draw();
		hand.add(card);
		sort();
		return card;
	}
}
