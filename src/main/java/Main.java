import remoteServices.ProjectInitializer;
import remoteServices.SkillInitializer;
import server.Server;
import tools.CommandHandler;
import user.UserRepo;

public class Main {
    public static void main(String args[]) throws Exception
    {
        SkillInitializer.initSkills();
        ProjectInitializer.initProjects();
        UserRepo.getInstance().addDefaultUser();

        Server server = new Server();
        server.startServer();


    }
}

