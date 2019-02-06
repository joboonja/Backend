import javafx.util.Pair;
import org.json.JSONObject;
import tools.CommandHandler;

import java.util.Scanner;

public class Main {
    public static void main(String args[])
    {
        Pair<String, JSONObject> pairedCommand = CommandHandler.getCommand();
        System.out.println(pairedCommand.getKey());
        System.out.println(pairedCommand.getValue().toString());

    }
}

