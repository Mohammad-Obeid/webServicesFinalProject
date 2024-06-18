package com.example.webservicesfinalproject.Serializers;

import com.example.webservicesfinalproject.Entity.Invoice;
import com.example.webservicesfinalproject.Entity.Reservation;
import com.example.webservicesfinalproject.Entity.Room;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InvoiceSerializer extends JsonSerializer<Invoice> {
    @Override
    public void serialize(Invoice invoice, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("Check-in Date", invoice.getRes().getCheckinDate().toString());
        jsonGenerator.writeStringField("Check-out Date", invoice.getRes().getCheckoutDate().toString());
        jsonGenerator.writeStringField("Customer Name", invoice.getRes().getCustomer().getUser().getUsername());

        LocalDate checkinDate = invoice.getRes().getCheckinDate();
        LocalDate checkoutDate = invoice.getRes().getCheckoutDate();
        long daysBetween = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        double totprice = 0;
        jsonGenerator.writeArrayFieldStart("Rooms");
        for (Room room : invoice.getRes().getRoom()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("Room ID", room.getRoomID());
            jsonGenerator.writeNumberField("Room Price/Night", room.getPrice());
            jsonGenerator.writeNumberField("Room Total Price", room.getPrice() * daysBetween);
            jsonGenerator.writeEndObject();
            totprice+=room.getPrice() * daysBetween;
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeNumberField("Total Price", totprice);
        jsonGenerator.writeEndObject();
    }
}
