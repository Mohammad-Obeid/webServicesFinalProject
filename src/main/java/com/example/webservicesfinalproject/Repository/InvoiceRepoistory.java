package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepoistory extends JpaRepository<Invoice,Integer> {
    Optional<Invoice> findByResResID(int resID);
}
