package com.example.webservicesfinalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private int reservationID;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private String status;
    private List<Integer> roomIDs;
    private int customerID;
    private double totalPrice;
}
