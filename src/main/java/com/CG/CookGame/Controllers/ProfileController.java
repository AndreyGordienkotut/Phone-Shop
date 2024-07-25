package com.CG.CookGame.Controllers;

import com.CG.CookGame.Models.Customer;
import com.CG.CookGame.Models.User;
import com.CG.CookGame.Services.UserService;
import com.CG.CookGame.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@AllArgsConstructor
@Controller
public class ProfileController {
    private final HttpSession session;
    private final UserService userService;
    @GetMapping("/{id}/profile")
    public String Profile(@PathVariable Long id, Model model) {
        if (!session.isPresent() || !session.getUser().getId().equals(id)) {
            return "redirect:/login";
        }

        Optional<User> userO = userService.findById(id);
        if (userO.isEmpty()) {
            return "redirect:/";
        }

        User user = userO.get();
        Customer customer = userService.findCustomerByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        model.addAttribute("userId", id);
        return "profile";
    }



}
