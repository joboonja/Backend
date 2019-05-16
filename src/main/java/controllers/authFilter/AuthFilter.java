package controllers.authFilter;

import org.springframework.http.HttpStatus;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import config.UserConfig;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        try {
            Algorithm algorithm = Algorithm.HMAC256(UserConfig.SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("joboonja.com")
                    .build();
            String header = ((HttpServletRequest)servletRequest).getHeader("Authorization");
            DecodedJWT jwt;
            if(header != null) {
                jwt = verifier.verify(header);
                servletRequest.setAttribute("id", jwt.getClaim("id"));
            }
            else
                ((HttpServletResponse)servletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
//            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JWTVerificationException exception){
            ((HttpServletResponse)servletResponse).setStatus(HttpStatus.FORBIDDEN.value());
            //filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
