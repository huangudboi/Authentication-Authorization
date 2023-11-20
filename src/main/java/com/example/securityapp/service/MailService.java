package com.example.securityapp.service;

import com.example.securityapp.Dto.DataMailDTO;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDTO dataMail, String templateName) throws MessagingException;
}
