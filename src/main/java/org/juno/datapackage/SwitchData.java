package org.juno.datapackage;

import org.juno.model.deck.Card;

import java.util.Collection;

/**
 * Defines SwitchData record,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public record SwitchData(Collection<Card> newHand, BuildMP.PG fromPg, BuildMP.PG toPg) implements Data
{
}
