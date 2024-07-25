package com.CG.CookGame.Services;

import com.CG.CookGame.Models.User;
import com.CG.CookGame.Repositorys.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository repository;



}
