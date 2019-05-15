package controllers.login;

import controllers.login.requests.LoginRequest;
import controllers.login.response.TokenResponse;
import exceptions.DataBaseError;
import exceptions.LoginFailure;
import models.services.user.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    TokenResponse login(@RequestBody LoginRequest request) throws DataBaseError, LoginFailure {
        String token =  UserService.login(request.getUsername(), request.getPassword());
        return new TokenResponse(token);
    }
}
