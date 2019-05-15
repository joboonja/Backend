package controllers.login;

import controllers.login.requests.LoginRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    void login(@RequestBody LoginRequest request){

    }
}
