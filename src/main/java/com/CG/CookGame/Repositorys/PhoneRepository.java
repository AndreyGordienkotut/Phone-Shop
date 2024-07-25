package com.CG.CookGame.Repositorys;


import com.CG.CookGame.Models.Phone;
import com.CG.CookGame.Models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone,Long> {

    List<Phone> findAll(Sort sort);
    Optional<Phone> findById(Long id);
}
