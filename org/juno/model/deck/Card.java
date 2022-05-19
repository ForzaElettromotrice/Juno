package org.juno.model.deck;

/**
 * Defines: Card Class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Card
{
    protected enum Value
    {
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        PLUSTWO,
        REVERSE,
        STOP,
        JOLLY,
        PLUSFOUR;
    }

    protected enum Color
    {
        RED,
        BLUE,
        GREEN,
        YELLOW,
        BLACK;
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
