package Logic;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Game
{
    private static Pattern pattern = Pattern.compile("put\\(\\d+,\\d+\\)");

    private static ArrayList<Game> games = new ArrayList<>();
    private static ArrayList<Game> pausedGames = new ArrayList<>();
    private Player playerOne;
    private Player playerTwo;
    private Player playerTurn;
    private MatchTable matchTable = new MatchTable();

    public Game(String playerOneName, String playerTwoName)
    {
        ArrayList<Player> players = Player.getPlayers();
        boolean playerOneSet = false;
        boolean playerTwoSet = false;
        for (int i=0;i < players.size();i++)
        {
            if (players.get(i).getName().equals(playerOneName))
            {
                this.playerOne = players.get(i);
                playerOneSet = true;
            }
            if (players.get(i).getName().equals(playerTwoName))
            {
                this.playerTwo = players.get(i);
                playerTwoSet = true;
            }
        }
        if (!playerOneSet)
        {
            this.playerOne = new Player(playerOneName);
        }
        if (!playerTwoSet)
        {
            this.playerTwo = new Player(playerTwoName);
        }
        this.playerOne.setTypeOFTaw('X');
        this.playerTwo.setTypeOFTaw('O');
        this.playerTurn = this.playerOne;
        this.matchTable.setStartingTable();
        Game.addGame(this);
    }

    public static void addGame(Game game)
    {
        games.add(game);
    }

    public static ArrayList<Game> getGames()
    {
        return games;
    }

    public static ArrayList<Game> getPausedGames()
    {
        return pausedGames;
    }

    public void insideTheGame()
    {
        MatchTable matchTable = this.getMatchTable();
        int row = matchTable.getRow();
        int column = matchTable.getColumn();
        for (int i=0;i < row;i++)
        {
            for (int j=0;j < column;j++)
            {
                System.out.print(matchTable.getTable()[i][j]);
            }
            System.out.println();
        }
        System.out.println(this.playerTurn.getName());

        String line = Main.scanner.nextLine();
        String command = line.trim();
        if (pattern.matcher(command).matches())   //put
        {
            int x = command.charAt(4) - '0' - 1;
            int y = 2 * (command.charAt(6) - '0' - 1);
            this.put(x, y);
        }
        else if (command.equals("stop"))
        {
            games.remove(this);
            Main.mainMenu();
        }
        else if (command.equals("pause"))
        {
            pausedGames.add(0, this);
            Main.mainMenu();
        }
        else if (command.equals("undo"))
        {
            undo();
        }
        else
        {
            System.out.println("Invalid command");
            insideTheGame();
        }
    }

    public void put(int x, int y)
    {
        MatchTable matchTable = this.getMatchTable();
        int row = matchTable.getRow();
        int column = matchTable.getColumn();
        if(x >= row || x < 0 || y >= column || y < 0 || matchTable.getTable()[x][y] != '_')
        {
            System.out.println("Invalid coordination");
            insideTheGame();
        }
        else
        {
            matchTable.getTable()[x][y] = this.playerTurn.getTypeOFTaw();
            matchTable.increaseNumOfTheTawInTheTable();
            matchTable.setCoordinateOfLastAndSecondFromTheEndTaw(x, y);
            this.playerTurn = this.playerTurn.equals(this.playerOne) ? this.playerTwo : this.playerOne;
            if (matchTable.checkWinCondition(this.playerOne))
            {
                System.out.println("Logic.Player " + this.playerOne.getName() + " won");
                playerOne.increaseNumOfWins();
                playerTwo.increaseNumOfLoses();
                Main.mainMenu();
            }
            else if (matchTable.checkWinCondition(this.playerTwo))
            {
                System.out.println("Logic.Player " + this.playerTwo.getName() + " won");
                playerOne.increaseNumOfLoses();
                playerTwo.increaseNumOfWins();
                Main.mainMenu();
            }
            else if (matchTable.getNumOfTawInTheTable() == matchTable.getRow() * (matchTable.getColumn() + 1) / 2)
            {
                System.out.println("Draw");
                playerOne.increaseNumOfDraws();
                playerTwo.increaseNumOfDraws();
                Main.mainMenu();
            }
            else
            {
                insideTheGame();
            }
        }
    }

    public void undo()
    {
        MatchTable matchTable = this.getMatchTable();
        Player player = this.playerTurn.equals(this.playerOne) ? this.playerTwo : this.playerOne;
        if (!player.getUseUndo() && this.matchTable.getNumOfTawInTheTable() >= 2)
        {
            matchTable.removeTheLastTaw();
            this.playerTurn = player;
            this.playerTurn.setUseUndo();
        }
        else
        {
            System.out.println("Invalid undo");
        }
        insideTheGame();
    }

    public MatchTable getMatchTable()
    {
        return this.matchTable;
    }

    public Player getPlayerTwo()
    {
        return this.playerTwo;
    }

    public Player getPlayerOne()
    {
        return this.playerOne;
    }
}
