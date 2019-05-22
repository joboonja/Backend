package tools;


import config.Commands;
import javafx.util.Pair;
import org.json.JSONObject;

import java.util.Scanner;

public class CommandHandler {
    public static Pair<String, JSONObject> getCommand() throws Exception{
        String command ;
        Scanner scanner = new Scanner(System.in);
        command = scanner.nextLine();
        Pair<String, JSONObject> pairedCommand;
        String parts[] = command.split(" ", 2);

        if(parts.length != 2)
            throw new Exception(Commands.INPUT_ERROR);

        String userCommand = parts[0];
        String jsonString = parts[1];

        JSONObject userInputJson = new JSONObject(jsonString);
        pairedCommand = new Pair<String, JSONObject>(userCommand, userInputJson);
        return pairedCommand;
    }
    public static void handleInputCommands()
    {
        do {
            try
            {
                Pair<String, JSONObject> pairedCommand = CommandHandler.getCommand();
                System.out.println(pairedCommand.getKey());
                System.out.println(pairedCommand.getValue().toString());
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
