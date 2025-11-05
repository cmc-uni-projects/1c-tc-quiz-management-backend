package com.example.final_project.service.serviceImpl;

import com.example.final_project.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendRegistrationSuccessEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Registration Successful");
        message.setText("Welcome! Your registration was successful.");
        mailSender.send(message);
    }

    @Override
    public void sendTeacherApprovalEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Teacher Account Approved");
        message.setText("Congratulations! Your teacher account has been approved by the admin.");
        mailSender.send(message);
    }

    @Override
    public void sendTeacherRejectionEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Teacher Account Rejected");
        message.setText("We regret to inform you that your teacher account registration has been rejected.");
        mailSender.send(message);
    }

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Password Reset Request");
            String resetUrl = "http://localhost:3000/reset-password?token=" + token;
            helper.setText("To reset your password, click the link below:\n" + resetUrl, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
