package Logic;

import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class Player
{
    private static ArrayList<Player> players = new ArrayList<>();
    private String name;
    private String password;
    private boolean useUndo = false;
    private int numOfWins = 0;
    private int numOfDraws = 0;
    private int numOfLoses = 0;
    private char TypeOFTaw;
    private static Player loggedInPlayer;

    public Player(String name, String password)
    {
        this.name = name;
        this.password = password;
        players.add(this);
    }

    public static void login(Player player)
    {
        Player.loggedInPlayer = player;
    }

    public static Player findPlayer(String name)
    {
        for (Player player : players)
        {
            if (player.getName().equals(name))
            {
                return player;
            }
        }
        return null;
    }

    public static void savePlayerInfo(Player player, boolean isNewAccount) throws IOException
    {
        String name = player.getName();
        if (isNewAccount)
        {
            FileWriter SavedAccountPath = new FileWriter("SavedAccounts/SavedAccountPath.txt" ,true);
            SavedAccountPath.write(name + "\n");
            SavedAccountPath.close();
        }
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(player);
        System.out.println(json);
        try
        {
            FileWriter saveAccountInfo = new FileWriter("SavedAccounts/" + name + ".json", false);
            saveAccountInfo.write(json);
            saveAccountInfo.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void changeName(String newName) throws IOException
    {
        deleteAccountJson();
        this.name = newName;
        savePlayerInfo(this, true);
    }

    private void deleteAccountJson() throws IOException
    {
        Files.deleteIfExists(Paths.get("SavedAccounts/" + this.getName() + ".json"));
        BufferedReader previousSavedAccountPath = new BufferedReader(new FileReader("SavedAccounts/SavedAccountPath.txt"));
        FileWriter newSavedAccountPath = new FileWriter("SavedAccounts/NewSavedAccountPath.txt" ,true);
        while (true)
        {
            String line = previousSavedAccountPath.readLine();
            if(line == null)
            {
                break;
            }
            if (line.equals(this.getName()))
            {
                continue;
            }
            newSavedAccountPath.write(line + "\n");
        }
        previousSavedAccountPath.close();
        newSavedAccountPath.close();
        Files.deleteIfExists(Paths.get("SavedAccounts/SavedAccountPath.txt"));
        File file = new File("SavedAccounts/NewSavedAccountPath.txt");
        file.renameTo(new File("SavedAccounts/SavedAccountPath.txt"));
    }

    public static void sortPlayers()
    {
        ArrayList<Player> players = Player.getPlayers();
        players.sort(new Comparator<Player>()
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

    public static Player getLoggedInPlayer()
    {
        return loggedInPlayer;
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

    public String getPassword()
    {
        return password;
    }
}
