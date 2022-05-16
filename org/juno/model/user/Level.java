package org.juno.model.user;

/**
 * Defines: Level class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Level
{
    private int lvl;
    private long exp;

    public int getLevel()
    {
        return lvl;
    }

    public long getExp()
    {
        return exp;
    }

    protected void setLevel(int lvl)
    {
        this.lvl = lvl;
    }

    protected void setExp(long exp)
    {
        this.exp = exp;
    }

    public void load()
    {
        //TODO: carica l'exp dal file di testo
    }

    public void save()
    {
        //TODO: salva l'exp corrente nel file di testo
    }

    public void addExp(long e)
    {
        exp += e;
        //TODO: scrivere l'algoritmo che determina gli exp da raggiungere per ogni livello
        // e fare il check se sei salito
    }
}
