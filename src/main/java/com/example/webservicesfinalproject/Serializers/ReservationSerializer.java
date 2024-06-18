package com.example.webservicesfinalproject.Serializers;

import com.example.webservicesfinalproject.Entity.Reservation;
import com.example.webservicesfinalproject.Entity.Room;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReservationSerializer extends JsonSerializer<Reservation> {
    @Override
    public void serialize(Reservation reservation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("Reservation Status", reservation.getStatus());
        jsonGenerator.writeStringField("Check-in Date", reservation.getCheckinDate().toString());
        jsonGenerator.writeStringField("Check-out Date", reservation.getCheckoutDate().toString());
        jsonGenerator.writeStringField("Customer Name", reservation.getCustomer().getUser().getUsername());

        jsonGenerator.writeArrayFieldStart("Rooms");
        for (Room room : reservation.getRoom()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("Room ID", room.getRoomID());
            jsonGenerator.writeNumberField("Room Price/Night", room.getPrice());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeNumberField("Total Price", reservation.countTotResPrice());

        jsonGenerator.writeEndObject();
    }
}
