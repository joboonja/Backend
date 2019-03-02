package controllers.user;


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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        try {
            User user = UserService.getUserByID(tools.HttpTokenizer.getID(request));
            request.setAttribute("user", user);
            request.setAttribute("skills", user.getSkills().values());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(UserConfig.SINGLE_USER_VIEW_PATH);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
