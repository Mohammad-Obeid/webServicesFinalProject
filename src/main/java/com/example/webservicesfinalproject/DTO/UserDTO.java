package com.example.webservicesfinalproject.DTO;
import com.example.webservicesfinalproject.Entity.Role;
import com.example.webservicesfinalproject.Serializers.userSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonSerialize(using = userSerializer.class)
public class UserDTO {
    @JsonIgnore
    private int userID;
    private String userName;
    private String userEmail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    private String userPhoneNumber;
    @JsonIgnore
    private LocalDateTime joinDate;
    private EmployeeDTO emp;

}
