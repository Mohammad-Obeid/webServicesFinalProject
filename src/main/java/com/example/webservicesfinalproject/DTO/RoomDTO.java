package com.example.webservicesfinalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private int roomID;
    private String type;
    private String feature;
    private String size;
    private String status;
    private int capacity;
    private double price;
    private int roomFloor;
    private int numOfBeds;
}
