package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkShiftRepository extends JpaRepository<WorkShift,Integer> {
    Optional<WorkShift> findByWorkDayAndEmployeeEmployeeID(LocalDate workDay, int empID);
}
