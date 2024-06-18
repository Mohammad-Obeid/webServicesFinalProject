package com.example.webservicesfinalproject.Controller;

import com.example.webservicesfinalproject.Entity.HouseKeepingTasks;
import com.example.webservicesfinalproject.Entity.WorkShift;
import com.example.webservicesfinalproject.Service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final userService userservice;

    public EmployeeController(userService userservice) {
        this.userservice = userservice;
    }

    @Operation(summary = "Assign housekeeping task", description = "Assigns a housekeeping task to an employee for a specific customer and room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully assigned the housekeeping task"),
            @ApiResponse(responseCode = "404", description = "Failed to assign housekeeping task")
    })
    @PostMapping("/Task/{custID}/{roomID}/{empID}")
    public ResponseEntity<HouseKeepingTasks> AssignHouseKeeper(@PathVariable("custID") int custID,
                                                               @PathVariable("roomID") int roomID,
                                                               @PathVariable("empID") int empID) {
        Optional<HouseKeepingTasks> task = Optional.ofNullable(userservice.AssignHouseKeeper(custID, roomID, empID));
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Mark housekeeping task as finished", description = "Marks a housekeeping task as finished for a specific customer, room, and employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully marked the task as finished"),
            @ApiResponse(responseCode = "404", description = "Failed to mark the task as finished")
    })
    @PostMapping("/finishTask/{custID}/{roomID}/{empID}")
    public ResponseEntity<HouseKeepingTasks> TaskFinished(@PathVariable("custID") int custID,
                                                          @PathVariable("roomID") int roomID,
                                                          @PathVariable("empID") int empID) {
        Optional<HouseKeepingTasks> task = Optional.ofNullable(userservice.TaskFinished(custID, roomID, empID));
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Operation(summary = "Start Working Shift", description = "fingerprint for employees to start counting working hours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully started the shift"),
            @ApiResponse(responseCode = "404", description = "Failed to start the shift")
    })
    @PostMapping("/shift/{empID}")
    public ResponseEntity<WorkShift> StartWorkingShift(
                                                          @PathVariable("empID") int empID) {
        Optional<WorkShift> task = Optional.ofNullable(userservice.StartWorkingShift(empID));
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @Operation(summary = "finish Working Shift", description = "fingerprint for employees to finish counting working hours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully finished the shift"),
            @ApiResponse(responseCode = "404", description = "Failed to finish the shift")
    })
    @PostMapping("/endshift/{empID}")
    public ResponseEntity<WorkShift> EndWorkingShift(
            @PathVariable("empID") int empID) {
        Optional<WorkShift> task = Optional.ofNullable(userservice.EndWorkingShift(empID));
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


}
