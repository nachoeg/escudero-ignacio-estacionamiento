package com.cespi.estacionamiento.filters;

import com.cespi.estacionamiento.enums.TokenValidationResult;
import com.cespi.estacionamiento.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthFilter implements Filter {

  private final AuthService authService;

  public AuthFilter(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String path = httpRequest.getRequestURI();

    if (isPublicPath(path, httpRequest.getMethod())) {
      chain.doFilter(request, response);
      return;
    }

    String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
    if (token == null || !token.startsWith("Bearer ")) {
      sendErrorResponse(httpResponse, HttpStatus.FORBIDDEN, "Token no presente o mal formado");
      return;
    }

    token = token.substring(7);

    TokenValidationResult result = authService.validateToken(token);

    switch (result) {
      case VALID -> chain.doFilter(request, response);
      case EXPIRED -> sendErrorResponse(httpResponse, HttpStatus.UNAUTHORIZED, "Token expirado");
      case INVALID -> sendErrorResponse(httpResponse, HttpStatus.FORBIDDEN, "Token inválido");
      default -> sendErrorResponse(httpResponse, HttpStatus.INTERNAL_SERVER_ERROR, "Error desconocido");
    }
  }

  private boolean isPublicPath(String path, String method) {
    // Define public paths here
    // For example, if you want to allow access to the login endpoint:
    // return path.startsWith("/api/auth/login") ||
    // path.startsWith("/api/auth/register");
    return path.startsWith("/api/auth/") || HttpMethod.OPTIONS.matches(method);
  }

  private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
    response.setStatus(status.value());
    response.getWriter().write(message);
  }
}