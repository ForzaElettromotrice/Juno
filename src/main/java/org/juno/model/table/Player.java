package org.juno.model.table;

import org.juno.model.deck.Card;
import org.juno.model.deck.DrawPile;
import org.juno.model.deck.WildCard;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * Defines: Player class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class Player
{
	protected static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();

	protected final LinkedList<Card> hand;
	protected int points;

	protected Card chosenCard;
	protected boolean hasPassed;
	protected boolean saidUno;

	public Player()
	{
		hand = new LinkedList<>();
	}

	public int getSizeHand()
	{
		return hand.size();
	}

	public void draw(int n)
	{
		for (int i = 0; i < n; i++)
		{
			hand.add(DRAW_PILE.draw());
		}

		sort();
	}

	protected void sort()
	{
		hand.sort(Comparator.comparingInt(o -> o.getValue().getVal()));
	}

	public boolean hasChosen()
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

	public boolean saidUno()
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
}
