package com.ea.backend.infra.security;

import com.ea.backend.domain.reservation.application.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = extractTokeFromRequest(request);

        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var userEmail = tokenService.validateToken(token);

        UserDetails existsUser = this.userDetailsService.loadUserByUsername(userEmail);

         if(existsUser == null) {
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             filterChain.doFilter(request, response);
             return;
         }

         request.setAttribute("user", existsUser);


        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(existsUser, null  ,existsUser.getAuthorities()));


        filterChain.doFilter(request, response);

    }



    private String
    extractTokeFromRequest(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");

        if (authorization == null) return null;



        return authorization.replace("Bearer ", "");
    }

}
