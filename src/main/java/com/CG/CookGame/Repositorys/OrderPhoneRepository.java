package com.CG.CookGame.Repositorys;

import com.CG.CookGame.Models.OrderPhone;
import com.CG.CookGame.Models.OrderPhoneId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPhoneRepository extends JpaRepository<OrderPhone, OrderPhoneId> {
}
