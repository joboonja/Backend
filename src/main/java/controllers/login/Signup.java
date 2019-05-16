package controllers.login;

import controllers.login.response.TokenResponse;
import controllers.user.requests.SignupRequest;
import exceptions.DataBaseError;
import exceptions.InvalidUser;
import exceptions.LoginFailure;
import exceptions.UserAlreadyExists;
import models.data.user.User;
import models.services.user.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Signup {
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public TokenResponse signup(@RequestBody final SignupRequest request) throws InvalidUser, UserAlreadyExists, DataBaseError, LoginFailure {
        UserService.registerUser(new User( request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getJobTitle(),
                request.getBio(),
                request.getProfilePictureUrl(),
                request.getPassword()));
        String token = UserService.login(request.getUsername(), request.getPassword());
        return new TokenResponse(token);
    }
}
