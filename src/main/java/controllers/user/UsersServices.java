package controllers.user;

import config.JspConfig;
import models.data.user.User;
import models.data.user.UserRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UsersServices {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers()
    {
        return UserRepo.getInstance().getAllUsers();
    }
}
