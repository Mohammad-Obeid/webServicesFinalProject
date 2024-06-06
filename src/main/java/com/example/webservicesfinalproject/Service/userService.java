package com.example.webservicesfinalproject.Service;

import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Entity.Customer;
import com.example.webservicesfinalproject.Entity.Employee;
import com.example.webservicesfinalproject.Entity.Room;
import com.example.webservicesfinalproject.Entity.User;
import com.example.webservicesfinalproject.Repository.RoomRepository;
import com.example.webservicesfinalproject.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class userService {
    private final UserRepository userRepo;

    private final RoomRepository roomRepo;

    public userService(UserRepository userRepo,
                       RoomRepository roomRepo) {
        this.userRepo = userRepo;
        this.roomRepo=roomRepo;
    }


    public User mapToEntity(UserDTO userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(userDto.getUserPassword());
        user.setUserPhoneNumber(userDto.getUserPhoneNumber());
        user.setUserRole(userDto.getUserRole());
        return user;
    }

    public UserDTO mapToDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setUserName(user.getUserName());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserPassword(user.getUserPassword());
        userDto.setUserPhoneNumber(user.getUserPhoneNumber());
        userDto.setUserRole(user.getUserRole());
        return userDto;
    }

    public UserDTO getUser(int userID) {
        Optional<User> user = userRepo.findById(userID);
        UserDTO userDto = mapToDto(user.get());
        return userDto;
    }

    public UserDTO CreateNewCustomer(UserDTO newUser) {
        Optional<User> user = userRepo.findByUserEmail(newUser.getUserEmail());
        if(user.isPresent()){
            return null;
        }
        User us = mapToEntity(newUser);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        us.setUserPassword(encoder.encode(us.getUserPassword()));
        us.setUserRole("customer");
        Customer cust = new Customer();
        cust.setUser(us);
        us.setCustomer(cust);
        us.setJoinDate(LocalDateTime.now());
        userRepo.save(us);
        return mapToDto(us);
    }

    public UserDTO CreateNewEmployee(UserDTO newUser) {
        Optional<User> user = userRepo.findByUserEmail(newUser.getUserEmail());
        if(user.isPresent()){
            return null;
        }
        User us = mapToEntity(newUser);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        us.setUserPassword(encoder.encode(us.getUserPassword()));
        us.setUserRole("employee");
        Employee emp = new Employee();
        emp.setUser(us);
        emp.setEmployeeRole(newUser.getEmp().getEmployeeRole());
        us.setEmployee(emp);
        us.setJoinDate(LocalDateTime.now());
        userRepo.save(us);
        return mapToDto(us);
    }

    public List<UserDTO> getCustomerbyName(String custName) {
        Optional<List<User>> user = userRepo.findAllByUserNameAndUserRole(custName,"customer");
        List<UserDTO> userDto = new ArrayList<>();
        for(int i=0;i<user.get().size();i++){
            UserDTO usdto=mapToDto(user.get().get(i));
            System.out.println(usdto.toString());
            userDto.add(usdto);
        }
        return userDto;
    }

    public UserDTO getCustomerbyEmail(String custemail) {
        Optional<User> user = userRepo.findByUserEmail(custemail);
        UserDTO userdto = mapToDto(user.get());
        return userdto;
    }

    public List<Room> getFreeRooms() {
        Optional<List<Room>> rooms = roomRepo.findAllByStatus("free");
        return rooms.get();
    }

    public Room AddNewRoom(Room room) {
        Room rr = roomRepo.save(room);
        return rr;
    }
}
