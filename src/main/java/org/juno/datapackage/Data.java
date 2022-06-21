package org.juno.datapackage;

import java.util.Map;

/**
 * Defines Data interface,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public interface Data
{
	<T> Map<String, T> getData();
}
