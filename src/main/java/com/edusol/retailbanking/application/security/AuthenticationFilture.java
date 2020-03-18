package com.edusol.retailbanking.application.security;

import com.edusol.retailbanking.application.SpringAppicationContext;
import com.edusol.retailbanking.application.dto.UserDto;
import com.edusol.retailbanking.application.request.UserLoginRequestModel;
import com.edusol.retailbanking.application.service.UserService;
import com.edusol.retailbanking.application.service.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilture extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilture(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        try {
            UserLoginRequestModel cred = new ObjectMapper()

                    .readValue(request.getInputStream(), UserLoginRequestModel.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            cred.getEmail(),
                            cred.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String  userName=((User) authResult.getPrincipal()).getUsername();
        String token= Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstant.EXPITATION_TIME))
                .signWith(SignatureAlgorithm.HS512,SecurityConstant.getTokenSecret())
                .compact();
        UserService userService=(UserService) SpringAppicationContext.getBean("userServiceImp");
        UserDto userDto=userService.getUser(userName);

        response.addHeader(SecurityConstant.HEADER_STRING,SecurityConstant.TOKEN_PREFIX+token);
        response.addHeader("UserId",userDto.getUserId());
    }
}
