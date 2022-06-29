package org.juno.model.table.reflex;

import org.juno.model.deck.Card;
import org.juno.model.table.Player;

/**
 * Defines PlayerReflex class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class PlayerReflex extends Player
{

	protected boolean jumped;


	public PlayerReflex(int i)
	{
		super(i);
	}

	public void jumpIn(Card.Color color, Card.Value value)
	{
		Card first = DISCARD_PILE.getFirst();

		for (Card card : hand)
		{
			if (card.getColor() == color && card.getValue() == value && card.equals(first))
			{
				chosenCard = card;
				hand.remove(card);
				jumped = true;
				return;
			}
		}
	}
	public boolean hasJumped()
	{
		return jumped;
	}
}
