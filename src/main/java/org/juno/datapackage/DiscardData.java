package org.juno.datapackage;

import org.juno.model.deck.Card;

/**
 * Defines DiscardData record,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public record DiscardData(BuildMP.PG player, Card card) implements Data
{
}
