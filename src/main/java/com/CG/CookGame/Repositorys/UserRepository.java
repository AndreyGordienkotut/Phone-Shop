package com.CG.CookGame.Repositorys;
import com.CG.CookGame.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
    Optional<User> findById(Long id);
}
