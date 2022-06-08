package org.juno.view;

/**
 * Defines StartMenuView class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class StartMenuView extends GenView
{
	private static final StartMenuView INSTANCE = new StartMenuView();

	private StartMenuView()
	{

	}

	public static StartMenuView getINSTANCE()
	{
		return INSTANCE;
	}
}
