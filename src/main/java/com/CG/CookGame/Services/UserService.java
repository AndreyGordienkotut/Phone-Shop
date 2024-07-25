package com.CG.CookGame.Services;


import com.CG.CookGame.Models.Customer;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Repositorys.UserRepository;
import com.CG.CookGame.Repositorys.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User login(String login) {
        return userRepository.findByLogin(login);
    }

    public Customer findCustomerByUserId(Long userId) {
        return customerRepository.findByUserId(userId).orElse(null);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
