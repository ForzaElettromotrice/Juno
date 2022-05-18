package org.juno.model.deck;

/**
 * Defines: WildCard class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class WildCard extends Card
{
    public WildCard(Color color, Value value)
    {
        super(color, value);
    }

    public void setColor(Color c)
    {
        color = c;
    }
}
