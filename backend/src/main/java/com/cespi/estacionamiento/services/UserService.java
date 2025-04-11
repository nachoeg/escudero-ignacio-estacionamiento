package com.cespi.estacionamiento.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cespi.estacionamiento.dtos.ParkingSessionDTO;
import com.cespi.estacionamiento.dtos.UserDTO;
import com.cespi.estacionamiento.dtos.UserGetDTO;
import com.cespi.estacionamiento.models.ParkingSession;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.repositories.UserRepository;
import com.cespi.estacionamiento.utils.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  public Long createUser(UserDTO userDTO) {
    User user = userRepository.save(mapToEntity(userDTO));
    return user.getId();
  }

  @Transactional
  public List<UserGetDTO> getUsers() {
    return userRepository.findAll().stream().map(user -> mapToDTO(user)).toList();
  }

  public Long getUserIdFromToken(String token) {
    token = token.replace("Bearer ", "");
    return jwtUtil.extractUserId(token);
  }

  @Transactional
  public UserGetDTO getUserById(Long id) {
    return userRepository.findById(id).map(user -> mapToDTO(user)).orElse(null);
  }

  @Transactional
  public UserGetDTO getUserByPhone(String phone) {
    return userRepository.findByPhone(phone).map(user -> mapToDTO(user)).orElse(null);
  }

  @Transactional
  public User getUserEntityByPhone(String phone) {
    return userRepository.findByPhone(phone).orElse(null);
  }

  public void updateUser(Long id, UserDTO userDTO) {
    User user = userRepository.findById(id).orElseThrow();
    mapToEntity(userDTO);
    userRepository.save(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public boolean existsByPhone(String phone) {
    return userRepository.existsByPhone(phone);
  }

  public UserGetDTO mapToDTO(User user) {
    ParkingSessionDTO parkingSessionDTO = null;

    ParkingSession activeSession = user.getParkingSessions().stream()
        .filter(ParkingSession::isActive)
        .findFirst()
        .orElse(null);

    if (activeSession != null) {
      parkingSessionDTO = new ParkingSessionDTO(
          activeSession.getId(),
          activeSession.getLicensePlate().getPlate(),
          activeSession.getStartTime(),
          activeSession.getEndTime());
    }

    return new UserGetDTO(
        user.getEmail(),
        user.getPhone(),
        user.getId(),
        user.getAccount().getBalance(),
        user.getLicensePlates().stream().map(licensePlate -> licensePlate.getPlate()).toList(),
        user.getParkingSessions().stream()
            .anyMatch(parkingSession -> parkingSession.isActive()),
        parkingSessionDTO);

  }

  public User mapToEntity(UserDTO userDTO) {
    return new User(userDTO.getEmail(), userDTO.getPhone(), userDTO.getPassword());
  }

}
