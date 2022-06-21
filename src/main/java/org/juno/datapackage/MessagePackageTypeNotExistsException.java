package org.juno.datapackage;

/**
 * Defines MessagePackageTypeNotExistsException exception,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class MessagePackageTypeNotExistsException extends Exception
{
	public MessagePackageTypeNotExistsException(String errorMessage)
	{
		super(errorMessage);
	}
}
