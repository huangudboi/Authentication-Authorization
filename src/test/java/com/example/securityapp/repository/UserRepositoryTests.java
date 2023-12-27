package com.example.securityapp.repository;

import com.example.securityapp.model.ERole;
import com.example.securityapp.model.Role;
import com.example.securityapp.model.State;
import com.example.securityapp.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void UserRepository_SaveUser_ReturnSavedUser() {
//        Set<Role> listRoles= new HashSet<>();
//        Role role = new Role(ERole.USER);
//        listRoles.add(role);
//        User user = User.builder().fullName("Pham Van H").userName("huanpv2001").email("huanabc@gmail.com").password("Huan09082001")
//                .code("1234").state(State.ACTIVE).listRoles(listRoles).build();
//
//        User savedUser = userRepository.save(user);
//
//        Assertions.assertThat(savedUser).isNotNull();
//        Assertions.assertThat(savedUser.getUserId()).isGreaterThan(0);
//    }

}
