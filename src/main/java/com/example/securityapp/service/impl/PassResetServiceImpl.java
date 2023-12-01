package com.example.securityapp.service.impl;

import com.example.securityapp.Dto.DataMailDTO;
import com.example.securityapp.Dto.response.ChangePassResponse;
import com.example.securityapp.model.PasswordResetToken;
import com.example.securityapp.model.User;
import com.example.securityapp.repository.PassResetRepository;
import com.example.securityapp.repository.UserRepository;
import com.example.securityapp.service.MailService;
import com.example.securityapp.service.PassResetService;
import com.example.securityapp.utils.Const;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PassResetServiceImpl implements PassResetService {

    @Autowired
    private PassResetRepository passResetRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Override
    public PasswordResetToken saveOrUpdate(PasswordResetToken passwordResetToken) {
        return passResetRepository.save(passwordResetToken);
    }
    @Override
    public PasswordResetToken getLastTokenByUserId(int userId) {
        return passResetRepository.getLastTokenByUserId(userId);
    }

    @Override
    public ResponseEntity<?> resetPassword(String userName) {
        if (userRepository.existsByUserName(userName)) {
            User user = userRepository.findByUserName(userName);
            String token = UUID.randomUUID().toString();
            PasswordResetToken myToken = new PasswordResetToken();
            myToken.setToken(token);
            myToken.setUser(user);
            Date now = new Date();
            myToken.setStartDate(now);
            saveOrUpdate(myToken);
            try {
                DataMailDTO dataMail = new DataMailDTO();
                dataMail.setTo(user.getEmail());
                dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

                Map<String, Object> props = new HashMap<>();
                props.put("fullName", user.getFullName());
                props.put("userName", user.getUserName());
                props.put("code", "Mã token reset mật khẩu của bạn là:"+ token + "\n" +
                        "Token chỉ có hiệu lực trong 5 phút, vui lòng thay đổi mật khẩu của bạn ngay.");
                dataMail.setProps(props);
                mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            } catch (MessagingException exp){
                exp.printStackTrace();
            }
            return ResponseEntity.ok("Email sent! Please check your email");
        } else {
            return new ResponseEntity<>(new ChangePassResponse("Email is not already"), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @Override
    public ResponseEntity<?> creatNewPass(String userName, String token, String newPassword) {
        if (userRepository.existsByUserName(userName)) {
            User user = userRepository.findByUserName(userName);
            PasswordResetToken passwordResetToken = getLastTokenByUserId(user.getUserId());
            long date1 = passwordResetToken.getStartDate().getTime() + 1800000;
            long date2 = new Date().getTime();
            if (date2 > date1) {
                return new ResponseEntity<>(new ChangePassResponse("Expired Token "), HttpStatus.EXPECTATION_FAILED);
            } else {
                if (passwordResetToken.getToken().equals(token)) {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    return new ResponseEntity<>(new ChangePassResponse("Update password successfully "), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ChangePassResponse("Token is fail "), HttpStatus.NO_CONTENT);
                }
            }
        }else{
            return new ResponseEntity<>(new ChangePassResponse("UserName not found "), HttpStatus.NO_CONTENT);
        }
    }
}
