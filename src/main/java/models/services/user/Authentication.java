package models.services.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import config.UserConfig;

import java.util.*;

public class Authentication {
    public static Algorithm algorithm = Algorithm.HMAC256(UserConfig.SECRET_KEY);

    public static String  createToken(String id) {
        Date expire = new Date();
        Calendar calender = Calendar.getInstance();
        calender.setTime(expire);
        calender.add(Calendar.HOUR_OF_DAY, 2);
        expire = calender.getTime();

        return JWT.create()
            .withIssuer("joboonja.com")
            .withIssuedAt(new Date())
            .withExpiresAt(expire)
            .withClaim("id", id)
            .sign(algorithm);
    }
}
