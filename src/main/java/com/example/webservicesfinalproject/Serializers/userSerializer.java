package com.example.webservicesfinalproject.Serializers;

import com.example.webservicesfinalproject.DTO.UserDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class userSerializer extends JsonSerializer<UserDTO> {
    @Override
    public void serialize(UserDTO userDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        Map<String, Object> user = new HashMap<>();
        user.put("userName", userDTO.getUserName());
        user.put("userEmail", userDTO.getUserEmail());
        user.put("phoneNumber", userDTO.getUserPhoneNumber());
        jsonGenerator.writeObjectField("user", user);

    }
}
