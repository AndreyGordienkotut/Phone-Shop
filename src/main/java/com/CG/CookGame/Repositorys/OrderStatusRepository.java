package com.CG.CookGame.Repositorys;

import com.CG.CookGame.Models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}