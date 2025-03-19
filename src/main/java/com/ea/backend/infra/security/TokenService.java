package com.ea.backend.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.jwt.secret}")
    private String secret;

    private final String issuer = "EA";


    public String generateToken(Authentication authentication) {

      try {

          var authenticatedDetails =  (UserAuthenticated) authentication.getPrincipal();
          var user = authenticatedDetails.getUser();

          Algorithm algorithm = Algorithm.HMAC256(secret);


          return JWT.create().withIssuer(issuer)
                  .withSubject(user.getEmail())
                  .withExpiresAt(generateExpirationDate())
                  .sign(algorithm);

      }catch (JWTCreationException exception) {
         throw new IllegalArgumentException("Error on createAdmin token " + exception.getLocalizedMessage());
      }

    }

    public String validateToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
          var decoded =  JWT.require(algorithm)
                  .withIssuer(issuer)
                  .build()
                  .verify(token);

          return decoded.getSubject();

        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Invalid token + " + e.getMessage());
        }
    }

    private Instant getExpirationDate(String token) {
        return JWT.decode(token).getExpiresAt().toInstant();
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

}
