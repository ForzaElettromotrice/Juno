package org.juno.datapackage;


/**
 * Defines PointsData record,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public record PointsData(int playerPoints, int bot1Points, int bot2Points, int bot3Points) implements Data
{
}
