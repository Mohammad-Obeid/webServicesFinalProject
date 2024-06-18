package com.example.webservicesfinalproject.Entity;

import com.example.webservicesfinalproject.Serializers.InvoiceSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonSerialize(using = InvoiceSerializer.class)
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceID;
    private double totPrice;
    private LocalDate invoceDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Reservation res;
}
