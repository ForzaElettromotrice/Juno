package org.juno.datapackage;

import org.juno.model.deck.Card;

import java.util.Collection;

/**
 * Defines MessagePackage class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class BuildMP
{
	private static final BuildMP INSTANCE = new BuildMP();
	private static final String ERROR_MESSAGE = "Questo tipo di MP non esiste!";

	public enum Actions
	{
		TURN,
		DRAW,
		DISCARD,
		SWITCH,
		EFFECTS,
		JUMP,
		GAMEFLOW,
		COLOR,
		POINTS,
		EXIT
	}

	public enum PG
	{
		PLAYER,
		BOT1,
		BOT2,
		BOT3
	}

	public enum Effects
	{
		STOP,
		REVERSE,
		PLUSTWO,
		PLUSFOUR,
		JOLLY,
		SAIDUNO,
		DIDNTSAYUNO,
		ONECARD,
		RED,
		BLUE,
		YELLOW,
		GREEN
	}

	public enum Colors
	{
		RED,
		YELLOW,
		GREEN,
		BLUE
	}

	public enum Gameflow
	{
		STARTGAME,
		ENDMATCH,
		ENDGAME
	}

	/**
	 * Constructor of BuildMP class. It is private because it is a singleton.
	 */
	private BuildMP()
	{
	}

	/**
	 * @return the instance of BuildMP class.
	 */
	public static BuildMP getINSTANCE()
	{
		return INSTANCE;
	}

	/**
	 * Builds a MessagePackage for the view.
	 *
	 * @param action       the action to be performed.
	 * @param playerPoints the points of the player.
	 * @param bot1Points   the points of the bot1.
	 * @param bot2Points   the points of the bot2.
	 * @param bot3Points   the points of the bot3.
	 * @return the MessagePackage.
	 * @throws MessagePackageTypeNotExistsException if the action is not valid.
	 */
	public Data createMP(Actions action, int playerPoints, int bot1Points, int bot2Points, int bot3Points) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.POINTS) return new PointsData(playerPoints, bot1Points, bot2Points, bot3Points);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}

	/**
	 * Builds a MessagePackage for the view.
	 *
	 * @param action the action to be performed.
	 * @param player the player.
	 * @param card   the card played or drawn.
	 * @return the MessagePackage.
	 * @throws MessagePackageTypeNotExistsException if the action is not valid.
	 */
	public Data createMP(Actions action, PG player, Card card) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.DRAW) return new DrawData(player, card);
		else if (action == Actions.DISCARD) return new DiscardData(player, card);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	/**
	 * Builds a MessagePackage for the view.
	 *
	 * @param action the action to be performed.
	 * @param effect the effect to be performed.
	 * @return the MessagePackage.
	 * @throws MessagePackageTypeNotExistsException if the action is not valid.
	 */
	public Data createMP(Actions action, Effects effect) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.EFFECTS) return new EffectData(effect);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	/**
	 * Builds a MessagePackage for the view.
	 *
	 * @param action the action to be performed.
	 * @param gf     the gameflow to be performed.
	 * @return the MessagePackage.
	 * @throws MessagePackageTypeNotExistsException if the action is not valid.
	 */
	public Data createMP(Actions action, Gameflow gf) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.GAMEFLOW) return new GameflowData(gf);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	/**
	 * Builds a MessagePackage for the view.
	 *
	 * @param action the action to be performed.
	 * @param color  the color to be played.
	 * @return the MessagePackage.
	 * @throws MessagePackageTypeNotExistsException if the action is not valid.
	 */
	public Data createMP(Actions action, Colors color) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.COLOR) return new ColorData(color);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	/**
	 * Builds a MessagePackage for the view.
	 *
	 * @param action the action to be performed.
	 * @param player the player who is playing.
	 * @return the MessagePackage.
	 * @throws MessagePackageTypeNotExistsException if the action is not valid.
	 */
	public Data createMP(Actions action, PG player) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.TURN) return new TurnData(player);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	/**
	 * Builds a MessagePackage for the view.
	 *
	 * @param actions the actions to be performed.
	 * @param newHand the new hand of the player.
	 * @param fromPg  who started the trade.
	 * @param toPg    who is receiving the cards.
	 * @return the MessagePackage.
	 * @throws MessagePackageTypeNotExistsException if the action is not valid.
	 */
	public Data createMP(Actions actions, Collection<Card> newHand, PG fromPg, PG toPg) throws MessagePackageTypeNotExistsException
	{
		if (actions == Actions.SWITCH) return new SwitchData(newHand, fromPg, toPg);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
}