package com.example.webservicesfinalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private int invoiceID;
    private double totalPrice;
    private LocalDate invoiceDate;
    private int reservationID;
}
