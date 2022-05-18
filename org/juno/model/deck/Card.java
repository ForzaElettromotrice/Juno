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

    public Card(Color color, Value value)
    {
        this.color = color;
        this.value = value;
    }

    public Color getColor()
    {
        return color;
    }

    public Value getValue()
    {
        return value;
    }

    public String toString()
    {
        return String.format("%s %s", value, color);
    }

    public boolean isValid(Card c)
    {
        return color.equals(Color.BLACK) || color.equals(c.getColor()) || value.equals(c.getValue());
    }
}
