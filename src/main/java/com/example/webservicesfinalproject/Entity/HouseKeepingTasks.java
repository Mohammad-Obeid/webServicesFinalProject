package com.example.webservicesfinalproject.Entity;

import com.example.webservicesfinalproject.Serializers.InvoiceSerializer;
import com.example.webservicesfinalproject.Serializers.TaskSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "housekeepingtasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonSerialize(using = TaskSerializer.class)
public class HouseKeepingTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskID;
    @ManyToOne(cascade = CascadeType.ALL)
    private Employee emp;
    @ManyToOne(cascade = CascadeType.ALL)
    private  Customer cust;
    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;
    private String status;

}
