package com.vovafomin.insidechallenge;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vovafomin.insidechallenge.entity.User;
import com.vovafomin.insidechallenge.service.CustomUserDetailsService;
import com.vovafomin.insidechallenge.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JWTAuthorizationFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final CustomUserDetailsService userDetailsService;

  @Autowired
  public JWTAuthorizationFilter(
      TokenService tokenService, CustomUserDetailsService userDetailsService) {
    this.tokenService = tokenService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer_")) {
      String jwt = authHeader.substring(7);
      if (jwt.isBlank()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
      } else {
        try {
          String username = tokenService.validateTokenAndRetrieveSubject(jwt);
          User user = userDetailsService.loadUserByUsername(username);
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  user, user.getPassword(), user.getAuthorities());
          if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        } catch (JWTVerificationException e) {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
