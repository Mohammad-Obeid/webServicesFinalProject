package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findByCustomerUserUserNameAndAndCheckinDate(String custName, LocalDate checkinDate);
    Optional<Reservation> findByCustomerUserUserNameAndCheckoutDateGreaterThan(String custName, LocalDate timenow);
}
