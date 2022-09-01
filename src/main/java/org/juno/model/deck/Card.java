package org.juno.model.deck;

import java.io.File;
import java.util.Objects;

/**
 * Defines: Card Class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Card
{
    public enum Value
    {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        PLUSTWO(10),
        REVERSE(11),
        STOP(12),
        JOLLY(13),
        PLUSFOUR(14);

        private final int val;

        Value(int val)
        {
            this.val=val;
        }

        public int getVal()
        {
            return val;
        }
    }

    public enum Color
    {
        RED(0, "r"),
        BLUE(1, "b"),
        GREEN(2, "g"),
        YELLOW(3, "y"),
        BLACK(4, "");

        private final int val;
        private final String string;

        Color(int val, String s)
        {
            this.val = val;
            string = s;
        }

        public int getVal()
        {
            return val;
        }

        @Override
        public String toString()
        {
            return string;
        }
    }


    protected Color color;
    protected final Value value;
    protected final File url;

    /**
     * Constructor
     *
     * @param color The card color
     * @param value The card value
     */
    public Card(Color color, Value value)
    {
        this.color = color;
        this.value = value;
        url = new File(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), color.toString(), value.getVal()));
    }

    //Getters

    /**
     * @return the Card color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * @return the Card value
     */
    public Value getValue()
    {
        return value;
    }

    /**
     * @return the Card url
     */
    public File getUrl()
    {
        return url;
    }
    /**
     * @return the final url of the card
     */
    public File getFinalUrl()
    {
        return getUrl();
    }

    /**
     * @return The couple value, color as a string
     */
    public String toString()
    {
        return String.format("%s %s", value, color);
    }

    /**
     * Check if the current card can be played on the given card
     *
     * @param c The card u want to play on
     * @return True if valid else False
     */
    public boolean isValid(Card c)
    {
        return color.equals(Color.BLACK) || color.equals(c.getColor()) || value.equals(c.getValue());
    }

    /**
     * Check if the given Obect is equal to the current card
     *
     * @param toCompare The object to compare
     * @return True if equal else False
     */
    @Override
    public boolean equals(Object toCompare)
    {
        if (toCompare instanceof Card card)
        {
            return this.hashCode() == card.hashCode();
        } else return false;
    }

    /**
     * @return The hashcode of the card
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(getColor(), getValue());
    }
}