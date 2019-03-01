package controllers.user;


import models.data.user.User;
import models.data.user.UserRepo;
import models.services.user.UserService;
import tools.HttpTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = HttpTokenizer.getID(request);


        try
        {
            User user = UserService.getUserByID(userID);
            request.setAttribute("id", "Farzad");
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("lastName", user.getLastName());
            request.setAttribute("jobTitle", user.getJobTitle());
            request.setAttribute("bio", user.getJobTitle());
            request.setAttribute("skills", user.getSkills());
            request.getRequestDispatcher("views/user/userInfo.jsp").forward(request, response);

        } catch (Exception e )
        {

        }
    }
}
