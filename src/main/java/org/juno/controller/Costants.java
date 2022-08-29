package org.juno.controller;

/**
 * Defines Costants enum,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public enum Costants
{
	CARD_WIDTH_SCALED(189),
	CARD_HEIGHT_SCALED(264),

	PARENT_PLAYER_CENTER_X(865.5),
	PARENT_BOT1_CENTER_X(37),
	PARENT_BOT2_CENTER_X(865.5),
	PARENT_BOT3_CENTER_X(1693.5),

	PARENT_PLAYER_CENTER_Y(816),
	PARENT_BOT1_CENTER_Y(408),
	PARENT_BOT2_CENTER_Y(0),
	PARENT_BOT3_CENTER_Y(408),

	PARENT_PLAYER_X(393),
	PARENT_BOT1_X(-340.5),
	PARENT_BOT2_X(487.5),
	PARENT_BOT3_X(1315.5),

	PARENT_PLAYER_Y(816),
	PARENT_BOT1_Y(408),
	PARENT_BOT2_Y(0),
	PARENT_BOT3_Y(408);

	private final double val;

	Costants(double n)
	{
		val = n;
	}

	public double getVal()
	{
		return val;
	}
}
