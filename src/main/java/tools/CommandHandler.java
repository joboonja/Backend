package tools;


import javafx.util.Pair;
import org.json.JSONObject;

import java.util.Scanner;

public class CommandHandler {
    public static Pair<String, JSONObject> getCommand() {
        String command ;
        Scanner scanner = new Scanner(System.in);
        command = scanner.nextLine();
        String parts[] = command.split(" ", 2);

        Pair<String, JSONObject> pairedCommand;
        try
        {
            if(parts.length != 2)
                throw new Exception();
            String userCommand = parts[0];
            String jsonString = parts[1];

            JSONObject userInputJson = new JSONObject(jsonString);

            pairedCommand = new Pair<String, JSONObject>(userCommand, userInputJson);
        }
        catch (Exception e)
        {
            System.out.println("Bad Input!");

            pairedCommand = new Pair<String, JSONObject>("", new JSONObject(""));
        }

        return pairedCommand;
    }
}
