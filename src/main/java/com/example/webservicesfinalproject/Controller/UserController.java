package com.example.webservicesfinalproject.Controller;

import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private final userService userservice;

    public UserController(userService userservice) {
        this.userservice = userservice;
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable int userID){
        Optional<UserDTO> user = Optional.ofNullable(userservice.getUser(userID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Register a new customer", description = "Registers a new customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the new customer"),
            @ApiResponse(responseCode = "302", description = "Customer registration was found to be unnecessary (duplicate or other reason)")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> CreateNewCustomer(@RequestBody UserDTO newUser
                                                     ) {
        Optional<UserDTO> user = Optional.ofNullable(userservice.CreateNewEmployee(newUser));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }


}
