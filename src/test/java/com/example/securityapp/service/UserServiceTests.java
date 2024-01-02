package com.example.securityapp.service;

import com.example.securityapp.config.SecurityConfig;
import com.example.securityapp.dto.LoginDTO;
import com.example.securityapp.dto.RegisterDTO;
import com.example.securityapp.dto.response.AuthenticationResponse;
import com.example.securityapp.model.*;
import com.example.securityapp.repository.UserRepository;
import com.example.securityapp.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;
    private RegisterDTO registerDTO;
    private LoginDTO loginDTO;

    @BeforeEach
    public void init() {
        Set<Role> listRoles = new HashSet<>();
        listRoles.add(new Role(ERole.USER));
        Set<ERole> listERoles = new HashSet<>();
        listERoles.add(ERole.USER);
//        user = User.builder().fullName("Pham Van H").userName("huanpv2001").email("phamvanhuan@gmail.com")
//                .password(passwordEncoder.encode("Huan09082001")).code("1234").state(State.ACTIVE)
//                .listRoles(listRoles).build();
        registerDTO = RegisterDTO.builder().fullName("Pham Van H").userName("huanpv2001").email("phamvanhuan@gmail.com")
                .password("Huan09082001").listRoles(listERoles).build();
        loginDTO = LoginDTO.builder().userName("huanpv2001").password("Huan09082001").code("1234").build();
    }

    @Test
    public void UserService_Register_ReturnAuthenticationResponse() {
        Mockito.when(securityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        AuthenticationResponse response = userService.register(registerDTO);

        Assertions.assertThat(response.getStatus()).isTrue();
    }

    @Test
    public void UserService_Login_ReturnAuthenticationResponse(){
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        AuthenticationResponse response = userService.login(loginDTO);

        Assertions.assertThat(response.getStatus()).isTrue();
    }
}
