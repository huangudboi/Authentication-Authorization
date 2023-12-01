package com.example.securityapp.service.impl;

import com.example.securityapp.Dto.*;
import com.example.securityapp.Dto.response.ChangePassResponse;
import com.example.securityapp.config.JwtService;
import com.example.securityapp.Dto.response.AuthenticationResponse;
import com.example.securityapp.model.ERole;
import com.example.securityapp.model.Role;
import com.example.securityapp.model.State;
import com.example.securityapp.model.User;
import com.example.securityapp.repository.UserRepository;
import com.example.securityapp.security.CustomUserDetails;
import com.example.securityapp.security.CustomUserDetailsService;
import com.example.securityapp.service.MailService;
import com.example.securityapp.service.RoleService;
import com.example.securityapp.service.UserService;
import com.example.securityapp.utils.Const;
import com.example.securityapp.utils.DataUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailService mailService;

    @Override
    public AuthenticationResponse register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return new AuthenticationResponse("Error: Email is already", false);
        }
        var codenumber = DataUtils.generateTempPwd(4);
        User user = new User();
        user.setFullName(registerDTO.getFullName());
        user.setUserName(registerDTO.getUserName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setCode(codenumber);
        user.setState(State.ACTIVE);
        Set<String> strRoles = registerDTO.getListRoles();
        Set<Role> listRoles = new HashSet<>();
        if (strRoles.size() == 0) {
            //User quyen mac dinh
            Role userRole = roleService.findByRoleName(ERole.USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            for(String role:strRoles){
                switch (role) {
                    case "ADMIN":
                        System.out.println("Có chức ADMIN");
                        Role adminRole = roleService.findByRoleName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                        continue;
                    case "MODERATOR":
                        System.out.println("Có chức MODERATOR");
                        Role modRole = roleService.findByRoleName(ERole.MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(modRole);
                        continue;
                    case "USER":
                        System.out.println("Có chức USER");
                        Role userRole = roleService.findByRoleName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                        continue;
                }
            }
        }
        user.setListRoles(listRoles);
        userRepository.save(user);
        try {
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setTo(registerDTO.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("fullName", registerDTO.getFullName());
            props.put("userName", registerDTO.getUserName());
            props.put("code", "Mã code đăng nhập hệ thống của bạn là:"+ codenumber);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUserName());
        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthenticationResponse("Register Success", true, jwtToken);
    }

    @Override
    public AuthenticationResponse login(LoginDTO loginDTO) {
        System.out.println(loginDTO);
        User user1 = userRepository.findByUserName(loginDTO.getUserName());
        if(user1 == null){
            return new AuthenticationResponse("Username not exits", false);
        }else{
            try{authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUserName(),
                            loginDTO.getPassword()
                    ));
            }
            catch (RuntimeException e){
                return new AuthenticationResponse("Password is Wrong", false);
            }
            if(!user1.getCode().equals(loginDTO.getCode())){
                return new AuthenticationResponse("Code is Wrong", false);
            }
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user1.getUserName());
            var jwtToken = jwtService.generateToken(userDetails);
            return new AuthenticationResponse("Login Success", true, loginDTO.getUserName(), jwtToken);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO){
        User userChange = new User();
        User user = userRepository.findByUserId(updateUserDTO.getUserId());
        if (user != null) {
            userChange.setUserId(updateUserDTO.getUserId());
            userChange.setFullName(updateUserDTO.getFullName());
            userChange.setUserName(updateUserDTO.getUserName());
            userChange.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
            userChange.setEmail(updateUserDTO.getEmail());
            userChange.setCode(user.getCode());
            userChange.setState(user.getState());
            Set<String> strRoles = updateUserDTO.getListRoles();
            Set<Role> listRoles = new HashSet<>();
            if (strRoles == null) {
                Role userRole = roleService.findByRoleName(ERole.USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                listRoles.add(userRole);
            } else {
                for(String role:strRoles){
                    switch (role) {
                        case "ADMIN":
                            Role adminRole = roleService.findByRoleName(ERole.ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            listRoles.add(adminRole);
                            continue;
                        case "MODERATOR":
                            Role modRole = roleService.findByRoleName(ERole.MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            listRoles.add(modRole);
                            continue;
                        case "USER":
                            Role userRole = roleService.findByRoleName(ERole.USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            listRoles.add(userRole);
                            continue;
                    }
                }
            }
            userChange.setListRoles(listRoles);
        } else {
            return new ResponseEntity<>(new ChangePassResponse("Can not find userID"), HttpStatus.FORBIDDEN);
        }
        userRepository.save(userChange);
        return ResponseEntity.ok(new ChangePassResponse("User update successfully"));
    }

    @Override
    public ResponseEntity<?> deleteUser(int userId){
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserId(userId);
        if (customUserDetail.getAuthorities().size()>user.getListRoles().size()){
            user.setState(State.DISABLED);
            userRepository.save(user);
        }
        return ResponseEntity.ok("Delete successfully");
    }
    @Override
    public ResponseEntity<?> updatePassword(ChangePasswordDTO changePasswordDTO){
        User user = userRepository.findByUserName(changePasswordDTO.getUserName());
        if(user != null) {
            boolean check = passwordEncoder.matches(changePasswordDTO.getOldPass(), user.getPassword());
            if (check) {
                user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPass()));
                userRepository.save(user);
                return new ResponseEntity<>("Update Password successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Password wrong", HttpStatus.EXPECTATION_FAILED);
            }
        }else {
            return new ResponseEntity<>("userId not exits", HttpStatus.EXPECTATION_FAILED);
        }
    }
}