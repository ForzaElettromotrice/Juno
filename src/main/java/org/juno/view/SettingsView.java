package org.juno.view;

/**
 * Defines SettingsView class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class SettingsView extends GenView
{
	private static final SettingsView INSTANCE = new SettingsView();

	private SettingsView()
	{

	}

	public static SettingsView getINSTANCE()
	{
		return INSTANCE;
	}
}
