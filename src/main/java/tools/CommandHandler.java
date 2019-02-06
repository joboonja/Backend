package tools;


import com.sun.xml.internal.bind.v2.TODO;
import config.Commands;
import javafx.util.Pair;
import joboonja.Joboonja;
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
                if(pairedCommand.getKey().equals(Commands.REGISTER))
                {
                    Joboonja.registerNewUser(pairedCommand.getValue());
                } else if(pairedCommand.getKey().equals(Commands.ADD_PROJECT))
                {
                    Joboonja.addNewProject(pairedCommand.getValue());
                } else if(pairedCommand.getKey().equals(Commands.BID))
                {
                    Joboonja.addNewBid(pairedCommand.getValue());
                } else if(pairedCommand.getKey().equals(Commands.AUCTION)) {
                    //TODO:Call needed function
                } else
                    throw new Exception(Commands.COMMAND_NOT_FOUND_ERROR);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
