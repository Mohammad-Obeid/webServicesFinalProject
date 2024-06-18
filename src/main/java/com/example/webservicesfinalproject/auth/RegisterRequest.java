package com.example.webservicesfinalproject.auth;
import com.example.webservicesfinalproject.Entity.Employee;
import com.example.webservicesfinalproject.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;
    private Role role;
    private String userPhoneNumber;
    private LocalDateTime joinDate;
    private Employee emp;
}
