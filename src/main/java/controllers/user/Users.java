package controllers.user;

import controllers.user.responses.AllUsersResponse;
import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.mapper.UserMapper;
import models.services.user.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Users {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<AllUsersResponse> getAllUsers() throws SQLException {
        List<AllUsersResponse> response = new ArrayList<>();
        for(User user : UserMapper.getInstance().getAllUsers())
        {
            response.add(new AllUsersResponse(user));
        }
        return response;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "userId") String userId) throws UserNotFound, SQLException {
        return UserService.getUserByID(userId);
    }
}
