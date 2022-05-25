package org.juno.model.deck;

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
        RED(0),
        BLUE(1),
        GREEN(2),
        YELLOW(3),
        BLACK(4);

        private final int val;

        Color(int val)
        {
            this.val=val;
        }

        public int getVal()
        {
            return val;
        }
    }


    protected Color color;
    protected final Value value;

    /**
     * Constructor
     * @param color The card color
     * @param value The card value
     */
    public Card(Color color, Value value)
    {
        this.color = color;
        this.value = value;
    }

    //Getters

    /**
     * Returns the Card color
     * @return the Card color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Returns the Card value
     * @return the Card value
     */
    public Value getValue()
    {
        return value;
    }

    /**
     * Returns the couple value, color of the card
     * @return The couple value, color
     */
    public String toString()
    {
        return String.format("%s %s", value, color);
    }

    /**
     * Check if the current card can be played on the given card
     * @param c The card u want to play on
     * @return True if valid else False
     */
    public boolean isValid(Card c)
    {
        return color.equals(Color.BLACK) || color.equals(c.getColor()) || value.equals(c.getValue());
    }
}