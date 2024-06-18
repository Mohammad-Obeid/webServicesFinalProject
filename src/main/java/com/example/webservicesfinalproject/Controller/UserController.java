package com.example.webservicesfinalproject.Controller;

import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Entity.Room;
import com.example.webservicesfinalproject.Entity.User;
import com.example.webservicesfinalproject.Service.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private final userService userservice;

    public UserController(userService userservice) {
        this.userservice = userservice;
    }

    @GetMapping("/userID")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable int userID){
        Optional<UserDTO> user= Optional.ofNullable(userservice.getUser(userID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @PostMapping("/addCust")
    public ResponseEntity<UserDTO> CreateNewCustomer(@RequestBody UserDTO newUser){
        Optional<UserDTO> user= Optional.ofNullable(userservice.CreateNewCustomer(newUser));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }

    //Admin Option 1
    @PostMapping("/addEmp")
    public ResponseEntity<UserDTO> CreateNewEmployee(@RequestBody UserDTO newUser){
        Optional<UserDTO> user= Optional.ofNullable(userservice.CreateNewEmployee(newUser));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }

    //Admin Option 2
    @GetMapping("customer/{custName}")
    public ResponseEntity<List<UserDTO>> getCustomerbyName(@PathVariable("custName") String custName){
        Optional<List<UserDTO>> user= Optional.ofNullable(userservice.getCustomerbyName(custName));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    //Admin Option 3
    @GetMapping("searchcustomer/{custemail}")
    public ResponseEntity<UserDTO> getCustomerbyEmail(@PathVariable("custemail") String custemail){
        Optional<UserDTO> user= Optional.ofNullable(userservice.getCustomerbyEmail(custemail));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    //Admin Option 4
    @GetMapping("rooms")
    public ResponseEntity<List<Room>> getFreeRooms(){
        Optional<List<Room>> rooms= Optional.ofNullable(userservice.getFreeRooms());
        return rooms.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    //Admin Option 5
    @PostMapping("room")
    public ResponseEntity<Room> AddNewRoom(@RequestBody Room room){
        Optional<Room> rooms= Optional.ofNullable(userservice.AddNewRoom(room));
        return rooms.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

}
