package com.example.webservicesfinalproject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "workshift")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shiftID;
    private LocalDate workDay;
    private LocalTime startTime,finishTime;
    @Transient
    private double totalHours;
    @ManyToOne(cascade = CascadeType.ALL)
    private Employee employee;

}
