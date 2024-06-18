package com.example.webservicesfinalproject.Service;
import com.example.webservicesfinalproject.DTO.UserDTO;
import com.example.webservicesfinalproject.Entity.Customer;
import com.example.webservicesfinalproject.Entity.CustomerLogin;
import com.example.webservicesfinalproject.Entity.User;
import com.example.webservicesfinalproject.Repository.CustomerRepository;
import com.example.webservicesfinalproject.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository custRepo;
    private final UserRepository userRepo;
    private final userService usServ;

    public CustomerService(CustomerRepository custRepo,
                           UserRepository userRepo,
                           userService usServ) {
        this.custRepo = custRepo;
        this.userRepo = userRepo;
        this.usServ=usServ;
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
}