package services.project;

import config.ProjectServiceConfig;
import project.Project;
import project.ProjectRepo;


import javax.xml.ws.spi.http.HttpExchange;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectService {
    public static String getAllProjectsHtml() throws Exception {
        ProjectRepo projectRepo = ProjectRepo.getInstance();
        ArrayList<Project> projects =  projectRepo.getProjectsForUser(ProjectServiceConfig.USER_ID);

        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Projects</title>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            text-align: center;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        td {\n" +
                "            min-width: 300px;\n" +
                "            margin: 5px 5px 5px 5px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table>\n";
        for(Project project : projects)
        {
            html += "        <tr>\n" +
                    "            <th>"+project.getID()+"</th>\n" +
                    "            <th>"+project.getTitle()+"</th>\n" +
                    "            <th>"+Long.toString(project.getBudget())+"</th>\n" +
                    "        </tr>\n";
        }
        html +=  "    </table>\n" +
                "</body>\n" +
                "</html>";
        return html;

    }
    public static String getProjectByIdHtml(String projectID) throws Exception
    {
        ProjectRepo projectRepo = ProjectRepo.getInstance();
        Project project = projectRepo.getProjectByIDForUser(projectID, ProjectServiceConfig.USER_ID);
        return  "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Project</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <ul>\n" +
                "        <li>id: "+project.getID()+"</li>\n" +
                "        <li>title: "+project.getTitle()+"</li>\n" +
                "        <li>description: "+project.getDescription()+"</li>\n" +
                "        <li>imageUrl: <img src=\""+project.getImageURL()+"\" style=\"width: 50px; height: 50px;\"></li>\n" +
                "        <li>budget: "+Long.toString(project.getBudget())+"</li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>";

    }
}
