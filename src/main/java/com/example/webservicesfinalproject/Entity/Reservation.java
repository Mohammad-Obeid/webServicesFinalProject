package com.example.webservicesfinalproject.Entity;

import com.example.webservicesfinalproject.Serializers.ReservationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonSerialize(using = ReservationSerializer.class)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resID;
    private LocalDate checkinDate, checkoutDate;
    private String status = "reserved";
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Room> room;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    private double totPrice;
    public double countTotResPrice(){
        double x = 0;
        for(int i=0;i<getRoom().size();i++){
            x+=getRoom().get(i).getPrice();
        }
        LocalDate y = getCheckinDate();
        LocalDate z = getCheckoutDate();
        Long p= ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        if(p<0)p*=-1;
        return p*x;
    }
}
