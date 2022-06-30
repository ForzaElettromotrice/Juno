package org.juno.model.table.combo;


import org.juno.model.table.Table;

/**
 * Defines TableReflex class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class TableCombo extends Table
{
	private static final TableCombo INSTANCE = new TableCombo();

	private TableCombo()
	{
	}


	public static TableCombo getINSTANCE()
	{
		return INSTANCE;
	}
}
