package Graphics;

import Logic.*;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
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
        convertJSONToPlayers();
        MenuGraphics menuGraphics = new MenuGraphics();
        menuGraphics.signUpMenu(primaryStage);
        primaryStage.setTitle("XO");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void convertJSONToPlayers() throws Exception
    {
        InputStream inputStream = new FileInputStream("SavedAccounts/SavedAccountPath.txt");
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext())
        {
            String fileName = scanner.nextLine();

            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader("SavedAccounts/" + fileName + ".json");
            Object obj = jsonParser.parse(reader);
            System.out.println(obj);
            Player.getPlayers().add(new Gson().fromJson(obj.toString(), Player.class));
        }
    }
}