package Graphics;

import Logic.*;

import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuGraphics
{
    private Group rootSignUpMenu = new Group();
    private Scene sceneSignUpMenu = new Scene(rootSignUpMenu, 400, 400);
    private Group rootLoginMenu = new Group();
    private Scene sceneLoginMenu = new Scene(rootLoginMenu, 400, 400);
    private Group rootMainMenu = new Group();
    private Scene sceneMainMenu = new Scene(rootMainMenu, 400, 450, Color.LIGHTGREEN);
    private Group rootChangeName = new Group();
    private Scene sceneChangeName = new Scene(rootChangeName, 300, 300);
    private Group rootChangeTable = new Group();
    private Scene sceneChangeTable = new Scene(rootChangeTable, 400, 400);
    private Group rootRanking = new Group();
    private Scene sceneRanking = new Scene(rootRanking, 300, 700);

    public void signUpMenu(Stage primaryStage) throws Exception
    {
        rootSignUpMenu.getChildren().clear();

        TextField textFieldName = new TextField();
        PasswordField passwordField = new PasswordField();
        makeNameAndPasswordFields(rootSignUpMenu, textFieldName, passwordField);

        Text textSignUp = new Text("Sign Up");
        textSignUp.setFont(Font.font(25));
        textSignUp.setFill(Color.BLACK);
        textSignUp.layoutXProperty().bind(sceneSignUpMenu.widthProperty().subtract(textSignUp.prefWidth(-1)).divide(2));
        textSignUp.setY(50);
        rootSignUpMenu.getChildren().add(textSignUp);

        Text textInvalidInput = new Text();
        rootSignUpMenu.getChildren().add(textInvalidInput);

        Button buttonSignUp = new Button("Submit");
        submitButton(buttonSignUp);
        buttonSignUp.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                String name = textFieldName.getText();
                String password = passwordField.getText();
                if (name.isEmpty() || password.isEmpty())
                {
                    setInvalidInputText(sceneSignUpMenu, textInvalidInput, "you must Fill both TextFields");
                }
                else
                {
                    Player player = Player.findPlayer(name);
                    if (player == null)
                    {
                        player = new Player(name, password);
                        try
                        {
                            Player.savePlayerInfo(player, true);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        primaryStage.setScene(sceneLoginMenu);
                        primaryStage.centerOnScreen();
                        login(primaryStage);
                    }
                    else
                    {
                        setInvalidInputText(sceneSignUpMenu, textInvalidInput, "Player exists with this name");
                    }
                }
            }
        });
        rootSignUpMenu.getChildren().add(buttonSignUp);

        Button buttonAlreadyHaveAccount = new Button("Already have account");
        buttonAlreadyHaveAccount.relocate(150, 300);
        buttonAlreadyHaveAccount.setFont(Font.font(20));
        buttonAlreadyHaveAccount.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                primaryStage.setScene(sceneLoginMenu);
                primaryStage.centerOnScreen();
                login(primaryStage);
            }
        });
        rootSignUpMenu.getChildren().add(buttonAlreadyHaveAccount);

        primaryStage.setScene(sceneSignUpMenu);
        primaryStage.centerOnScreen();
    }

    private void login(Stage primaryStage)
    {
        rootLoginMenu.getChildren().clear();

        Text textLogin = new Text("Login");
        textLogin.setFill(Color.BLACK);
        textLogin.setFont(Font.font(25));
        textLogin.layoutXProperty().bind(sceneLoginMenu.widthProperty().subtract(textLogin.prefWidth(-1)).divide(2));
        textLogin.setY(50);
        rootLoginMenu.getChildren().add(textLogin);

        TextField textFieldName = new TextField();
        PasswordField passwordField = new PasswordField();
        makeNameAndPasswordFields(rootLoginMenu, textFieldName, passwordField);

        Text textInvalidInput = new Text();
        rootLoginMenu.getChildren().add(textInvalidInput);

        Button buttonLogin = new Button("Submit");
        submitButton(buttonLogin);
        buttonLogin.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                String name = textFieldName.getText();
                String password = passwordField.getText();
                if (name.isEmpty() || password.isEmpty())
                {
                    setInvalidInputText(sceneLoginMenu, textInvalidInput, "you must Fill both TextFields");
                    return;
                }
                Player player = Player.findPlayer(name);
                if (player == null)
                {
                    setInvalidInputText(sceneLoginMenu, textInvalidInput, "Invalid name or password");
                }
                else if (player.getPassword().equals(password))
                {
                    Player.login(player);
                    primaryStage.setScene(sceneMainMenu);
                    primaryStage.centerOnScreen();
                    try
                    {
                        mainMenu(primaryStage);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    setInvalidInputText(sceneLoginMenu, textInvalidInput, "Password is Wrong.Try again");
                }
            }
        });
        rootLoginMenu.getChildren().add(buttonLogin);

        Button buttonNeedToSignUp = new Button("Sign Up");
        buttonNeedToSignUp.relocate(260, 300);
        buttonNeedToSignUp.setFont(Font.font(20));
        buttonNeedToSignUp.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                primaryStage.setScene(sceneSignUpMenu);
                primaryStage.centerOnScreen();
                try
                {
                    signUpMenu(primaryStage);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        rootLoginMenu.getChildren().add(buttonNeedToSignUp);
    }

    private void mainMenu(Stage primaryStage)
    {
        rootMainMenu.getChildren().clear();

        Text textMainMenu = new Text("Welcome to XO");
        textMainMenu.setFill(Color.BLACK);
        textMainMenu.setFont(Font.font(40));
        textMainMenu.layoutXProperty().bind(sceneMainMenu.widthProperty().subtract(textMainMenu.prefWidth(-1)).divide(2));
        textMainMenu.setY(50);
        rootMainMenu.getChildren().add(textMainMenu);

        setMainMenuText(primaryStage, "Play", 80);
        setMainMenuText(primaryStage, "Set Match Table", 140);
        setMainMenuText(primaryStage, "Change Name", 200);
        setMainMenuText(primaryStage, "Ranking", 260);
        setMainMenuText(primaryStage, "Logout", 320);
        setMainMenuText(primaryStage, "Exit", 380);
    }

    private void setMainMenuText(Stage primaryStage, String text, int y)
    {
        Text mainMenuText = new Text(text);
        mainMenuText.setTextOrigin(VPos.TOP);
        mainMenuText.setFont(Font.font(null, FontWeight.SEMI_BOLD, 25));
        mainMenuText.layoutXProperty().bind(sceneMainMenu.widthProperty().subtract(mainMenuText.prefWidth(-1)).divide(2));
        mainMenuText.setY(y);
        mainMenuText.setFill(Color.BLUE);
        mainMenuText.setOnMouseEntered(event -> mainMenuText.setFill(Color.RED));
        mainMenuText.setOnMouseExited(event -> mainMenuText.setFill(Color.BLUE));
        mainMenuText.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                try
                {
                    switch (text)
                    {
                        case "Play":
                            //TODO
                            break;
                        case "Set Match Table":
                            primaryStage.setScene(sceneChangeTable);
                            primaryStage.centerOnScreen();
                            changeTable(primaryStage);
                            break;
                        case "Change Name":
                            primaryStage.setScene(sceneChangeName);
                            primaryStage.centerOnScreen();
                            changeName(primaryStage);
                            break;
                        case "Ranking":
                            primaryStage.setScene(sceneRanking);
                            primaryStage.centerOnScreen();
                            ranking(primaryStage);
                            break;
                        case "Logout":
                            primaryStage.setScene(sceneLoginMenu);
                            primaryStage.centerOnScreen();
                            login(primaryStage);
                            break;
                        case "Exit":
                            primaryStage.close();
                            break;
                    }
                }
                catch (Exception ignored)
                {

                }
            }
        });
        rootMainMenu.getChildren().add(mainMenuText);
    }

    private void changeName(Stage primaryStage)
    {
        rootChangeName.getChildren().clear();

        Label labelNewName = new Label("Enter new name");
        labelNewName.relocate(50, 30);
        labelNewName.setFont(Font.font(30));
        labelNewName.setTextFill(Color.YELLOWGREEN);
        rootChangeName.getChildren().add(labelNewName);

        TextField textFieldNewName = new TextField();
        textFieldNewName.relocate(80, 130);
        rootChangeName.getChildren().add(textFieldNewName);

        Text textInputConditionCommand = new Text();
        rootChangeName.getChildren().add(textInputConditionCommand);

        Button buttonNewName = new Button("Submit");
        buttonNewName.setFont(Font.font(25));
        buttonNewName.relocate(20, 200);
        buttonNewName.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                String newName = textFieldNewName.getText();
                if (newName.isEmpty())
                {
                    setInvalidInputText(sceneChangeName, textInputConditionCommand,"TextField is Empty");
                    return;
                }
                Player player = Player.findPlayer(newName);
                if (player != null)
                {
                    setInvalidInputText(sceneChangeName, textInputConditionCommand,"Player exists with this name");
                    return;
                }
                try
                {
                    Player.getLoggedInPlayer().changeName(newName);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                setInvalidInputText(sceneChangeName, textInputConditionCommand,"Name changed successfully");
                textInputConditionCommand.setFill(Color.GREEN);
            }
        });
        rootChangeName.getChildren().add(buttonNewName);

        backButton(primaryStage, rootChangeName, 190, 200);
    }

    private void changeTable(Stage primaryStage) throws Exception
    {
        rootChangeTable.getChildren().clear();

        Label labelChangeTable = new Label("Enter your desired number of rows and columns");
        rootChangeTable.getChildren().add(labelChangeTable);
        labelChangeTable.relocate(10, 30);
        labelChangeTable.setFont(Font.font(18));
        labelChangeTable.setTextFill(Color.GREEN);

        Label labelRow = new Label("Num of rows: ");
        rootChangeTable.getChildren().add(labelRow);
        labelRow.relocate(10, 120);
        labelRow.setFont(Font.font(20));
        labelRow.setTextFill(Color.BLACK);

        Label labelColumn = new Label("Num of columns: ");
        rootChangeTable.getChildren().add(labelColumn);
        labelColumn.relocate(10, 180);
        labelColumn.setFont(Font.font(20));
        labelColumn.setTextFill(Color.BLACK);

        TextField textFieldRow = new TextField();
        textFieldRow.setPromptText("Number of Rows");
        HBox hBoxRow = new HBox(textFieldRow);
        hBoxRow.relocate(200, 120);
        rootChangeTable.getChildren().add(hBoxRow);

        TextField textFieldColumn = new TextField();
        textFieldColumn.setPromptText("Number of Columns");
        HBox hBoxColumn = new HBox(textFieldColumn);
        hBoxColumn.relocate(200, 180);
        rootChangeTable.getChildren().add(hBoxColumn);

        Text inputIsInvalid = new Text();
        rootChangeTable.getChildren().add(inputIsInvalid);

        Button buttonApplyTableChange = new Button("Set new Table");
        buttonApplyTableChange.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                try
                {
                    int numOfRows = Integer.parseInt(textFieldRow.getText());
                    int numOfColumns = Integer.parseInt(textFieldColumn.getText());
                    if (numOfRows > 3 && numOfRows < 7 && numOfColumns > 3 && numOfColumns < 7)
                    {
                        primaryStage.setScene(sceneMainMenu);
                        primaryStage.centerOnScreen();
                        mainMenu(primaryStage);
                    }
                    else
                    {
                        setInvalidInputText(sceneChangeTable, inputIsInvalid, "Numbers must be between 4 and 6");
                    }
                }
                catch (NumberFormatException exception)
                {
                    setInvalidInputText(sceneChangeTable, inputIsInvalid, "Input in invalid.Please enter numbers");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        buttonApplyTableChange.setFont(Font.font(25));
        buttonApplyTableChange.relocate(30, 300);
        backButton(primaryStage, rootChangeTable, 280, 300);
        rootChangeTable.getChildren().add(buttonApplyTableChange);
    }

    private void ranking(Stage primaryStage)
    {
        rootRanking.getChildren().clear();

        Text textTop10 = new Text("Top 10");
        textTop10.setFill(Color.YELLOW);
        textTop10.setFont(Font.font(20));
        textTop10.layoutXProperty().bind(sceneRanking.widthProperty().subtract(textTop10.prefWidth(-1)).divide(2));
        textTop10.setY(20);
        rootRanking.getChildren().add(textTop10);

        setRankingMenuTexts();

        Player.sortPlayers();
        showRankingPlayers();
        backButton(primaryStage, rootRanking, 100, 600);
    }

    private void setRankingMenuTexts()
    {
        Text textNumber = new Text("NO");
        textNumber.relocate(20, 30);
        Text textName = new Text("Name");
        textName.relocate(45, 30);
        Text textW = new Text("W");
        textW.relocate(220, 30);
        Text textD = new Text("D");
        textD.relocate(240, 30);
        Text textL = new Text("L");
        textL.relocate(260, 30);
        rootRanking.getChildren().addAll(textNumber, textName, textW, textD, textL);
    }

    private void showRankingPlayers()
    {
        int counter = 1;
        for (Player player : Player.getPlayers())
        {
            if (counter > 10)
            {
                return;
            }
            Label labelPlayerName = new Label(counter + "- " + player.getName());
            labelPlayerName.setFont(Font.font(15));
            labelPlayerName.relocate(25, counter * 50);
            rootRanking.getChildren().add(labelPlayerName);

            Label labelPlayerHighScore = new Label(player.getNumOfWins() + "   " + player.getNumOfDraws() + "   " + player.getNumOfLoses());
            labelPlayerHighScore.setFont(Font.font(15));
            labelPlayerHighScore.relocate(220, counter * 50);
            rootRanking.getChildren().add(labelPlayerHighScore);

            if (player.equals(Player.getLoggedInPlayer()))
            {
                labelPlayerName.setTextFill(Color.RED);
                labelPlayerHighScore.setTextFill(Color.RED);
            }

            counter ++;
        }
    }

    public void makeNameAndPasswordFields(Group root, TextField textFieldName, PasswordField passwordField)
    {
        Label labelName = new Label("Name");
        root.getChildren().add(labelName);
        labelName.relocate(20, 130);
        labelName.setFont(Font.font(15));
        labelName.setTextFill(Color.BLACK);

        textFieldName.setPromptText("Username");
        HBox hBoxName = new HBox(textFieldName);
        hBoxName.relocate(130, 130);
        root.getChildren().add(hBoxName);

        Label labelPassword = new Label("Password");
        root.getChildren().add(labelPassword);
        labelPassword.relocate(20, 210);
        labelPassword.setFont(Font.font(15));
        labelPassword.setTextFill(Color.BLACK);

        passwordField.setPromptText("Password");
        HBox hBoxPassword = new HBox(passwordField);
        hBoxPassword.relocate(130, 210);
        root.getChildren().add(hBoxPassword);
    }

    private void setInvalidInputText(Scene scene, Text textInvalidInput, String text)
    {
        textInvalidInput.setText(text);
        textInvalidInput.setFont(Font.font(15));
        textInvalidInput.setFill(Color.RED);
        textInvalidInput.layoutXProperty().bind(scene.widthProperty().subtract(textInvalidInput.prefWidth(-1)).divide(2));
        textInvalidInput.setY(100);
    }

    private void submitButton(Button button)
    {
        button.relocate(25, 300);
        button.setFont(Font.font(20));
    }

    private void backButton(Stage primaryStage, Group root, int x, int y)
    {
        Button buttonBackToMainMenu = new Button("Back");
        buttonBackToMainMenu.setFont(Font.font(25));
        buttonBackToMainMenu.relocate(x, y);
        buttonBackToMainMenu.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                primaryStage.setScene(sceneMainMenu);
                primaryStage.centerOnScreen();
                try
                {
                    mainMenu(primaryStage);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        root.getChildren().add(buttonBackToMainMenu);
    }
}