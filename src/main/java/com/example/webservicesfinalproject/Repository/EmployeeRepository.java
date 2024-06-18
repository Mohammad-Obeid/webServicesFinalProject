package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.Employee;
import com.example.webservicesfinalproject.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Optional<Employee> findByUserUserID(int ID);
    Optional<List<Employee>> findByUserRoleAndEmployeeRoleAndStatus(Role userRole, String empRole, String status);
}
