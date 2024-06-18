package com.example.webservicesfinalproject.Controller;

import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Entity.Employee;
import com.example.webservicesfinalproject.Entity.HouseKeepingTasks;
import com.example.webservicesfinalproject.Entity.Reservation;
import com.example.webservicesfinalproject.Entity.Room;
import com.example.webservicesfinalproject.Service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final userService userservice;

    public AdminController(userService userservice) {
        this.userservice = userservice;
    }

    // Admin Option 1
    @Operation(summary = "Create a new employee", description = "Creates a new employee and associates it with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the new employee"),
            @ApiResponse(responseCode = "302", description = "Employee creation was found to be unnecessary (duplicate or other reason)")
    })
    @PostMapping("/{adminID}/Emp")
    public ResponseEntity<UserDTO> CreateNewEmployee(@RequestBody UserDTO newUser,
                                                     @PathVariable("adminID") int adminID) {
        Optional<UserDTO> user = Optional.ofNullable(userservice.CreateNewEmployee(newUser, adminID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }

    @Operation(summary = "Delete an employee", description = "Deletes an employee associated with the given admin ID and employee ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the employee"),
            @ApiResponse(responseCode = "302", description = "Employee deletion was found to be unnecessary (duplicate or other reason)")
    })
    @DeleteMapping("/Emp/{adminID}/{empID}")
    public ResponseEntity<UserDTO> DeleteEmployee(@PathVariable("adminID") int adminID,
                                                  @PathVariable("empID") int empID) {
        Optional<UserDTO> user = Optional.ofNullable(userservice.DeleteEmployee(adminID, empID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }

    // Admin Option 2
    @Operation(summary = "Get customers by name", description = "Retrieves a list of customers with the given name associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customers"),
            @ApiResponse(responseCode = "404", description = "Customers not found")
    })
    @GetMapping("customer/{adminID}/{custName}")
    public ResponseEntity<List<UserDTO>> getCustomerbyName(@PathVariable("custName") String custName,
                                                           @PathVariable("adminID") int adminID) {
        Optional<List<UserDTO>> user = Optional.ofNullable(userservice.getCustomerbyName(custName, adminID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // Admin Option 3
    @Operation(summary = "Get customer by email", description = "Retrieves a customer with the given email associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("searchcustomer/{adminID}/{custemail}")
    public ResponseEntity<UserDTO> getCustomerbyEmail(@PathVariable("custemail") String custemail,
                                                      @PathVariable("adminID") int adminID) {
        Optional<UserDTO> user = Optional.ofNullable(userservice.getCustomerbyEmail(custemail, adminID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // Admin Option 4
    @Operation(summary = "Get free rooms", description = "Retrieves a list of free rooms associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the rooms"),
            @ApiResponse(responseCode = "404", description = "Rooms not found")
    })
    @GetMapping("rooms/{adminID}")
    public ResponseEntity<List<Room>> getFreeRooms(@PathVariable("adminID") int adminID) {
        Optional<List<Room>> rooms = Optional.ofNullable(userservice.getFreeRooms(adminID));
        return rooms.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // Admin Option 5
    @Operation(summary = "Add a new room", description = "Adds a new room associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the new room"),
            @ApiResponse(responseCode = "404", description = "Room addition failed")
    })
    @PostMapping("room/{adminID}")
    public ResponseEntity<Room> AddNewRoom(@RequestBody Room room,
                                           @PathVariable("adminID") int adminID) {
        Optional<Room> rooms = Optional.ofNullable(userservice.AddNewRoom(room, adminID));
        return rooms.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // Admin Option 6
    @Operation(summary = "Cancel a reservation", description = "Cancels a reservation with the given reservation ID associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully canceled the reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PostMapping("cancelres/{adminID}/{id}")
    public ResponseEntity<Reservation> CancelReservation(@PathVariable int id,
                                                         @PathVariable("adminID") int adminID) {
        Optional<Reservation> res = Optional.ofNullable(userservice.CancelReservation(id, adminID));
        return res.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Cannot cancel a reservation", description = "Indicates that a reservation with the given reservation ID cannot be canceled, associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully indicated the reservation cannot be canceled"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PostMapping("cantcancelres/{adminID}/{id}")
    public ResponseEntity<Reservation> CantCancelReservation(@PathVariable int id,
                                                             @PathVariable("adminID") int adminID) {
        Optional<Reservation> res = Optional.ofNullable(userservice.CantCancelReservation(id, adminID));
        return res.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Search customer reservation by name and date", description = "Searches for a customer reservation by customer name and reservation date associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @GetMapping("res/{adminID}/{custName}/{resDate}")
    public ResponseEntity<Reservation> SearchCustomerReservationByCustNameAndDate(@PathVariable("custName") String custName,
                                                                                  @PathVariable("resDate") LocalDate resDate,
                                                                                  @PathVariable("adminID") int adminID) {
        Optional<Reservation> res = Optional.ofNullable(userservice.SearchCustomerReservationByCustNameAndDate(custName, resDate, adminID));
        return res.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Check in", description = "Checks in a reservation with the given reservation ID and customer name, associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully checked in"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PostMapping("res/checkin/{adminID}/{resID}/{custName}")
    public ResponseEntity<Reservation> Checkin(@PathVariable("adminID") int adminID,
                                               @PathVariable("resID") int resID,
                                               @PathVariable("custName") String custName) {
        Optional<Reservation> res = Optional.ofNullable(userservice.Checkin(adminID, resID, custName));
        return res.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Check out", description = "Checks out a reservation with the given reservation ID and customer name, associated with the given admin ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully checked out"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PostMapping("res/checkout/{adminID}/{resID}/{custName}")
    public ResponseEntity<Reservation> Checkout(@PathVariable("adminID") int adminID,
                                                @PathVariable("resID") int resID,
                                                @PathVariable("custName") String custName) {
        Optional<Reservation> res = Optional.ofNullable(userservice.Checkout(adminID, resID, custName));
        return res.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }



    @Operation(summary = "Get Free HouseKeepers", description = "Get Free HouseKeepers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned Free Housekeepers"),
            @ApiResponse(responseCode = "404", description = "No Free Housekeepers")
    })
    @GetMapping("housekeeper/free/{adminID}")
    public ResponseEntity<List<Employee> > GetFreeHousekeepers(@PathVariable("adminID") int adminID) {
        Optional<List<Employee> > task = Optional.ofNullable(userservice.GetFreeHousekeepers(adminID));
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @Operation(summary = "Get Busy HouseKeepers", description = "Get Busy HouseKeepers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned Busy Housekeepers"),
            @ApiResponse(responseCode = "404", description = "No Busy Housekeepers")
    })
    @GetMapping("housekeeper/busy/{adminID}")
    public ResponseEntity<List<Employee> > GetBusyHousekeepers(@PathVariable("adminID") int adminID) {
        Optional<List<Employee>> task = Optional.ofNullable(userservice.GetBusyHousekeepers(adminID));
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Mark housekeeper as Busy Right now", description = "Marks a Housekeeper as Busy cleaning a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Assigned the task to free Housekeeper"),
            @ApiResponse(responseCode = "404", description = "Failed to mark the Housekeeper as Busy")
    })
    @PostMapping("/Task/{adminID}/{custID}/{roomID}/{empID}")
    public ResponseEntity<HouseKeepingTasks> AssignHouseKeeperByAdmin(@PathVariable("adminID") int adminID,
                                                                      @PathVariable("custID") int custID,
                                                               @PathVariable("roomID") int roomID,
                                                               @PathVariable("empID") int empID) {
        Optional<HouseKeepingTasks> task = Optional.ofNullable(userservice.AssignHouseKeeperByAdmin(adminID,custID, roomID, empID));
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Mark housekeeper as Free", description = "Marks a Housekeeper as Free and Ready to be Assigned a Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully marked the task as finished"),
            @ApiResponse(responseCode = "404", description = "Failed to mark the task as finished")
    })
    @PostMapping("/finishTask/{adminID}/{custID}/{roomID}/{empID}")
    public ResponseEntity<HouseKeepingTasks> TaskFinishedByAdmin(@PathVariable("adminID") int adminID,
                                                                 @PathVariable("custID") int custID,
                                                          @PathVariable("roomID") int roomID,
                                                          @PathVariable("empID") int empID) {
        Optional<HouseKeepingTasks> task = Optional.ofNullable(userservice.TaskFinishedByAdmin(adminID,custID, roomID, empID));
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}
