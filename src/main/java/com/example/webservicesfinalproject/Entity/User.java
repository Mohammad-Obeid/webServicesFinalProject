package com.example.webservicesfinalproject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String userRole;
    @Column(nullable = false)
    private String userPassword;
    @Column(nullable = false)
    private String userPhoneNumber;
    private LocalDateTime joinDate;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer customer;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;

}
