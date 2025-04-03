package com.cespi.estacionamiento.services;

import org.springframework.stereotype.Service;

import com.cespi.estacionamiento.enums.TokenValidationResult;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class AuthService {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  public AuthService(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  public User authenticate(String phone, String password) {
    User user = userService.getUserEntityByPhone(phone);
    if (user == null) {
      throw new RuntimeException("Usuario no encontrado");
    }
    if (!user.getPassword().equals(password)) {
      throw new RuntimeException("Contraseña incorrecta");
    }
    return user;
  }

  public String generateToken(Long userId) {
    return jwtUtil.generateToken(userId);
  }

  public TokenValidationResult validateToken(String token) {
    try {
      jwtUtil.extractAllClaims(token);
      return TokenValidationResult.VALID;
    } catch (ExpiredJwtException e) {
      return TokenValidationResult.EXPIRED;
    } catch (Exception e) {
      return TokenValidationResult.INVALID;
    }
  }
}