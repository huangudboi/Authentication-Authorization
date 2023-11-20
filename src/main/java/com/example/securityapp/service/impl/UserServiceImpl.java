package com.example.securityapp.service.impl;

import com.example.securityapp.Dto.DataMailDTO;
import com.example.securityapp.Dto.LoginDTO;
import com.example.securityapp.Dto.RegisterDTO;
import com.example.securityapp.config.JwtService;
import com.example.securityapp.controller.AuthenticationResponse;
import com.example.securityapp.model.Role;
import com.example.securityapp.model.State;
import com.example.securityapp.model.User;
import com.example.securityapp.repository.UserRepository;
import com.example.securityapp.service.MailService;
import com.example.securityapp.service.UserService;
import com.example.securityapp.utils.Const;
import com.example.securityapp.utils.DataUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailService mailService;

    @Override
    public AuthenticationResponse register(RegisterDTO registerDTO) {
        var codenumber = DataUtils.generateTempPwd(4);
        var user = User.builder()
                .fullname(registerDTO.getFullname())
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .code(codenumber)
                .role(Role.USER)
                .state(State.ACTIVE)
                .build();
        repository.save(user);
        try {
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo(registerDTO.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("fullname", registerDTO.getFullname());
            props.put("username", registerDTO.getUsername());
            props.put("code", codenumber);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse("Register Success", true, jwtToken);
    }

    @Override
    public AuthenticationResponse login(LoginDTO loginDTO) {
        User user1 = repository.findByUsername(loginDTO.getUsername());
        if(user1 == null){
            return new AuthenticationResponse("Username not exits", false);
        }else{
            try{authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    ));
            }
            catch (RuntimeException e){
                return new AuthenticationResponse("Password is Wrong", false);
            }
            System.out.println(user1.getCode());
            System.out.println(loginDTO.getCode());
            if(!user1.getCode().equals(loginDTO.getCode())){
                return new AuthenticationResponse("Code is Wrong", false);
            }
            var jwtToken = jwtService.generateToken(user1);
            return new AuthenticationResponse("Login Success", true, loginDTO.getUsername(), jwtToken);
        }
    }
}
