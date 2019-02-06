package joboonja;

import bid.Bid;
import org.json.JSONObject;
import project.Project;
import user.User;

import java.util.ArrayList;

public class Joboonja {
    private static ArrayList<Project> projects = new ArrayList<Project>();
    private static ArrayList<User> users = new ArrayList<User>();
    private static ArrayList<Bid> bids = new ArrayList<Bid>();
    public static void addNewProject(JSONObject projectInfo)
    {
        projects.add(new Project(projectInfo));
    }
    public static void registerNewUser(JSONObject userInfo) { users.add(new User(userInfo)); }
    public static void addNewBid(JSONObject bidInfo) { bids.add(new Bid(bidInfo)); }
}
