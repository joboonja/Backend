package models.services.project;

import config.ProjectServiceConfig;
import models.data.project.Project;
import models.data.project.ProjectRepo;

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
                "        tr {\n" +
                "            direction: rtl;\n" +
                "        }"+
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
}
