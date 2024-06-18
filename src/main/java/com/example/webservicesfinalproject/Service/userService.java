package com.example.webservicesfinalproject.Service;

import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Entity.*;
import com.example.webservicesfinalproject.Repository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class userService {
    private final UserRepository userRepo;

    private final RoomRepository roomRepo;
    private final ReservationRepository resRepo;
    private final CustomerRepository custRepo;
    private final TaskRepository taskRepo;
    private final EmployeeRepository empRepo;
    private final WorkShiftRepository shiftRepo;


    public userService(UserRepository userRepo,
                       RoomRepository roomRepo,
                       ReservationRepository resRepo,
                       CustomerRepository custRepo,
                       TaskRepository taskRepo,
                       EmployeeRepository empRepo,
                       WorkShiftRepository shiftRepo) {
        this.userRepo = userRepo;
        this.roomRepo=roomRepo;
        this.resRepo=resRepo;
        this.custRepo=custRepo;
        this.taskRepo=taskRepo;
        this.empRepo=empRepo;
        this.shiftRepo=shiftRepo;
    }


    public User mapToEntity(UserDTO userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(userDto.getUserPassword());
        user.setUserPhoneNumber(userDto.getUserPhoneNumber());
        user.setRole(Role.valueOf(userDto.getUserRole()));
        return user;
    }

    public UserDTO mapToDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setUserName(user.getUsername());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserPassword(user.getUserPassword());
        userDto.setUserPhoneNumber(user.getUserPhoneNumber());
        userDto.setUserRole(String.valueOf(user.getRole()));
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
        us.setRole(Role.Customer);
        Customer cust = new Customer();
        cust.setUser(us);
        us.setCustomer(cust);
        us.setJoinDate(LocalDateTime.now());
        userRepo.save(us);
        return mapToDto(us);
    }




    public UserDTO CreateNewEmployee(UserDTO newUser, int adminID) {
        Optional<User> user = userRepo.findByUserEmail(newUser.getUserEmail());
        Optional<User> usCheck = userRepo.findByEmployeeEmployeeID(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
        && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
            if (user.isPresent()) {
                return null;
            }
            User us = mapToEntity(newUser);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            us.setUserPassword(encoder.encode(us.getUserPassword()));
            us.setRole(Role.Employee);
            Employee emp = new Employee();
            emp.setUser(us);
            emp.setEmployeeRole(newUser.getEmp().getEmployeeRole());
            us.setEmployee(emp);
            us.setJoinDate(LocalDateTime.now());
            userRepo.save(us);
            return mapToDto(us);
        }
        return null;
    }


    public UserDTO DeleteEmployee(int adminID, int empID) {
        Optional<User> admin = userRepo.findByEmployeeEmployeeID(adminID);
        if(admin.isPresent()){
            if(admin.get().getRole()==Role.Employee && admin.get().getEmployee().getEmployeeRole().equals("Admin")){
                Optional<User> employee = userRepo.findByEmployeeEmployeeID(empID);
                if(employee.isPresent()){
                    UserDTO us = new UserDTO();
                    us.setUserName(employee.get().getUsername());
                    us.setUserName(employee.get().getUsername());
                    Optional<Employee> empp = empRepo.findByUserUserID(employee.get().getUserID());
                    empp.get().setUser(null);
                    empRepo.delete(empp.get());
                    userRepo.delete(employee.get());
                }
            }
        }
        return null;
    }

    public List<UserDTO> getCustomerbyName(String custName, int adminID) {
        Optional<List<User>> user = userRepo.findAllByUserName(custName);
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
        List<UserDTO> userDto = new ArrayList<>();
        for(int i=0;i<user.get().size();i++){
            UserDTO usdto=mapToDto(user.get().get(i));
            System.out.println(usdto.toString());
            userDto.add(usdto);
        }
        return userDto;
    }
        return null;
    }

    public UserDTO getCustomerbyEmail(String custemail, int adminID) {
        Optional<User> user = userRepo.findByUserEmail(custemail);
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
        UserDTO userdto = mapToDto(user.get());
        return userdto;}
        return null;
    }

    public List<Room> getFreeRooms(int adminID) {
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
        Optional<List<Room>> rooms = roomRepo.findAllByStatus("free");
        return rooms.get();}
        return null;
    }

    public Room AddNewRoom(Room room, int adminID) {
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent()
//                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
            System.out.println("/////////////////////////////////////////////////////////////////////");

        Room rr = roomRepo.save(room);
        return rr;}
        return null;
    }

    public Reservation CancelReservation(int id, int adminID) {
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
        Optional<Reservation> res = resRepo.findById(id);
        if(res.isPresent()){
            if(res.get().getStatus().equals("Awaiting Admin Approval \"For Cancellation\"")){
            res.get().setStatus("cancelled");
            resRepo.save(res.get());
            for(int i=0;i<res.get().getRoom().size();i++){
                Optional<Room> room = roomRepo.findById(res.get().getRoom().get(i).getRoomID());
                room.get().setStatus("free");
                roomRepo.save(room.get());
            }


            return res.get();}

        }
        }
        return null;
    }

    public Reservation CantCancelReservation(int id, int adminID) {
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
            Optional<Reservation> res = resRepo.findById(id);
            if (res.isPresent()) {
                if (res.get().getStatus().equals("Awaiting Admin Approval \"For Cancellation\"")) {
                    res.get().setStatus("can't cancel this reservation");
                    resRepo.save(res.get());
                    return res.get();
                }
            }
        }
        return null;
    }

    public Reservation SearchCustomerReservationByCustNameAndDate(String custName, LocalDate resDate, int adminID) {
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
        Optional<Reservation> reservation = resRepo.findByCustomerUserUserNameAndAndCheckinDate(custName,resDate);
        return reservation.orElse(null);}
        return null;
    }

    public Reservation Checkin(int adminID, int resID, String custName) {
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
        Optional<Customer> cust = custRepo.findByUserUserName(custName);
        Optional<Reservation> res = resRepo.findById(resID);
        if(cust.get().getCustomerID()==res.get().getCustomer().getCustomerID()){
            res.get().setCheckinDate(LocalDate.now());
            LocalDate x = res.get().getCheckinDate();
            LocalDate y = res.get().getCheckoutDate();
            Long dist = ChronoUnit.DAYS.between(x,y);
            double totPrice=0;
            for(int i=0;i<res.get().getRoom().size();i++){
                totPrice+=res.get().getRoom().get(i).getPrice()*dist;
            }
            res.get().setTotPrice(totPrice);
            resRepo.save(res.get());
            return res.get();
        }
        }
        return null;
    }

    public Reservation Checkout(int adminID, int resID, String custName) {
        Optional<User> usCheck = userRepo.findById(adminID);
        if(usCheck.isPresent() &&
                usCheck.get().getRole() == Role.Employee
                && usCheck.get().getEmployee().getEmployeeRole().equals("Admin")) {
            Optional<Customer> cust = custRepo.findByUserUserName(custName);
            Optional<Reservation> res = resRepo.findById(resID);
            if(cust.get().getCustomerID()==res.get().getCustomer().getCustomerID()){
                res.get().setCheckoutDate(LocalDate.now());
                LocalDate x = res.get().getCheckinDate();
                LocalDate y = res.get().getCheckoutDate();
                Long dist = ChronoUnit.DAYS.between(x,y);
                double totPrice=0;
                for(int i=0;i<res.get().getRoom().size();i++){
                    totPrice+=res.get().getRoom().get(i).getPrice()*dist;
                }
                res.get().setTotPrice(totPrice);
                resRepo.save(res.get());
                return res.get();
            }
        }
        return null;
    }

    public HouseKeepingTasks AssignHouseKeeper(int custID, int roomID,int empID) {
        Optional<Customer> customer = custRepo.findById(custID);
        if(customer.isPresent()){
            Optional<Room> room = roomRepo.findById(roomID);
            if(room.isPresent()){
                Optional<Reservation> res = resRepo.findByCustomerUserUserNameAndCheckoutDateGreaterThan(customer.get().getUser().getUsername(),LocalDate.now());
                List<Room> rooms = res.get().getRoom();
                if(rooms.contains(room.get())){
                    Optional<HouseKeepingTasks> ttCheck = taskRepo.findByRoomRoomID(room.get().getRoomID());
                    if(ttCheck.isPresent() && ttCheck.get().getStatus().equals("Waiting for Free HouseKeeper")){
                        Optional<User> emp = userRepo.findByEmployeeEmployeeID(empID);
                        if(emp.isPresent()) {
                            ttCheck.get().setStatus("Cleaning Room!");
                            ttCheck.get().setEmp(emp.get().getEmployee());
                            taskRepo.save(ttCheck.get());
                            emp.get().getEmployee().setStatus("busy");
                            emp.get().getEmployee().setStatusSinceWhen(LocalDateTime.now());
                            userRepo.save(emp.get());
                            return ttCheck.get();
                        }
                    }
                }
            }
        }
        return null;
    }

    public HouseKeepingTasks TaskFinished(int custID, int roomID, int empID) {
        Optional<Customer> customer = custRepo.findById(custID);
        if(customer.isPresent()){
            Optional<Room> room = roomRepo.findById(roomID);
            if(room.isPresent()){
                Optional<Reservation> res = resRepo.findByCustomerUserUserNameAndCheckoutDateGreaterThan(customer.get().getUser().getUsername(),LocalDate.now());
                List<Room> rooms = res.get().getRoom();
                if(rooms.contains(room.get())){
                    Optional<HouseKeepingTasks> ttCheck = taskRepo.findByRoomRoomID(room.get().getRoomID());
                    if(ttCheck.isPresent() && ttCheck.get().getStatus().equals("Cleaning Room!")){
                        Optional<User> emp = userRepo.findByEmployeeEmployeeID(empID);
                        if(emp.isPresent()) {
                            ttCheck.get().setStatus("Room is Ready");
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
                            emp.get().getEmployee().setStatus("free");
                            emp.get().getEmployee().setStatusSinceWhen(LocalDateTime.now());
                            userRepo.save(emp.get());
                            return task;
                        }
                    }
                }
            }
        }
        return null;
    }


    public WorkShift StartWorkingShift(int empID) {
        Optional<Employee> emp = empRepo.findById(empID);
        if(emp.isPresent()){
            WorkShift shift = new WorkShift();
            shift.setEmployee(emp.get());
            shift.setWorkDay(LocalDate.now());
            shift.setStartTime(LocalTime.now());
            shiftRepo.save(shift);
        }
    return null;
    }

    public WorkShift EndWorkingShift(int empID) {
        Optional<Employee> emp = empRepo.findById(empID);
        if(emp.isPresent()){
            Optional<WorkShift> shift = shiftRepo.findByWorkDayAndEmployeeEmployeeID(LocalDate.now(),emp.get().getEmployeeID());
            if(shift.isPresent()){
                shift.get().setFinishTime(LocalTime.now());
                Duration duration = Duration.between(shift.get().getStartTime(), shift.get().getFinishTime());
                long hours = duration.toHours();
                shift.get().setTotalHours(hours);
                shiftRepo.save(shift.get());
                return shift.get();
            }
            else{
                Optional<WorkShift> shift1 = shiftRepo.findByWorkDayAndEmployeeEmployeeID(LocalDate.now().minusDays(1),emp.get().getEmployeeID());
                if(shift1.isPresent()){
                    if(shift1.get().getFinishTime()==(null)){
                        shift1.get().setFinishTime(LocalTime.now());
                        Duration duration = Duration.between(shift1.get().getStartTime(), shift1.get().getFinishTime());
                        long hours = duration.toHours();
                        shift1.get().setTotalHours(hours);
                        shiftRepo.save(shift1.get());
                        return shift1.get();
                    }
                }
            }
        }
        return null;
    }

    public List<Employee> GetFreeHousekeepers(int adminID) {
        Optional<Employee> admin = empRepo.findById(adminID);
        if(admin.isPresent() && admin.get().getEmployeeRole().equals("Admin")){
            Optional<List<Employee>> freehouseKeepers = empRepo.findByUserRoleAndEmployeeRoleAndStatus(Role.Employee,"housekeeper","free");
            return freehouseKeepers.orElse(null);
        }
        return null;
    }

    public List<Employee> GetBusyHousekeepers(int adminID) {
        Optional<Employee> admin = empRepo.findById(adminID);
        if(admin.isPresent() && admin.get().getEmployeeRole().equals("Admin")){
            Optional<List<Employee>> freehouseKeepers = empRepo.findByUserRoleAndEmployeeRoleAndStatus(Role.Employee,"housekeeper","busy");
            return freehouseKeepers.orElse(null);
        }
        return null;
    }

    public HouseKeepingTasks AssignHouseKeeperByAdmin(int adminID, int custID, int roomID, int empID) {
        Optional<Employee> admin = empRepo.findById(adminID);
        if(admin.isPresent() && admin.get().getEmployeeRole().equals("Admin")) {
            Optional<Customer> customer = custRepo.findById(custID);
            if(customer.isPresent()){
                Optional<Room> room = roomRepo.findById(roomID);
                if(room.isPresent()){
                    Optional<Reservation> res = resRepo.findByCustomerUserUserNameAndCheckoutDateGreaterThan(customer.get().getUser().getUsername(),LocalDate.now());
                    List<Room> rooms = res.get().getRoom();
                    if(rooms.contains(room.get())){
                        Optional<HouseKeepingTasks> ttCheck = taskRepo.findByRoomRoomID(room.get().getRoomID());
                        if(ttCheck.isPresent() && ttCheck.get().getStatus().equals("Waiting for Free HouseKeeper")){
                            Optional<User> emp = userRepo.findByEmployeeEmployeeID(empID);
                            if(emp.isPresent() && emp.get().getEmployee().getEmployeeRole().equals("housekeeper")) {
                                ttCheck.get().setStatus("Cleaning Room!");
                                ttCheck.get().setEmp(emp.get().getEmployee());
                                taskRepo.save(ttCheck.get());
                                emp.get().getEmployee().setStatus("busy");
                                emp.get().getEmployee().setStatusSinceWhen(LocalDateTime.now());
                                userRepo.save(emp.get());
                                return ttCheck.get();
                            }
                        }
                    }
                }
            }
            return null;
        }
        return null;
    }


    public HouseKeepingTasks TaskFinishedByAdmin(int adminID, int custID, int roomID, int empID) {
        Optional<Employee> admin = empRepo.findById(adminID);
        if (admin.isPresent() && admin.get().getEmployeeRole().equals("Admin")) {
            Optional<Customer> customer = custRepo.findById(custID);
            if (customer.isPresent()) {
                Optional<Room> room = roomRepo.findById(roomID);
                if (room.isPresent()) {
                    Optional<Reservation> res = resRepo.findByCustomerUserUserNameAndCheckoutDateGreaterThan(customer.get().getUser().getUsername(), LocalDate.now());
                    List<Room> rooms = res.get().getRoom();
                    if (rooms.contains(room.get())) {
                        Optional<HouseKeepingTasks> ttCheck = taskRepo.findByRoomRoomID(room.get().getRoomID());
                        if (ttCheck.isPresent() && ttCheck.get().getStatus().equals("Cleaning Room!")) {
                            Optional<User> emp = userRepo.findByEmployeeEmployeeID(empID);
                            if (emp.isPresent()) {
                                ttCheck.get().setStatus("Room is Ready");
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
                                emp.get().getEmployee().setStatus("free");
                                emp.get().getEmployee().setStatusSinceWhen(LocalDateTime.now());
                                userRepo.save(emp.get());
                                return task;
                            }
                        }
                    }
                }
            }
            return null;
        }
        return null;
    }




}
