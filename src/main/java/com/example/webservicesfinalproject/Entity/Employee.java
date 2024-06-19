package com.example.webservicesfinalproject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeID;
    private String employeeRole;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    private String status = "free";
    private LocalDateTime statusSinceWhen;

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", employeeRole='" + employeeRole + '\'' +
                ", status='" + status + '\'' +
                ", statusSinceWhen=" + statusSinceWhen +
                // Exclude `user` to avoid recursion
                // ", user=" + user +
                '}';
    }
}
