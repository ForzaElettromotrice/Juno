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


    /**
     * Save the data in {nickname}.txt
     */
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

    /**
     * Load the data from {nickname}.txt
     *
     * @param name nickname
     * @return the User instance loaded
     */
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
    /**
     * @return the User instance
     */
    public static User getInstance()
    {
        if (instance == null) instance = new User();
        return instance;
    }

    /**
     * @return the Volume of the music for this User
     */
    public double getMusicVolume()
    {
        return musicVolume;
    }

    /**
     * @return the Volume of the effects for this User
     */
    public double getEffectsVolume()
    {
        return effectsVolume;
    }
    /**
     * @return the current nickname
     */
    public String getNickname()
    {
        return nickname;
    }

    /**
     * @return the curret avatar path
     */
    public String getAvatar()
    {
        return avatar;
    }

    /**
     * @return the current total wins
     */
    public int getVictories()
    {
        return victories;
    }

    /**
     * @return the current total losses
     */
    public int getDefeats()
    {
        return defeats;
    }

    /**
     * @return the total matches played
     */
    public int getTotalMatches()
    {
        return defeats + victories;
    }

    /**
     * @return the total exp gained
     */
    public int getTotalExp()
    {
        return 500 * ((4 * (level.lvl - 1)) + ((level.lvl - 1) * (level.lvl) / 2)) + level.exp;
    }

    /**
     * @return the partial exp
     */
    public int getExp()
    {
        return level.exp;
    }

    /**
     * @return the current level
     */
    public int getLevel()
    {
        return level.lvl;
    }

    /**
     * @return the progress to the next level
     */
    public double getProgress()
    {
        return ((double) level.exp) / ((4 + level.lvl) * 500);
    }

    //Setters

    /**
     * Set the Music Volume for this User
     *
     * @param musicVolume the new Music Volume
     */
    public void setMusicVolume(double musicVolume)
    {
        this.musicVolume = musicVolume;
    }
    /**
     * Set the Effects Volume for this User
     *
     * @param effectsVolume the new Effects Volume
     */
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

    /**
     * reset the User data
     */
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
}