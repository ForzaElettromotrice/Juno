package org.juno.datapackage;

import org.juno.model.deck.Card;

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
		SWITCH0,
		SWITCH7,
		EFFECTS,
		JUMP
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
		RESETMATCH

	}

	private BuildMP()
	{
	}

	public static BuildMP getINSTANCE()
	{
		return INSTANCE;
	}

	public Data createMP(Actions action, PG player, Card.Color color, Card.Value value) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.DRAW) return new DrawData(player, color, value);
		else if (action == Actions.DISCARD) return new DiscardData(player, color, value);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	public Data createMP(Actions action, Effects effect) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.EFFECTS) return new EffectData(effect);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
	public Data createMP(Actions action, PG player) throws MessagePackageTypeNotExistsException
	{
		if (action == Actions.TURN) return new TurnData(player);
		else throw new MessagePackageTypeNotExistsException(ERROR_MESSAGE);
	}
}
