package com.example.securityapp.service.impl;

import com.example.securityapp.Dto.response.ChangePassResponse;
import com.example.securityapp.model.PasswordResetToken;
import com.example.securityapp.model.ProvideSendEmail;
import com.example.securityapp.model.User;
import com.example.securityapp.repository.PassResetRepository;
import com.example.securityapp.repository.UserRepository;
import com.example.securityapp.security.CustomUserDetails;
import com.example.securityapp.security.CustomUserDetailsService;
import com.example.securityapp.service.PassResetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PassResetServiceImpl implements PassResetService {

    @Autowired
    private PassResetRepository passResetRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProvideSendEmail provideSendEmail;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public PasswordResetToken saveOrUpdate(PasswordResetToken passwordResetToken) {
        return passResetRepository.save(passwordResetToken);
    }
    @Override
    public PasswordResetToken getLastTokenByUserId(int userId) {
        return passResetRepository.getLastTokenByUserId(userId);
    }

    @Override
    public ResponseEntity<?> resetPassword(String userEmail, HttpServletRequest request) {
//        User user = (User) userService.findByUserName(userEmail);
        if (userRepository.existsByEmail(userEmail)) {
            User user = userRepository.findByEmail(userEmail);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUserName());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = UUID.randomUUID().toString();
            PasswordResetToken myToken = new PasswordResetToken();
            myToken.setToken(token);
            String mess= "token is valid for 5 minutes.\n"+"Your token: " +token;
            myToken.setUser(user);
            Date now = new Date();
            myToken.setStartDate(now);
            saveOrUpdate(myToken);
            provideSendEmail.sendSimpleMessage(user.getEmail(),
                    "Reset your password", mess);
            return ResponseEntity.ok("Email sent! Please check your email");
        } else {
            return new ResponseEntity<>(new ChangePassResponse("Email is not already"), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @Override
    public ResponseEntity<?> creatNewPass(String token, String newPassword) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PasswordResetToken passwordResetToken = getLastTokenByUserId(userDetails.getUserId());
        long date1 = passwordResetToken.getStartDate().getTime() + 1800000;
        long date2 = new Date().getTime();
        if (date2 > date1) {
            return new ResponseEntity<>(new ChangePassResponse("Expired Token "), HttpStatus.EXPECTATION_FAILED);
        } else {
            if (passwordResetToken.getToken().equals(token)) {
                User user = userRepository.findByUserId(userDetails.getUserId());
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return new ResponseEntity<>(new ChangePassResponse("update password successfully "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ChangePassResponse("token is fail "), HttpStatus.NO_CONTENT);
            }
        }
    }
}
