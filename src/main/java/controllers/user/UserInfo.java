package controllers.user;


import config.JspConfig;
import config.ProjectServiceConfig;
import config.UserConfig;
import models.data.user.User;

import models.services.user.UserService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(value = "/user/*")
public class UserInfo extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
        response.setContentType("text/html; charset=UTF-8");
        try {
            User user = UserService.getUserByID(tools.HttpTokenizer.getID(request));
            request.setAttribute("user", user);
            request.setAttribute("skills", user.getSkills().values());

            String jsp = user.getId().equals(ProjectServiceConfig.USER_ID) ? JspConfig.SINGLE_LOGEDIN_USER_VIEW_PATH : JspConfig.SINGLE_GUEST_USER_VIEW_PATH;
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(jsp);
            requestDispatcher.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ERROR_VIEW);
            requestDispatcher.forward(request, response);
        }
    }
}
