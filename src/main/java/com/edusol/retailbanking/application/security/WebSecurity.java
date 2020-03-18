package com.edusol.retailbanking.application.security;

import com.edusol.retailbanking.application.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter  {
    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserService userService,BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userDetailsService=userService;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()
               .authorizeRequests()
               .antMatchers(HttpMethod.POST, SecurityConstant.SIGN_UP_URL)
               .permitAll()
               .anyRequest()
               .authenticated().and()
               .addFilter(getAuthenticationFilture())
                .addFilter(new AuthorizationFilture(authenticationManager()))
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilture getAuthenticationFilture() throws Exception{
        final AuthenticationFilture filture=new AuthenticationFilture(authenticationManager());
        filture.setFilterProcessesUrl("/users/login");
        return filture;
    }
}
