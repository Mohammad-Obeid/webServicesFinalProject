package com.example.webservicesfinalproject.Serializers;

import com.example.webservicesfinalproject.Entity.HouseKeepingTasks;
import com.example.webservicesfinalproject.Entity.Room;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.Serializers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TaskSerializer extends JsonSerializer<HouseKeepingTasks> {
    @Override
    public void serialize(HouseKeepingTasks Task, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("Customer Name", Task.getCust().getUser().getUsername());
        jsonGenerator.writeStringField("Room ID", String.valueOf(Task.getRoom().getRoomID()));
        if(Task.getEmp()==null)
            jsonGenerator.writeStringField("Employee Name", "Not Assigned Yet");
        else
            jsonGenerator.writeStringField("Employee Name", Task.getEmp().getUser().getUsername());
        jsonGenerator.writeStringField("Task Status", Task.getStatus());
        jsonGenerator.writeEndObject();
        }

}
