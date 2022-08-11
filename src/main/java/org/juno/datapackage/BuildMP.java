package org.juno.datapackage;

import org.juno.model.deck.Card;
import org.juno.model.table.Player;

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
		JUMPIN,
		ONECARD
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

	private BuildMP()
	{
	}

	public static BuildMP getINSTANCE()
	{
		return INSTANCE;
	}

	public Data createMP(Actions action, int playerPoints, int bot1Points, int bot2Points, int bot3Points) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.POINTS) return new PointsData(playerPoints, bot1Points, bot2Points, bot3Points);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}

	public Data createMP(Actions action, PG player, Card card) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.DRAW) return new DrawData(player, card);
		else if (action == Actions.DISCARD) return new DiscardData(player, card);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	public Data createMP(Actions action, Effects effect) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.EFFECTS) return new EffectData(effect);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	public Data createMP(Actions action, Gameflow gf) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.GAMEFLOW) return new GameflowData(gf);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	public Data createMP(Actions action, Colors color) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.COLOR) return new ColorData(color);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	public Data createMP(Actions action, PG player) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.TURN) return new TurnData(player);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	public Data createMP(Actions actions, Collection<Card> newHand, PG fromPg, PG toPg) throws MessagePackageTypeNotExistsException
	{
		if (actions == Actions.SWITCH) return new SwitchData(newHand, fromPg, toPg);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
}