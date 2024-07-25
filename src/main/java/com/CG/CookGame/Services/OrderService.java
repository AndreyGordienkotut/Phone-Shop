package com.CG.CookGame.Services;

import com.CG.CookGame.Models.*;
import com.CG.CookGame.Repositorys.OrderRepository;
import com.CG.CookGame.Repositorys.OrderPhoneRepository;
import com.CG.CookGame.Repositorys.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    public Order createOrder(Customer customer, List<OrderPhone> orderPhones) {
        Order order = new Order();
        order.setCustomer(customer);

        // Assuming 1 is the ID for "Pending" status
        OrderStatus pendingStatus = orderStatusRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("OrderStatus with ID 1 not found"));

        order.setOrderStatus(pendingStatus);
        order.setCreateDate(LocalDateTime.now());
        order.setOrderPhones(orderPhones);

        // Make sure to set the id for OrderPhoneId correctly
        for (OrderPhone orderPhone : orderPhones) {
            orderPhone.setOrder(order);
            orderPhone.setId(new OrderPhoneId(order.getId(), orderPhone.getPhone().getId()));
        }

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomer_UserId(customer.getUser().getId());
    }
}
