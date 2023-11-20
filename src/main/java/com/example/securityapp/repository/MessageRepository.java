package com.example.securityapp.repository;

import com.example.securityapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUsername(String username);

//    @Query("SELECT m FROM Message m WHERE LOWER(m.username) = LOWER(:username) ORDER BY m.id DESC")
//    List<Message> getAllBy();
//    public List<Message> find(@Param("username") String username);

}
