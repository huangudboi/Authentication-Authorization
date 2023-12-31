package com.example.securityapp.repository;

import com.example.securityapp.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PassResetRepository extends JpaRepository<PasswordResetToken, Integer> {

    @Query(value = "select id, startDate, token, user_id\n" +
            "    from PasswordResetToken\n" +
            "where id= (select max(id)\n" +
            "           from PasswordResetToken\n" +
            "           where user_id = :uId)", nativeQuery = true)
    PasswordResetToken getLastTokenByUserId(@Param("uId")int uId);

}
