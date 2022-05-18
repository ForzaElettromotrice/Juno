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

        private void addExp(int e)
        {
            exp += e;
            checkLevel();
        }

        private void checkLevel()
        {
            if (exp >= (4 + lvl) * 500) lvl++;
        }
    }

    public User()
    {
        level = new Level();
        load();
    }

    /**
     * Getters
     */
    public String getNickname()
    {
        return nickname;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public int getVictories()
    {
        return victories;
    }

    public int getDefeats()
    {
        return defeats;
    }

    public int getTotalMatches()
    {
        return defeats + victories;
    }

    /**
     * Setters
     */
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public void addVictories()
    {
        victories++;
    }

    public void addDefeats()
    {
        defeats++;
    }

    /**
     * Load and Save:
     * <p>
     * 1: nickname;
     * <p>
     * 2: avatar;
     * <p>
     * 3: victories;
     * <p>
     * 4: defeats;
     * <p>
     * 5: lvl;
     * <p>
     * 6: exp.
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

    public void addExp(int e)
    {
        level.addExp(e);
    }
}