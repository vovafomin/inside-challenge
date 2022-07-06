package com.vovafomin.insidechallenge.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vovafomin.insidechallenge.dto.TokenDTO;
import com.vovafomin.insidechallenge.request.CredentialsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

  private final String secret;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Autowired
  public TokenService(
      @Value("${app.jwt-secret}") String secret,
      AuthenticationManagerBuilder authenticationManagerBuilder) {
    this.secret = secret;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
  }

  public TokenDTO exchange(CredentialsRequest credentials) {
    try {
      AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(
              credentials.getUsername(), credentials.getPassword());
      authenticationManager.authenticate(authenticationToken);
      String token = generateToken(credentials.getUsername());
      return TokenDTO.builder().token(token).build();
    } catch (AuthenticationException authExc) {
      throw new RuntimeException("Invalid login credentials");
    }
  }

  public String generateToken(String username)
      throws IllegalArgumentException, JWTCreationException {
    return JWT.create()
        .withSubject("sub")
        .withClaim("username", username)
        .withIssuedAt(new Date())
        .withIssuer("com.vovafomin")
        .sign(Algorithm.HMAC256(secret));
  }

  public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
    JWTVerifier verifier =
        JWT.require(Algorithm.HMAC256(secret))
            .withSubject("sub")
            .withIssuer("com.vovafomin")
            .build();
    DecodedJWT jwt = verifier.verify(token);
    return jwt.getClaim("username").asString();
  }
}
