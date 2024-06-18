package com.example.webservicesfinalproject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resID;
    private String checkinDate, checkoutDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
}
