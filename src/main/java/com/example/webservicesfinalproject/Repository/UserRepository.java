package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<List<User>> findAllByUserNameAndUserRole(String userName,String role);

}
