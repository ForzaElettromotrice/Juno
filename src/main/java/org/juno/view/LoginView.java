package org.juno.view;

/**
 * Defines LoginView class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class LoginView extends GenView
{
	private static final LoginView INSTANCE = new LoginView();


	private LoginView()
	{

	}

	public static LoginView getINSTANCE()
	{
		return INSTANCE;
	}
}
