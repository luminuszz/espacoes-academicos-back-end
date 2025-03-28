package com.ea.backend.infra.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    var token = extractTokeFromRequest(request);

    if (token == null || token.isEmpty() || request.getServletPath().startsWith("/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
        var userEmail = tokenService.validateToken(token);

        UserDetails existsUser = this.userDetailsService.loadUserByUsername(userEmail);

        request.setAttribute("user", existsUser);

        System.out.println(existsUser.getAuthorities());

        var newAuthContext =
            new UsernamePasswordAuthenticationToken(existsUser, null, existsUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuthContext);
      
    } catch (JWTVerificationException e) {
      SecurityContextHolder.clearContext();
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired or invalid");
      return;
    }

    filterChain.doFilter(request, response);
    }

    private String
    extractTokeFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

    if (authorization == null || authorization.isEmpty()) return null;

    return authorization.replace("Bearer ", "");
    }

}
