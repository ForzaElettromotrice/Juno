package org.juno.datapackage;

/**
 * Defines MessagePackage class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class MessagePackage
{

	public Actions action;
	public Data data;

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

	public MessagePackage(Actions action, Data data)
	{
		this.action = action;
		this.data = data;
	}
}
