package org.juno.controller;

import org.juno.datapackage.DiscardData;
import org.juno.datapackage.DrawData;
import org.juno.datapackage.EffectData;
import org.juno.datapackage.TurnData;

/**
 * Defines Gameplay interface,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public interface Gameplay
{
	void draw(DrawData drawData);
	void discard(DiscardData discardData);
	void turn(TurnData turnData);
	void effect(EffectData effectData);
}
