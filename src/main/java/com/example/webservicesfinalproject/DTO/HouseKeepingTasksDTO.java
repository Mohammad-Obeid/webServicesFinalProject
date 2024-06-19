package com.example.webservicesfinalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseKeepingTasksDTO {
    private int taskID;
    private int employeeID;
    private int customerID;
    private int roomID;
    private String status;
}
