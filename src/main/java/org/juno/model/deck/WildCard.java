package org.juno.model.deck;

import java.io.File;

/**
 * Defines: WildCard class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class WildCard extends Card
{
    private File newUrl;
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
     *
     * @param c Color to set
     */
    public void setColor(Color c)
    {
        color = c;
        newUrl = new File(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), color.toString(), value.getVal()));
    }

    /**
     * @return the final URL of the card
     */
    @Override
    public File getFinalUrl()
    {
        return newUrl;
    }
}
