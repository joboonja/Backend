import models.remoteServices.ProjectInitializer;
import models.remoteServices.SkillInitializer;
import server.Server;
import models.data.user.UserRepo;

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

