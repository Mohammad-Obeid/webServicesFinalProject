package com.example.webservicesfinalproject.Service;
import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Entity.*;
import com.example.webservicesfinalproject.Repository.*;
import org.springframework.scheduling.config.Task;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository custRepo;
    private final UserRepository userRepo;
    private final userService usServ;
    private final RoomRepository roomRepo;
    private final ReservationRepository resRepo;
    private final InvoiceRepoistory invRepo;
    private final TaskRepository taskRepo;

    public CustomerService(CustomerRepository custRepo,
                           UserRepository userRepo,
                           userService usServ,
                           RoomRepository roomRepo,
                           ReservationRepository resRepo,
                           InvoiceRepoistory invRepo,
                           TaskRepository taskRepo) {
        this.custRepo = custRepo;
        this.userRepo = userRepo;
        this.usServ=usServ;
        this.roomRepo=roomRepo;
        this.resRepo=resRepo;
        this.invRepo=invRepo;
        this.taskRepo=taskRepo;
    }

    public UserDTO getcustomer(int custID) {
        Optional<Customer> cust = custRepo.findById(custID);
        if(cust.isEmpty()){
            return null;
        }
        User user = cust.get().getUser();
        UserDTO usDto = usServ.mapToDto(user);
        return usDto;
    }

    public Boolean Login(CustomerLogin custlogin) {
        Optional<User> user = userRepo.findByUserEmail(custlogin.getUserEmail());
        if(user.isPresent()){
            BCryptPasswordEncoder ps = new BCryptPasswordEncoder();
            if(ps.matches(custlogin.getUserPassword(),user.get().getUserPassword())){
                return true;
            }
            return null;
        }
        return null;
    }

    public UserDTO updateCustInfo(int custID,UserDTO userdt) {
        Optional<Customer> cust = custRepo.findById(custID);
        if(cust.isEmpty()){
            return null;
        }
        User user = cust.get().getUser();
        Optional<User> check = userRepo.findByUserEmail(userdt.getUserEmail());
        if(check.isPresent()) return null;
        user.setUserEmail(userdt.getUserEmail());
        user.setUserPhoneNumber(userdt.getUserPhoneNumber());
        user.setUserName(userdt.getUserName());
        UserDTO usDto = usServ.mapToDto(user);
        userRepo.save(user);
        return usDto;
    }

    public UserDTO changeCustPassword(int custID, UserDTO userdto) {
        Optional<Customer> cust = custRepo.findById(custID);
        if(cust.isEmpty()){
            return null;
        }
        User user = cust.get().getUser();
        BCryptPasswordEncoder passencrypted = new BCryptPasswordEncoder();
        user.setUserPassword(passencrypted.encode(userdto.getUserPassword()));
        UserDTO usDto = usServ.mapToDto(user);
        userRepo.save(user);
        return usDto;
    }

    public List<Room> ReserveRoom(Reservation reservation) {
        Optional<Customer> cust = custRepo.findById(reservation.getCustomer().getCustomerID());
        if(cust.isPresent()) {
            if (reservation.getCheckinDate().isEqual(LocalDate.now()) ||
                    reservation.getCheckinDate().isAfter(LocalDate.now())) {
                LocalDate x =reservation.getCheckinDate();
                LocalDate y = reservation.getCheckoutDate();
                Long dist = ChronoUnit.DAYS.between(x,y);
                double totPrice=0;
                reservation.setCustomer(cust.get());
                List<Room> rooms = new ArrayList<>();
                for (int i = 0; i < reservation.getRoom().size(); i++) {
                    Optional<Room> room = roomRepo.findById(reservation.getRoom().get(i).getRoomID());
                    if (room.isPresent()) {
                        totPrice+=room.get().getPrice()*dist;

                        if (room.get().getStatus().toLowerCase().trim().equals("free")) {
                            room.get().setStatus("reserved");
                            rooms.add(reservation.getRoom().get(i));
                            roomRepo.save(room.get());
                        }
                    }
                }
                reservation.setRoom(rooms);
                reservation.setTotPrice(totPrice);
                resRepo.save(reservation);
                return rooms;
            }
        }
        return null;
    }







    public Reservation CancelReservation(int resID) {
        Optional<Reservation> res = resRepo.findById(resID);
        if(res.isPresent()){
            res.get().setStatus("Awaiting Admin Approval \"For Cancellation\"");
            resRepo.save(res.get());
            return res.get();
        }
        return null;
    }

    public List<Room> getFreeRoomsForCustomer() {
        return null;
    }

    public Invoice printInvoice(int resID) {
        Optional<Reservation> res = resRepo.findById(resID);
        if(res.isPresent()) {
            Optional<Invoice> invoice = invRepo.findByResResID(resID);
            if(invoice.isEmpty()){
            Invoice in = new Invoice();
            in.setRes(res.get());
            in.setInvoceDate(LocalDate.now());
            in.setTotPrice(res.get().getTotPrice());
            invRepo.save(in);
            return in;}
            return invoice.get();
        }
    return null;
    }

    public HouseKeepingTasks CallHouseKeeper(int custID, int roomID) {
        Optional<Customer> customer = custRepo.findById(custID);
        if(customer.isPresent()){
            Optional<Room> room = roomRepo.findById(roomID);
            if(room.isPresent()){
                Optional<Reservation> res = resRepo.findByCustomerUserUserNameAndCheckoutDateGreaterThan(customer.get().getUser().getUsername(),LocalDate.now());
                List<Room> rooms = res.get().getRoom();
                if(rooms.contains(room.get())){
                    Optional<HouseKeepingTasks> ttCheck = taskRepo.findByRoomRoomID(room.get().getRoomID());
                    if(ttCheck.isEmpty() || (ttCheck.isPresent() && !ttCheck.get().getStatus().equals("Waiting for Free HouseKeeper"))){
                    HouseKeepingTasks task = new HouseKeepingTasks();
                    task.setCust(customer.get());
                    task.setRoom(room.get());
                    task.setEmp(null);
                    task.setStatus("Waiting for Free HouseKeeper");
                    taskRepo.save(task);
                    return task;}
                }
            }
        }
        return null;
    }

    public HouseKeepingTasks CancelHouseKeeping(int custID, int roomID) {
        Optional<Customer> customer = custRepo.findById(custID);
        if(customer.isPresent()){
            Optional<Room> room = roomRepo.findById(roomID);
            if(room.isPresent()){
                Optional<Reservation> res = resRepo.findByCustomerUserUserNameAndCheckoutDateGreaterThan(customer.get().getUser().getUsername(),LocalDate.now());
                List<Room> rooms = res.get().getRoom();
                if(rooms.contains(room.get())){
                    Optional<HouseKeepingTasks> ttCheck = taskRepo.findByRoomRoomID(room.get().getRoomID());
                    if(ttCheck.isPresent() && ttCheck.get().getStatus().equals("Waiting for Free HouseKeeper")){
                        ttCheck.get().setStatus("Cancelled");
                        HouseKeepingTasks task = new HouseKeepingTasks();
                        task.setTaskID(ttCheck.get().getTaskID());
                        task.setCust(ttCheck.get().getCust());
                        task.setEmp(ttCheck.get().getEmp());
                        task.setStatus(ttCheck.get().getStatus());
                        task.setRoom(ttCheck.get().getRoom());
                        ttCheck.get().setCust(null);
                        ttCheck.get().setEmp(null);
                        ttCheck.get().setRoom(null);
                        taskRepo.delete(ttCheck.get());
                        return task;
                    }
                }
            }
        }
        return null;
    }
}