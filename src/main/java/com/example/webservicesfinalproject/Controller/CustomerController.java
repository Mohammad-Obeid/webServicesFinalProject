package com.example.webservicesfinalproject.Controller;

import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Entity.*;
import com.example.webservicesfinalproject.Service.CustomerService;
import com.example.webservicesfinalproject.Service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService custServ;
    private final userService userservice;

    public CustomerController(CustomerService custServ, userService userservice) {
        this.custServ = custServ;
        this.userservice = userservice;
    }

    @Operation(summary = "Get customer details", description = "Retrieves the details of a customer by their customer ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer details"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{custID}")
    public ResponseEntity<UserDTO> getCustomer(@PathVariable("custID") int custID) {
        Optional<UserDTO> cust = Optional.ofNullable(custServ.getcustomer(custID));
        return cust.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Customer login", description = "Logs in a customer using their login details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in"),
            @ApiResponse(responseCode = "404", description = "Login failed")
    })
    @PostMapping("")
    public ResponseEntity<Boolean> Login(@RequestBody CustomerLogin custlogin) {
        Optional<Boolean> cust = Optional.ofNullable(custServ.Login(custlogin));
        return cust.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Register a new customer", description = "Registers a new customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the new customer"),
            @ApiResponse(responseCode = "302", description = "Customer registration was found to be unnecessary (duplicate or other reason)")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> CreateNewCustomer(@RequestBody UserDTO newUser) {
        Optional<UserDTO> user = Optional.ofNullable(userservice.CreateNewCustomer(newUser));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }

    @Operation(summary = "Update customer information", description = "Updates the information of a customer by their customer ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the customer information"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping("/{custID}")
    public ResponseEntity<UserDTO> updateCustInfo(@PathVariable("custID") int custID,
                                                  @RequestBody UserDTO user) {
        Optional<UserDTO> cust = Optional.ofNullable(custServ.updateCustInfo(custID, user));
        return cust.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Get free rooms", description = "Retrieves a list of free rooms available for customers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the rooms"),
            @ApiResponse(responseCode = "404", description = "Rooms not found")
    })
    @GetMapping("rooms")
    public ResponseEntity<List<Room>> getFreeRooms() {
        Optional<List<Room>> rooms = Optional.ofNullable(custServ.getFreeRoomsForCustomer());
        return rooms.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Change customer password", description = "Changes the password of a customer by their customer ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully changed the customer password"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping("/changePass/{custID}")
    public ResponseEntity<UserDTO> changeCustPassword(@PathVariable("custID") int custID,
                                                      @RequestBody UserDTO userdto) {
        Optional<UserDTO> cust = Optional.ofNullable(custServ.changeCustPassword(custID, userdto));
        return cust.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Reserve a room", description = "Reserves a room based on the provided reservation details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully reserved the room"),
            @ApiResponse(responseCode = "404", description = "Room reservation failed")
    })
    @PostMapping("room")
    public ResponseEntity<List<Room>> ReserveRoom(@RequestBody Reservation reservation) {
        Optional<List<Room>> room = Optional.ofNullable(custServ.ReserveRoom(reservation));
        return room.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Cancel a reservation", description = "Cancels a reservation by its reservation ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully canceled the reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PostMapping("res/{resID}")
    public ResponseEntity<Reservation> CancelReservation(@PathVariable("resID") int resID) {
        Optional<Reservation> res = Optional.ofNullable(custServ.CancelReservation(resID));
        return res.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Print invoice", description = "Prints the invoice for a reservation by its reservation ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully printed the invoice"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    @GetMapping("invoice/{resID}")
    public ResponseEntity<Invoice> printInvoice(@PathVariable("resID") int resID) {
        Optional<Invoice> invoice = Optional.ofNullable(custServ.printInvoice(resID));
        return invoice.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Call housekeeping", description = "Requests housekeeping services for a room by customer ID and room ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully requested housekeeping"),
            @ApiResponse(responseCode = "404", description = "Housekeeping request failed")
    })
    @PostMapping("housekeeping/{custID}/{roomID}")
    public ResponseEntity<HouseKeepingTasks> CallHouseKeeper(@PathVariable("custID") int custID,
                                                             @PathVariable("roomID") int roomID) {
        Optional<HouseKeepingTasks> invoice = Optional.ofNullable(custServ.CallHouseKeeper(custID, roomID));
        return invoice.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Cancel housekeeping", description = "Cancels a housekeeping request by customer ID and room ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully canceled housekeeping"),
            @ApiResponse(responseCode = "404", description = "Housekeeping cancellation failed")
    })
    @DeleteMapping("housekeeping/{custID}/{roomID}")
    public ResponseEntity<HouseKeepingTasks> CancelHouseKeeping(@PathVariable("custID") int custID,
                                                                @PathVariable("roomID") int roomID) {
        Optional<HouseKeepingTasks> invoice = Optional.ofNullable(custServ.CancelHouseKeeping(custID, roomID));
        return invoice.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}
