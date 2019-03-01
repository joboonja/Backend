package controllers.user;


import models.data.user.User;
import models.data.user.UserRepo;
import models.services.user.UserService;
import tools.HttpTokenizer;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = HttpTokenizer.getID(request);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/views/user/userInfo.jsp");

        requestDispatcher.forward(request, response);

        try
        {
            User user = UserService.getUserByID(userID);
            request.setAttribute("user", user);
        }
        catch (Exception e )
        {

        }
    }
}
