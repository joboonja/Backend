package controllers.user;


import config.JspConfig;
import config.ProjectServiceConfig;
import config.UserConfig;
import models.data.user.User;

import models.data.user.UserRepo;
import models.services.user.UserService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(value = "/userList")
public class UserList extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
        response.setContentType("text/html; charset=UTF-8");
        try {
            ArrayList<User> users = UserRepo.getInstance().getAllUsers();
            request.setAttribute("userList", users);

            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.USER_LIST_VIEW_PATH);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(JspConfig.ERROR_VIEW);
            requestDispatcher.forward(request, response);
        }
    }
}
