package org.juno.model.user;

import java.io.*;

/**
 * Defines: User class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class User
{
    private String nickname;
    private String avatar;
    private int victories;
    private int defeats;
    private final Level level;


    private static class Level
    {
        private int lvl;
        private int exp;

        private Level()
        {
        }

        /**
         * Add the given value to the partial exp and then check if u reached the next level
         * @param e experience gained
         */
        private void addExp(int e)
        {
            exp += e;
            checkLevel();
        }

        /**
         * Check if u reached the next level
         */
        private void checkLevel()
        {
            if (exp >= (4 + lvl) * 500)
            {
                lvl++;
                exp = 0;
            }
        }
    }

    /**
     * Create the Level and then load from User.txt the data
     */
    public User()
    {
        level = new Level();
        load();
    }

    //Getters

    /**
     * Returns the current nickname
     * @return nickname
     */
    public String getNickname()
    {
        return nickname;
    }

    /**
     * Returns the curret avatar path
     * @return avatar path
     */
    public String getAvatar()
    {
        return avatar;
    }

    /**
     * Returns the current total wins
     * @return total wins
     */
    public int getVictories()
    {
        return victories;
    }

    /**
     * Returns the current total losses
     * @return total losses
     */
    public int getDefeats()
    {
        return defeats;
    }

    /**
     * Returns the total matches played
     * @return total matches
     */
    public int getTotalMatches()
    {
        return defeats + victories;
    }

    /**
     * Returns the total exp
     * @return total exp
     */
    public int getTotalExp()
    {
        return getTotalExp(level.lvl, level.exp);
    }

    /**
     * Calculates total exp based on partial exp
     * @param level -> current level
     * @param exp -> partial exp
     * @return total exp
     */
    public int getTotalExp(int level, int exp)
    {
        if (level==1)
            return exp;
        return exp+getTotalExp((4+level-1)*500, level-1);
    }

    /**
     * Return partial exp
     * @return partial exp
     */
    public int getExp()
    {
        return level.exp;
    }

    /**
     * Returns the current level
     * @return current level
     */
    public int getLevel()
    {
        return level.lvl;
    }

    //Setters

    /**
     * Set the current nickname to the given value
     * @param nickname the new nickname
     */
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    /**
     * Set the current avatar path to the given value
     * @param avatar the new avatar path
     */
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    /**
     * Increments the wins by 1
     */
    public void addVictories()
    {
        victories++;
    }

    /**
     * Increments the losses by 1
     */
    public void addDefeats()
    {
        defeats++;
    }


    /**
     * Loads from User.txt the data ->
     * <p>
     * 1 Line: nickname;
     * <p>
     * 2 Line: avatar;
     * <p>
     * 3 Line: victories;
     * <p>
     * 4 Line: defeats;
     * <p>
     * 5 Line: lvl;
     * <p>
     * 6 Line: exp.
     */
    public void load()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("assets/data/user.txt")))
        {
            nickname = br.readLine();
            avatar = br.readLine();
            victories = Integer.parseInt(br.readLine());
            defeats = Integer.parseInt(br.readLine());
            level.lvl = Integer.parseInt(br.readLine());
            level.exp = Integer.parseInt(br.readLine());
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            level.checkLevel();
        }
    }

    /**
     * Save on User.txt the data ->
     * <p>
     * 1 Line: nickname;
     * <p>
     * 2 Line: avatar;
     * <p>
     * 3 Line: victories;
     * <p>
     * 4 Line: defeats;
     * <p>
     * 5 Line: lvl;
     * <p>
     * 6 Line: exp.
     */
    public void save()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("assets/data/user.txt")))
        {
            bw.write(String.format("%s%n%s%n%d%n%d%n%d%n%d", nickname, avatar, victories, defeats,
                    level.lvl, level.exp));
        } catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Add the given Exp to the total exp
     * @param e Exp gained
     */
    public void addExp(int e)
    {
        level.addExp(e);
    }
}