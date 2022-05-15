package juno.model.user;

/**
 * Defines: Level class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class Level
{
    private int level;
    private long exp;

    public int getLevel()
    {
        return level;
    }

    public long getExp()
    {
        return exp;
    }

    protected void setLevel(int level)
    {
        this.level = level;
    }

    protected void setExp(long exp)
    {
        this.exp = exp;
    }

    public void load()
    {
        //TODO: load
    }

    public void save()
    {
        //TODO: save
    }

    public void addExp(long e)
    {
        exp += e;
        //TODO: level based on exp
    }
}
