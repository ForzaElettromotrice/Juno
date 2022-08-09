package org.juno.controller;

import org.juno.datapackage.*;

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
	void doSwitch(SwitchData switchData);
	void gameflow(GameflowData gameflowData);
	void getPoints(PointsData pointsData);
}
