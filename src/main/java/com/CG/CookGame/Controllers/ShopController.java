package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.Customer;
import com.CG.CookGame.Models.Phone;
import com.CG.CookGame.Services.*;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Repositorys.PhoneRepository;
import com.CG.CookGame.Repositorys.UserRepository;
import com.CG.CookGame.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@AllArgsConstructor
@Controller
public class ShopController {
    @Autowired
    private final HttpSession session;
    @Autowired
    private final PhoneRepository phoneRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;

    @GetMapping("/")
    public String showProducts(Model model) {
        List<Phone> phones = phoneRepository.findAll();
        model.addAttribute("phones", phones);
        return "index";
    }

    @GetMapping("/Main")
    public String showProductsWithUser(Model model) {
        if (!session.isPresent()) {
            return "redirect:/login";
        }

        User user = session.getUser();
        List<Phone> phones = phoneRepository.findAll();
        model.addAttribute("phones", phones);
        model.addAttribute("userId", user.getId());
        return "Main";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model) {
        User user = userService.login(login);

        if (user == null) {
            model.addAttribute("error", "Логіну не існує");
            return "login";
        } else {
            if (!user.getPassword().equals(password)) {
                model.addAttribute("error", "Не правильний пароль");
                return "login";
            } else {
                session.setUser(user);
                return "redirect:/" + user.getId() + "/Main";
            }
        }
    }

    @GetMapping("/{id}/Main")
    public String showProductsForUser(@PathVariable Long id, Model model) {
        if (!session.isPresent() || !session.getUser().getId().equals(id)) {
            return "redirect:/login";
        }

        List<Phone> allPhone = phoneRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().collect(Collectors.toList());
        model.addAttribute("phones", allPhone);
        model.addAttribute("currentUser", session.getUser());
        model.addAttribute("userId", id);
        return "Main";
    }

    @PostMapping("/registration")
    public String register(@RequestParam String login,
                           @RequestParam String password,
                           @RequestParam String passwordCheck,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String address,
                           Model model) {
        if (!password.equals(passwordCheck)) {
            model.addAttribute("errorpassword", "Пароли не совпадают");
            return "login";
        }
        if (userRepository.existsByLogin(login)) {
            model.addAttribute("error", "Логін вже існує");
            return "login";
        }
        User newUser = new User(login, password);
        newUser.setLogin(login);
        newUser.setPassword(password);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setUser(newUser);
        newUser.setCustomer(customer);
        userService.save(newUser);
        session.setUser(newUser);
        return "redirect:/" + newUser.getId() + "/Main";
    }

    @GetMapping("/logout")
    public String logout(Model model){
        if (session.isPresent()){
            session.clearUser();
            return "redirect:/"; }
        return "redirect:/";
    }


}
