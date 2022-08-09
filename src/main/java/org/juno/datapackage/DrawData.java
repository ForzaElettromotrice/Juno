package org.juno.datapackage;

import org.juno.model.deck.Card;


/**
 * Defines DrawData record,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public record DrawData(BuildMP.PG player, Card card) implements Data
{
}
