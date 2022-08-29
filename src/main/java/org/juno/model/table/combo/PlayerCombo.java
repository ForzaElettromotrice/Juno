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

	public PlayerCombo(int i)
	{
		super(i);
	}


	@Override
	public void chooseCard(Card.Color color, Card.Value value)
	{
		if (!combo)
		{
			super.chooseCard(color, value);
		} else
		{
			Card first = DISCARD_PILE.getFirst();
			for (Card card : hand)
			{
				System.out.println(value + " " + color);
				System.out.println(first);
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
			System.out.println(card);
			System.out.println(first);
			if (card.getColor() != Card.Color.BLACK && card.getValue() == first.getValue())
			{
				System.out.println("COMBO TRUE");
				combo = true;
				break;
			}
		}
	}

	public boolean canPlay()
	{
		chosenCard = null;
		return combo;
	}

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
			if (id != BuildMP.PG.PLAYER) delay(700);
		}
	}

	@Override
	public void resetTurn()
	{
		super.resetTurn();
		combo = false;
	}
}
