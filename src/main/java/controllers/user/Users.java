package controllers.user;

import config.JspConfig;
import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.UserRepo;
import models.services.user.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Users {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers()
    {
        return UserRepo.getInstance().getAllUsers();
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "userId") String userId) throws UserNotFound
    {
        return UserService.getUserByID(userId);
    }
}
