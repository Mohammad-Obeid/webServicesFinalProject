package com.example.webservicesfinalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShiftDTO {
    private int shiftID;
    private LocalDate workDay;
    private LocalTime startTime;
    private LocalTime finishTime;
    private double totalHours;
    private int employeeId;
}
