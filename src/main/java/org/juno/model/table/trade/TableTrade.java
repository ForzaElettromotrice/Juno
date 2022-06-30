package org.juno.model.table.trade;

import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;

/**
 * Defines TableTrade class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TableTrade extends Table
{
	private static final TableTrade INSTANCE = new TableTrade();


	private TableTrade()
	{
		super(new TurnOrder(TurnOrder.MODALITY.TRADE));
	}

	public static TableTrade getINSTANCE()
	{
		return INSTANCE;
	}
	@Override
	protected boolean startTurn(Player player)
	{
		return false;
	}
}
