package com.example.webservicesfinalproject.Controller;

import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Entity.Customer;
import com.example.webservicesfinalproject.Entity.CustomerLogin;
import com.example.webservicesfinalproject.Service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService custServ;

    public CustomerController(CustomerService custServ) {
        this.custServ = custServ;
    }
    @GetMapping("/{custID}")
    public ResponseEntity<UserDTO> getCustomer(@PathVariable("custID") int custID){
        Optional<UserDTO> cust= Optional.ofNullable(custServ.getcustomer(custID));
        return cust.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> Login(@RequestBody CustomerLogin custlogin){
        Optional<Boolean> cust= Optional.ofNullable(custServ.Login(custlogin));
        return cust.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @PutMapping("/{custID}")
    public ResponseEntity<UserDTO> updateCustInfo(@PathVariable("custID") int custID,
                                                  @RequestBody UserDTO user){
        Optional<UserDTO> cust= Optional.ofNullable(custServ.updateCustInfo(custID,user));
        return cust.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PutMapping("/changePass/{custID}")
    public ResponseEntity<UserDTO> changeCustPassword(@PathVariable("custID") int custID,
                                                  @RequestBody UserDTO userdto){
        Optional<UserDTO> cust= Optional.ofNullable(custServ.changeCustPassword(custID, userdto));
        return cust.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


}
