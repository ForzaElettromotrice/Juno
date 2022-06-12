package org.juno.model.user;

/**
 * Defines DataCorruptedException exception,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class DataCorruptedException extends Exception
{
	public DataCorruptedException(String errorMessage)
	{
		super(errorMessage);
	}
}
