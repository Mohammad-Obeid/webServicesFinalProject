package com.example.webservicesfinalproject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomID;
    private String type,feature,size;
    private String status = "free";
    private int capacity;
    private double price;
    private int roomFloor, numOfBeds;

}
