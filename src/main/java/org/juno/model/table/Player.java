package org.juno.model.table;

import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.DrawPile;
import org.juno.model.deck.WildCard;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Defines Player class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public abstract class Player extends Observable
{
	protected static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();
	protected static final DiscardPile DISCARD_PILE = DiscardPile.getINSTANCE();
	protected static final BuildMP BUILD_MP = BuildMP.getINSTANCE();

	protected final BuildMP.PG id;


	protected final LinkedList<Card> hand = new LinkedList<>();

	protected Card chosenCard;

	protected boolean hasPassed;
	protected boolean hasDrawn;
	protected boolean saidUno;

	protected int points;


	protected Player(int n)
	{
		id = switch (n)
				{
					case 0 -> BuildMP.PG.PLAYER;
					case 1 -> BuildMP.PG.BOT1;
					case 2 -> BuildMP.PG.BOT2;
					case 3 -> BuildMP.PG.BOT3;
					default -> null;
				};


	}


	public BuildMP.PG getId()
	{
		return id;
	}
	public int getSizeHand()
	{
		return hand.size();
	}
	public Card getChosenCard()
	{
		return chosenCard;
	}
	public boolean hasChosen()
	{
		return chosenCard != null;
	}
	public boolean hasPassed()
	{
		return hasPassed;
	}
	public boolean saidUno()
	{
		return saidUno;
	}
	public int getPoints()
	{
		return points;
	}


	public void chooseCard(Card.Color color, Card.Value value)
	{
		Card first = DISCARD_PILE.getFirst();
		System.out.println(hand);
		for (Card card : hand)
		{
			if (card.getValue() == value && card.getColor() == color && card.isValid(first))
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
	public void chooseColor(Card.Color color)
	{
		if (chosenCard instanceof WildCard wildCard)
		{
			wildCard.setColor(color);
			setChanged();
			try
			{
				notifyObservers(BUILD_MP.createMP(BuildMP.Actions.COLOR, switch (color)
						{
							case RED -> BuildMP.Colors.RED;
							case BLUE -> BuildMP.Colors.BLUE;
							case GREEN -> BuildMP.Colors.GREEN;
							case YELLOW -> BuildMP.Colors.YELLOW;
							default -> throw new MessagePackageTypeNotExistsException("Non puoi scegliere il nero!");
						}));
			} catch (MessagePackageTypeNotExistsException err)
			{
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
			clearChanged();
		}
	}
	public void setHasPassed()
	{
		hasPassed = true;
	}
	public void sayUno()
	{
		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.SAIDUNO));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
		saidUno = true;
	}
	public Card draw()
	{
		if (hasDrawn) return null;

		Card card = DRAW_PILE.draw();

		hand.add(card);
		hasDrawn = true;

		setChanged();
		try
		{
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DRAW, id, card));
		} catch (MessagePackageTypeNotExistsException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		clearChanged();
		return card;
	}


	public void draw(int n)
	{
		for (int i = 0; i < n; i++)
		{
			Card card = DRAW_PILE.draw();
			hand.add(card);
			setChanged();
			try
			{
				notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DRAW, id, card));
			} catch (MessagePackageTypeNotExistsException err)
			{
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
			clearChanged();
		}
	}

	public void addPoints(int n)
	{
		points += n;
	}
	public int calculatePoints()
	{
		int out = 0;

		for (Card card : hand)
		{
			out += switch (card.getValue())
					{
						case JOLLY, PLUSFOUR -> 50;
						case PLUSTWO, STOP, REVERSE -> 20;
						default -> card.getValue().getVal();
					};
		}
		return out;
	}


	public void resetGame()
	{
		points = 0;
	}
	public void resetMatch()
	{
		hand.clear();
	}
	public void resetTurn()
	{
		chosenCard = null;
		hasPassed = false;
		hasDrawn = false;
		saidUno = false;
	}


	protected void delay(int millis)
	{
		try
		{
			Thread.sleep(millis);
		} catch (InterruptedException err)
		{
			Thread.currentThread().interrupt();
		}
	}
}
