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

	/**
	 * Constructor, initiate the player with an ID
	 *
	 * @param n The given id
	 */
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

	/**
	 * @return the id of the player
	 */
	public BuildMP.PG getId()
	{
		return id;
	}
	/**
	 * @return the size of the hand of the player
	 */
	public int getSizeHand()
	{
		return hand.size();
	}
	/**
	 * @return the card that the player has chosen to play
	 */
	public Card getChosenCard()
	{
		return chosenCard;
	}
	/**
	 * @return true if the player has chosen a card to play else false
	 */
	public boolean hasChosen()
	{
		return chosenCard != null;
	}
	/**
	 * @return true if the player has passed else false
	 */
	public boolean hasPassed()
	{
		return hasPassed;
	}
	/**
	 * @return true if the player said UNO else false
	 */
	public boolean saidUno()
	{
		return saidUno;
	}
	/**
	 * @return the points of the player
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * Choose a card to play from the given values
	 *
	 * @param color The given color
	 * @param value The given value
	 */
	public void chooseCard(Card.Color color, Card.Value value)
	{
		Card first = DISCARD_PILE.getFirst();
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
	/**
	 * Choose the color of the wild card from the given color
	 *
	 * @param color The given color
	 */
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
	/**
	 * set the player as if he had passed
	 */
	public void setHasPassed()
	{
		hasPassed = true;
	}
	/**
	 * set the player as if he said UNO
	 */
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
	/**
	 * Draw a card from the draw pile
	 *
	 * @return the card drawn
	 */
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

		delay(500);

		return card;
	}

	/**
	 * Draw cards from the draw pile
	 *
	 * @param n The number of cards to draw
	 */
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
			delay(250);
		}
	}

	/**
	 * Add points to the player
	 *
	 * @param n The number of points to add
	 */
	public void addPoints(int n)
	{
		points += n;
	}
	/**
	 * Calculate the points of the player in his hand
	 *
	 * @return the points that the player has in his hand
	 */
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

	/**
	 * Reset the player for the next game
	 */
	public void resetGame()
	{
		points = 0;
	}
	/**
	 * Reset the player for the next Match
	 */
	public void resetMatch()
	{
		hand.clear();
	}
	/**
	 * Reset the player for the next turn
	 */
	public void resetTurn()
	{
		chosenCard = null;
		hasPassed = false;
		hasDrawn = false;
		saidUno = false;
	}

	/**
	 * Make the Thread sleep for the given time
	 *
	 * @param millis The time to sleep
	 */
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
