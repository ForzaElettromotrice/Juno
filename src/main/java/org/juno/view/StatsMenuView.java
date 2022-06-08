package org.juno.view;

/**
 * Defines: StatsMenuView, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StatsMenuView extends GenView
{
    private static final StatsMenuView INSTANCE = new StatsMenuView();

	private StatsMenuView()
    {

    }

    public static StatsMenuView getINSTANCE()
    {
        return INSTANCE;
    }
}

