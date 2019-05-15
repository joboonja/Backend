package controllers.user;

import controllers.user.requests.SignupRequest;
import controllers.user.responses.AllUsersResponse;
import exceptions.InvalidUser;
import exceptions.UserAlreadyExists;
import exceptions.UserNotFound;
import models.data.user.User;
import models.data.user.mapper.UserMapper;
import models.services.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Users {
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void signup(@RequestBody final SignupRequest request) throws InvalidUser, UserAlreadyExists {
        UserService.registerUser(new User( request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getJobTitle(),
                request.getBio(),
                request.getProfilePictureUrl()));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<AllUsersResponse> getAllUsers(
        @RequestParam(value = "search", required = false) String query) throws SQLException, UserNotFound {
        List<AllUsersResponse> response = new ArrayList<>();
        ArrayList<User> users = query != null ? UserService.searchUsers(query) : UserMapper.getInstance().getAllUsers();

        for(User user : users)
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
