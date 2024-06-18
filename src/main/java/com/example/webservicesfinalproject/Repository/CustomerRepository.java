package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByUserUserName(String userName);

}
