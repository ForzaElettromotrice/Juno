package org.juno.model.deck;

/**
 * Defines: WildCard class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class WildCard extends Card
{
    /**
     * Constructor
     * @param value The Card value
     */
    public WildCard(Value value)
    {
        super(Color.BLACK, value);
    }

    /**
     * Set the Card color
     * @param c Color to set
     */
    public void setColor(Color c)
    {
        color = c;
    }
}
