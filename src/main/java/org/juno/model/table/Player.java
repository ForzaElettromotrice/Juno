package org.juno.model.table;

import org.juno.datapackage.BuildMP;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
import org.juno.model.deck.DiscardPile;
import org.juno.model.deck.DrawPile;
import org.juno.model.deck.WildCard;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Defines: Player class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class Player extends Observable
{
	protected static final DiscardPile DISCARD_PILE = DiscardPile.getINSTANCE();
	protected static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();
	protected static final BuildMP BUILD_MP = BuildMP.getINSTANCE();

	protected final BuildMP.PG ID;

	protected final LinkedList<Card> hand;
	protected int points;

	protected Card chosenCard;
	protected boolean hasDrawn;
	protected boolean hasPassed;
	protected boolean saidUno;

	public Player(int n)
	{
		ID = switch (n)
				{
					case 1 -> BuildMP.PG.BOT1;
					case 2 -> BuildMP.PG.BOT2;
					case 3 -> BuildMP.PG.BOT3;
					default -> BuildMP.PG.PLAYER;
				};
		hand = new LinkedList<>();
	}

	public BuildMP.PG getID()
	{
		return ID;
	}
	public int getSizeHand()
	{
		return hand.size();
	}

	public void draw(int n) throws MessagePackageTypeNotExistsException
	{
		System.out.println("IO SONO " + ID + " E STO PESCANDO " + n + " CARTE");
		for (int i = 0; i < n; i++)
		{
			Card card = DRAW_PILE.draw();
			hand.add(card);


			setChanged();
			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DRAW, ID, card.getColor(), card.getValue()));
			clearChanged();
		}

		sort();
	}


	public void chooseCard(Card.Color color, Card.Value value) throws MessagePackageTypeNotExistsException
	{
		for (Card card : hand)
		{
			if (card.getValue() == value && card.getColor() == color && card.isValid(DISCARD_PILE.getFirst()))
			{
				chosenCard = card;
				hand.remove(card);
				if (hand.size() == 1)
				{
					setChanged();
					notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.ONECARD));
					clearChanged();
				}

				break;
			}
		}
	}

	protected void sort()
	{
		hand.sort(Comparator.comparingInt(o -> o.getValue().getVal()));
	}

	public boolean hasChosen() throws MessagePackageTypeNotExistsException
	{
		return chosenCard != null;
	}

	public boolean hasPassed()
	{
		return hasPassed;
	}

	public Card getChosenCard()
	{
		return chosenCard;
	}

	public void chooseColor(Card.Color color)
	{
		if (chosenCard instanceof WildCard wildCard)
			wildCard.setColor(color);
	}

	public void sayUno() throws MessagePackageTypeNotExistsException
	{
		saidUno = true;
		notifyObservers(BUILD_MP.createMP(BuildMP.Actions.EFFECTS, BuildMP.Effects.SAIDUNO));
	}
	public boolean saidUno() throws MessagePackageTypeNotExistsException
	{
		return saidUno;
	}

	public int calculatePoints()
	{
		int out = 0;
		for (Card card : hand)
		{
			out += switch (card.getValue().getVal())
					{
						case 10, 11, 12 -> 20;
						case 13, 14 -> 50;
						default -> card.getValue().getVal();
					};
		}
		return out;
	}

	public void addPoints(int points)
	{
		this.points += points;
	}

	public int getPoints()
	{
		return points;
	}

	public void resetTurn()
	{
		chosenCard = null;
		hasPassed = false;
		saidUno = false;
		hasDrawn = false;
	}

	public void resetMatch()
	{
		hand.clear();
		resetTurn();
	}

	public void resetGame()
	{
		resetMatch();
		points = 0;
	}

	public Card draw() throws MessagePackageTypeNotExistsException
	{
		if (hasDrawn) return null;

		Card card = DRAW_PILE.draw();
		hand.add(card);
		sort();
		hasDrawn = true;

		setChanged();
		notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DRAW, ID, card.getColor(), card.getValue()));
		clearChanged();

		return card;
	}

	public boolean hasDrawn()
	{
		return hasDrawn;
	}

	public void setHasPassed()
	{
		hasPassed = true;
	}

}
