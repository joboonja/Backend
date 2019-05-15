package models.services.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import config.UserConfig;

import java.util.*;

public class Authentication {
    private static Algorithm algorithm;

    public String  createToken() {
        Date exp = new Date();
        Calendar calender = Calendar.getInstance();
        calender.setTime(exp);
        calender.add(Calendar.HOUR_OF_DAY, 2);
        exp = calender.getTime();
        Map< String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");


        algorithm = Algorithm.HMAC256(UserConfig.SECRET_KEY);
        String token = JWT.create()
            .withIssuer("joboonja.com")
            .withIssuedAt(new Date())
            .withHeader(header)
            .withExpiresAt(exp)
            .sign(algorithm);

        return token;
    }
}
