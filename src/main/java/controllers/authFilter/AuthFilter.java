package controllers.authFilter;

import models.services.user.Authentication;
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
    public AuthFilter(){

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException{
        try {
            String uri = ((HttpServletRequest)servletRequest).getRequestURI();
            if(uri.equals("/login") || uri.equals("/signup"))
            {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
            JWTVerifier verifier = JWT.require(Authentication.algorithm)
                    .withIssuer("joboonja.com")
                    .build();
            String header = ((HttpServletRequest)servletRequest).getHeader("Authorization");
            header = header.substring(7); // - Bearer
            DecodedJWT jwt;
            if(header != null) {
                jwt = verifier.verify(header);
                String id =  jwt.getClaim("id").asString();
                servletRequest.setAttribute("id", id);
                chain.doFilter(servletRequest, servletResponse);
            }
            else
                ((HttpServletResponse)servletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (JWTVerificationException exception){
            ((HttpServletResponse)servletResponse).setStatus(HttpStatus.FORBIDDEN.value());
        }

    }
    public void init(FilterConfig fConfig) throws ServletException {
    }

}