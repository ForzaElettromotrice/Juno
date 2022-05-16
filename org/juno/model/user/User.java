package org.juno.model.user;

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

    public User()
    {
        level = new Level();
        //TODO: invocare load
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

    // TODO: fare il get del lvl e dell'exp

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

    private void setVictories(int victories)
    {
        this.victories = victories;
    }

    private void setDefeats(int defeats)
    {
        this.defeats = defeats;
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
     * Load and Save ->
     *
     *      1: nickname;
     *      2: avatar;
     *      3: victories;
     *      4: defeats;
     *      5: exp
     *
     */
    public void load()
    {
        //TODO: caricare dal file di testo tutto il necessario
    }

    public void save()
    {
        //TODO: salvare dal file di testo tutto il necessario
    }

}
