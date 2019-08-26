package Graphics;

import Logic.*;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main extends Application
{
    private static Pattern pattern1 = Pattern.compile("new game.*");
    private static Pattern pattern2 = Pattern.compile("set table");
    private static Pattern pattern3 = Pattern.compile("set table \\d+\\*\\d+");
    private static Pattern pattern4 = Pattern.compile("\\d+");

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        mainMenu();
    }

    public static void mainMenu()
    {
        String line = scanner.nextLine();
        String command = line.trim();
        if (pattern1.matcher(command).matches())
        {
            checkNewGameValidity(command);
        }
        else if (pattern2.matcher(command).matches())
        {
            MatchTable.changeDefaultTable(3,3);
            mainMenu();
        }
        else if (pattern3.matcher(command).matches())
        {
            String[] commandParts = command.split(" ");
            int row = Integer.parseInt(commandParts[2].substring(0, commandParts[2].indexOf('*')));
            int column = Integer.parseInt(commandParts[2].substring(commandParts[2].indexOf('*') + 1));
            MatchTable.changeDefaultTable(row,column);
            mainMenu();
        }
        else if (command.equals("scoreboard"))
        {
            scoreboard();
        }
        else if (command.equals("resume"))
        {
            resumeMenu();
        }
        else if (command.equals("quit"))
        {
            System.exit(0);
        }
        else
        {
            System.out.println("Invalid command");
            mainMenu();
        }
    }

    public static void checkNewGameValidity(String command)
    {
        String[] commandParts = command.split(" ");
        if (commandParts.length < 4)
        {
            System.out.println("Invalid players");
            mainMenu();
        }
        else if(commandParts.length > 4)
        {
            System.out.println("Invalid command");
            mainMenu();
        }
        else
        {
            String playerOne = commandParts[2];
            String playerTwo = commandParts[3];
            Game game = new Game(playerOne, playerTwo);
            game.insideTheGame();
        }
    }

    public static void scoreboard()
    {
        ArrayList<Player> players = Player.getPlayers();
        int numOfPlayers = players.size();
        Player.arrangePlayers();
        for (int i=0;i < numOfPlayers;i++)
        {
            Player player = players.get(i);
            System.out.println(player.getName() + " " + player.getNumOfWins() + " " + player.getNumOfLoses() + " " + player.getNumOfDraws());
        }
        while (true)
        {
            String line = scanner.nextLine();
            String command = line.trim();
            if (command.equals("back"))
            {
                mainMenu();
            }
            else if (command.equals("quit"))
            {
                System.exit(0);
            }
            else
            {
                System.out.println("Invalid command");
            }
        }
    }

    public static void resumeMenu()
    {
        ArrayList<Game> pausedGames = Game.getPausedGames();
        int numOfPausedGames = pausedGames.size();
        for (int i=0;i < numOfPausedGames;i++)
        {
            Game game = pausedGames.get(i);
            System.out.println(i + 1 + ". " + game.getPlayerOne().getName() + " " + game.getPlayerTwo().getName());
        }
        while (true)
        {
            String line = scanner.nextLine();
            String command = line.trim();
            if (pattern4.matcher(command).matches())
            {
                int number = Integer.parseInt(command);
                if (number > numOfPausedGames || number <= 0)
                {
                    System.out.println("Invalid number");
                }
                else
                {
                    ArrayList<Game> games = Game.getGames();
                    int gamaTobeContinuedIndex = 0;
                    for (int i=0;i < games.size();i++)
                    {
                        if (games.get(i).equals(pausedGames.get(number - 1)))
                        {
                            pausedGames.remove(number - 1);
                            gamaTobeContinuedIndex = i;
                            break;
                        }
                    }
                    games.get(gamaTobeContinuedIndex).insideTheGame();
                }
            }
            else if (command.equals("back"))
            {
                mainMenu();
            }
            else
            {
                System.out.println("Invalid command");
            }
        }
    }
}