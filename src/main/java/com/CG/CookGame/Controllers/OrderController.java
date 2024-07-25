package com.CG.CookGame.Controllers;
import com.CG.CookGame.Models.*;
import com.CG.CookGame.Repositorys.OrderPhoneRepository;
import com.CG.CookGame.Repositorys.OrderRepository;
import com.CG.CookGame.Repositorys.OrderStatusRepository;
import com.CG.CookGame.Repositorys.PhoneRepository;
import com.CG.CookGame.Services.*;
import com.CG.CookGame.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final PhoneRepository phoneRepository;
    private final OrderPhoneRepository orderPhoneRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final HttpSession session;

    @GetMapping("/cart")
    public String showCart(Model model) {
        if (!session.isPresent()) {
            return "redirect:/login";
        }

        User user = session.getUser();
        Customer customer = user.getCustomer();
        List<OrderPhone> orderPhones = new ArrayList<>();
        Order currentOrder = orderService.getOrdersByCustomer(customer).stream()
                .filter(order -> order.getOrderStatus().getId() == 1)
                .findFirst()
                .orElse(null);

        if (currentOrder != null) {
            orderPhones = currentOrder.getOrderPhones();
        }

        model.addAttribute("orderPhones", orderPhones);
        model.addAttribute("userId", user.getId());

        return "cart";
    }

    @PostMapping("/cart/add/{phoneId}")
    public String addToCart(@PathVariable Long phoneId) {
        if (!session.isPresent()) {
            return "redirect:/login";
        }

        User user = session.getUser();
        Customer customer = user.getCustomer();

        Phone phone = phoneRepository.findById(phoneId).orElse(null);
        if (phone == null) {
            return "redirect:/Main";
        }

        Order currentOrder = orderService.getOrdersByCustomer(customer).stream()
                .filter(order -> order.getOrderStatus().getId() == 1)
                .findFirst()
                .orElse(null);

        if (currentOrder == null) {
            currentOrder = new Order();
            currentOrder.setCustomer(customer);
            currentOrder.setCreateDate(LocalDateTime.now());
            currentOrder.setOrderStatus(orderStatusRepository.findById(1L)
                    .orElseThrow(() -> new NoSuchElementException("Order status not found")));
            currentOrder.setOrderPhones(new ArrayList<>());
            orderRepository.save(currentOrder);
        }
        OrderPhone existingOrderPhone = currentOrder.getOrderPhones().stream()
                .filter(op -> op.getPhone().getId().equals(phoneId))
                .findFirst()
                .orElse(null);

        if (existingOrderPhone == null) {
            OrderPhone newOrderPhone = new OrderPhone();
            newOrderPhone.setOrder(currentOrder);
            newOrderPhone.setPhone(phone);
            newOrderPhone.setCount(1);  // Пример количества
            newOrderPhone.setPrice(phone.getPrice());
            newOrderPhone.setId(new OrderPhoneId(currentOrder.getId(), phone.getId()));
            currentOrder.getOrderPhones().add(newOrderPhone);
        } else {
            existingOrderPhone.setCount(existingOrderPhone.getCount() + 1);
        }

        orderRepository.save(currentOrder);

        return "redirect:/"+ user.getId() + "/orders";
    }

    @PostMapping("/cart/remove/{orderId}/{phoneId}")
    public String removeFromCart(@PathVariable Long orderId, @PathVariable Long phoneId) {
        OrderPhoneId orderPhoneId = new OrderPhoneId(orderId, phoneId);
        orderPhoneRepository.deleteById(orderPhoneId);
        return "redirect:/cart";
    }


    @PostMapping("/cart/clear")
    public String clearCart() {
        if (!session.isPresent()) {
            return "redirect:/login";
        }

        User user = session.getUser();
        Customer customer = user.getCustomer();
        Order currentOrder = orderService.getOrdersByCustomer(customer)
                .stream()
                .filter(order -> order.getOrderStatus().getId() == 1)
                .findFirst()
                .orElse(null);

        if (currentOrder != null) {
            // Удаляем все товары из текущего заказа
            List<OrderPhone> orderPhones = new ArrayList<>(currentOrder.getOrderPhones());
            currentOrder.getOrderPhones().clear();
            orderPhoneRepository.deleteAll(orderPhones); // Удаляем товары из базы данных
            orderRepository.save(currentOrder); // Сохраняем изменения в заказе
        }

        return "redirect:/cart";
    }

    @PostMapping("/{id}/order/checkout")
    public String checkout(@PathVariable Long id,Model model) {
        if (!session.isPresent()|| !session.getUser().getId().equals(id)) {
            return "redirect:/login";
        }

        User user = session.getUser();
        Customer customer = user.getCustomer();
        Order currentOrder = orderService.getOrdersByCustomer(customer)
                .stream()
                .filter(order -> order.getOrderStatus().getId() == 1)
                .findFirst()
                .orElse(null);

        if (currentOrder != null) {
            OrderStatus completedStatus = orderStatusRepository.findById(2L)
                    .orElseThrow(() -> new NoSuchElementException("Order status not found"));
            currentOrder.setOrderStatus(completedStatus);
            orderRepository.save(currentOrder);
        }
        model.addAttribute("userId", id);

        return "redirect:/"+ user.getId() + "/orders";
    }
    @GetMapping("/{id}/orders")
    public String showOrders(@PathVariable Long id,Model model) {
        if (!session.isPresent()) {
            return "redirect:/login";
        }

        User user = session.getUser();
        Customer customer = user.getCustomer();
        List<Order> orders = orderService.getOrdersByCustomer(customer);
        model.addAttribute("orders", orders);
        model.addAttribute("user", user);
        model.addAttribute("userId", id);
        return "orders";
    }
}