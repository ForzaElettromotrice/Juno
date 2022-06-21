package org.juno.model.table;

import org.juno.datapackage.BuildMP;
import org.juno.datapackage.Data;
import org.juno.datapackage.MessagePackageTypeNotExistsException;
import org.juno.model.deck.Card;
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
	protected static final DrawPile DRAW_PILE = DrawPile.getINSTANCE();
	protected static final BuildMP BUILD_MP = BuildMP.getINSTANCE();

	private final BuildMP.PG ID;

	protected final LinkedList<Card> hand;
	protected int points;

	protected Card chosenCard;
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
		for (int i = 0; i < n; i++)
		{
			Card card = DRAW_PILE.draw();
			hand.add(card);
			System.out.println("MESSAGGINO");

			System.out.println(card.getValue());
			System.out.println(card.getColor());
			setChanged();

			notifyObservers(BUILD_MP.createMP(BuildMP.Actions.DRAW, ID, card.getColor(), card.getValue()));
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
