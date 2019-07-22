package Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Player
{
    private static ArrayList<Player> players = new ArrayList<>();
    private String name;
    private boolean useUndo = false;
    private int numOfWins = 0;
    private int numOfDraws = 0;
    private int numOfLoses = 0;
    private char TypeOFTaw;

    public Player(String name)
    {
        this.name = name;
        Player.addPlayer(this);
    }

    public static void addPlayer(Player player)
    {
        players.add(player);
    }

    public static void arrangePlayers()
    {
        ArrayList<Player> players = Player.getPlayers();
        Collections.sort(players, new Comparator<Player>()
        {
            @Override
            public int compare(Player o1, Player o2)
            {
                if (o1.getNumOfWins() < o2.getNumOfWins())
                {
                    return 1;
                }
                else if (o1.getNumOfWins() > o2.getNumOfWins())
                {
                    return -1;
                }
                else if (o1.getNumOfLoses() < o2.getNumOfLoses())
                {
                    return -1;
                }
                else if (o1.getNumOfLoses() > o2.getNumOfLoses())
                {
                    return 1;
                }
                else if (o1.getNumOfDraws() < o2.getNumOfDraws())
                {
                    return -1;
                }
                else if (o1.getNumOfDraws() > o2.getNumOfDraws())
                {
                    return 1;
                }
                else if (o1.getName().compareTo(o2.getName()) < 1)
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
        });
    }
    public static ArrayList<Player> getPlayers()
    {
        return players;
    }

    public String getName()
    {
        return this.name;
    }

    public char getTypeOFTaw()
    {
        return this.TypeOFTaw;
    }

    public void setTypeOFTaw(char typeOFTaw)
    {
        this.TypeOFTaw = typeOFTaw;
    }

    public int getNumOfDraws()
    {
        return this.numOfDraws;
    }

    public void increaseNumOfDraws()
    {
        this.numOfDraws ++;
    }

    public int getNumOfLoses()
    {
        return this.numOfLoses;
    }

    public void increaseNumOfLoses()
    {
        this.numOfLoses ++;
    }

    public int getNumOfWins()
    {
        return this.numOfWins;
    }

    public void increaseNumOfWins()
    {
        this.numOfWins ++;
    }

    public boolean getUseUndo()
    {
        return this.useUndo;
    }

    public void setUseUndo()
    {
        this.useUndo = true;
    }
}
