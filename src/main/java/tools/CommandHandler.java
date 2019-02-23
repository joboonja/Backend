package tools;


import bid.BidRepo;
import config.Commands;
import javafx.util.Pair;
import org.json.JSONObject;
import project.ProjectRepo;
import user.UserRepo;

import java.util.Scanner;

public class CommandHandler {
    public static Pair<String, JSONObject> getCommandParts(String command) throws Exception{
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
                    Joboonja.auction(pairedCommand.getValue());
                } else
                    throw new Exception(Commands.COMMAND_NOT_FOUND_ERROR);
            try {
                String command;
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    command = scanner.nextLine();
                    Pair<String, JSONObject> pairedCommand = CommandHandler.getCommandParts(command);
                    if (pairedCommand.getKey().equals(Commands.REGISTER)) {
                        UserRepo.getInstance().registerNewUser(JSONDecoder.decodeJSONtoUser(pairedCommand.getValue()));
                    } else if (pairedCommand.getKey().equals(Commands.ADD_PROJECT)) {
                        ProjectRepo.getInstance().addNewProject(JSONDecoder.decodeJSONtoProject(pairedCommand.getValue()));
                    } else if (pairedCommand.getKey().equals(Commands.BID)) {
                        BidRepo.getInstance().addNewBid(JSONDecoder.decodeJSONtoBid(pairedCommand.getValue()));
                    } else if (pairedCommand.getKey().equals(Commands.AUCTION)) {
                        JSONDecoder.decodeJSONToAuction(pairedCommand.getValue()).start();
                        return;
                    } else
                        throw new Exception(Commands.COMMAND_NOT_FOUND_ERROR);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
