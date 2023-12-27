package com.example.securityapp.repository;

import com.example.securityapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

    User findByUserId(int userId);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);
}
