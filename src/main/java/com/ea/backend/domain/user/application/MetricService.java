package com.ea.backend.domain.user.application;

import com.ea.backend.domain.reservation.application.repository.ReservationRepository;
import com.ea.backend.domain.space.application.repository.AcademicSpaceRepository;
import com.ea.backend.domain.user.application.repository.UserRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

  @Autowired private AcademicSpaceRepository academicSpaceRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ReservationRepository reservationRepository;

  public Map<String, Long> getCounts() {

    return Map.of(
        "academicSpaces",
        academicSpaceRepository.count(),
        "users",
        userRepository.count(),
        "reservations",
        reservationRepository.count());
  }

  public List<Map<String, Object>> countReservationsByDayOfWeek() {
    return reservationRepository.countReservationsByDayOfWeek();
  }

  public List<Map<String, Object>> countReservationsByAcademicSpaceLast7Days() {
    return academicSpaceRepository.countReservationsByAcademicSpaceLast7Days();
  }
}
