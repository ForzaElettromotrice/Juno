package org.juno.model.user;

import org.juno.view.AudioPlayer;

import java.io.*;

/**
 * Defines: User class,
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class User implements Serializable
{
    private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
    private static final String PATH = "src/main/resources/org/juno/model/user/";

    private static User instance;

    private double musicVolume;
    private double effectsVolume;


    private String nickname;
    private String avatar;
    private int victories;
    private int defeats;
    private final Level level;


    private static class Level implements Serializable
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
                exp -= (4 + lvl) * 500;
            }
        }
    }


    /**
     * Create the Level and then load from User.txt the data
     */
    private User()
    {
        level = new Level();
    }


    public static void save()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH + instance.nickname + ".txt")))
        {
            oos.writeObject(instance);

        } catch (IOException err)
        {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }
    public static User load(String name)
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH + name)))
        {
            instance = (User) ois.readObject();
        } catch (IOException | ClassNotFoundException err)
        {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }

        AUDIO_PLAYER.setMusicVolume(instance.musicVolume);
        AUDIO_PLAYER.setEffectsVolume(instance.effectsVolume);
        return instance;
    }

    //Getters
    public static User getInstance()
    {
        if (instance == null) instance = new User();
        return instance;
    }


    public double getMusicVolume()
    {
        return musicVolume;
    }

    public double getEffectsVolume()
    {
        return effectsVolume;
    }
    /**
     * Returns the current nickname
     *
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
     * Returns the total exp gained
     * @return the total exp
     */
    public int getTotalExp()
    {
        return 500 * ((4 * (level.lvl - 1)) + ((level.lvl - 1) * (level.lvl) / 2)) + level.exp;
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
     *
     * @return current level
     */
    public int getLevel()
    {
        return level.lvl;
    }

    public double getProgress()
    {
        return ((double) level.exp) / ((4 + level.lvl) * 500);
    }

    //Setters

    public void setMusicVolume(double musicVolume)
    {
        this.musicVolume = musicVolume;
    }
    public void setEffectsVolume(double effectsVolume)
    {
        this.effectsVolume = effectsVolume;
    }
    /**
     * Set the current nickname to the given value
     *
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
     * Add the given Exp to the total exp
     *
     * @param e Exp gained
     */
    public void addExp(int e)
    {
        level.addExp(e);
    }

    public void reset()
    {
        nickname = "";
        avatar = "";
        victories = 0;
        defeats = 0;
        level.lvl = 1;
        level.exp = 0;
        musicVolume = 1;
        effectsVolume = 1;
    }

    public static void main(String[] args)
    {
        User user = User.getInstance();

        user.setNickname("Eleonora");
        user.setAvatar("file:\\C:\\Users\\minga\\OneDrive\\Juno\\src\\main\\resources\\org\\juno\\images\\icon2.png");

        User.save();

    }
}