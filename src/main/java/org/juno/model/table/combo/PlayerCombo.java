package org.juno.model.table.combo;

import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.table.Player;

/**
 * Defines PlayerReflex class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class PlayerCombo extends Player
{

	protected boolean combo;

	/**
	 * Constructor, initiate the player
	 *
	 * @param i the id of the player
	 */
	public PlayerCombo(int i)
	{
		super(i);
	}

	/**
	 * choose the card to play from the given values,
	 * if combo is true, the player can play a card with different conditions
	 *
	 * @param color The given color
	 * @param value The given value
	 */
	@Override
	public void chooseCard(Card.Color color, Card.Value value)
	{
		if (chosenCard != null) return;
		if (!combo)
		{
			super.chooseCard(color, value);
		} else
		{
			Card first = DISCARD_PILE.getFirst();
			for (Card card : hand)
			{
				if (card.getValue() == value && card.getColor() == color && card.getColor() != Card.Color.BLACK && card.getValue() == first.getValue())
				{
					chosenCard = card;
					hand.remove(card);
					if (hand.size() == 1)
					{
						setChanged();
						try
						{
							notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.ONECARD));
						} catch (MessagePackageTypeNotExistsException err)
						{
							System.out.println(err.getMessage());
							err.printStackTrace();
						}
						clearChanged();
					}
					break;
				}
			}
		}
		Card first = chosenCard;

		for (Card card : hand)
		{
			if (card.getColor() != Card.Color.BLACK && card.getValue() == first.getValue())
			{
				combo = true;
				return;
			}
		}
		combo = false;
	}

	/**
	 * @return true if the player can still play a card else false
	 */
	public boolean canPlay()
	{
		chosenCard = null;
		return combo;
	}

	/**
	 * draws a card from the deck until the player can play a card
	 *
	 * @return the card which the player can play
	 */
	@Override
	public Card draw()
	{
		Card first = DISCARD_PILE.getFirst();

		Card cardDrawn;

		while (true)
		{
			cardDrawn = super.draw();
			if (cardDrawn == null) return null;

			hasDrawn = false;

			if (cardDrawn.isValid(first))
			{
				hasDrawn = true;
				return cardDrawn;
			}
		}
	}

	/**
	 * reset the player to its initial state of a turn
	 */
	@Override
	public void resetTurn()
	{
		super.resetTurn();
		combo = false;
	}
}
