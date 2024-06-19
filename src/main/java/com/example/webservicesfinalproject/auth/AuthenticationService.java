package com.example.webservicesfinalproject.auth;
import com.example.webservicesfinalproject.Configuration.JwtService;
import com.example.webservicesfinalproject.Entity.Customer;
import com.example.webservicesfinalproject.Entity.Employee;
import com.example.webservicesfinalproject.Entity.Role;
import com.example.webservicesfinalproject.Entity.User;
import com.example.webservicesfinalproject.Repository.UserRepository;
import com.example.webservicesfinalproject.token.Token;
import com.example.webservicesfinalproject.token.TokenRepository;
import com.example.webservicesfinalproject.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

//  private final CustomerService customerService;
//
//  public AuthenticationResponse register(RegisterRequest request) {
//    var customerDto=new CustomerDTO();
//    customerDto.setFirstName(request.getFirstname());
//    customerDto.setLastName(request.getLastname());
//    customerDto.setBornAt(request.getBornAt());
//
//    var customer= customerService.createCustomer(customerDto);
//
//    var user = User.builder()
//        .firstname(request.getFirstname())
//        .lastname(request.getLastname())
//        .email(request.getEmail())
//        .password(passwordEncoder.encode(request.getPassword()))
//        .customerId(customer.getId())
//        .role(Role.CUSTOMER)
//        .build();
//    var savedUser = repository.save(user);
//    var jwtToken = jwtService.generateToken(user);
//    var refreshToken = jwtService.generateRefreshToken(user);
//    saveUserToken(savedUser, jwtToken);
//    return AuthenticationResponse.builder()
//        .accessToken(jwtToken)
//            .refreshToken(refreshToken)
//        .build();
//  }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .userName(request.getUserName())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .userEmail(request.getEmail())
                .role(request.getRole())
                .userPhoneNumber(request.getUserPhoneNumber())
                .joinDate(LocalDateTime.now())
                .build();
        User savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        if(user.getRole()== Role.EMPLOYEE){
            Employee emp = new Employee();
            emp.setUser(savedUser);
            emp.setEmployeeRole(request.getEmp().getEmployeeRole());
//            empRepo.save(emp);
        }
        else{
            Customer customer = new Customer();
            customer.setUser(savedUser);
//            custRepo.save(customer);
        }
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse  authenticate(AuthenticationRequest request) {
        String email=request.getEmail();
        System.out.println("email:"+email);
        System.out.println("pass:"+request.getPassword());
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        System.out.println(auth.toString());
        var user = repository.findByUserEmail(email)
                .orElseThrow();
        System.out.println(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserID());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByUserEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}