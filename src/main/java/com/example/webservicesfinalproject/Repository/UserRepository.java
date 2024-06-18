package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.Role;
import com.example.webservicesfinalproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserName(String userName);

//    Optional<List<User>> findAllByUserNameAndRole(String userName, Role role);
    Optional<List<User>> findAllByUserName(String userName);

    Optional<User> findByEmployeeEmployeeID(int empID);
}
