package org.example.project.security.auth;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.File;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String sendMail(String email) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText("Emaili yenile");
        message.setSubject("Click here!!!!");
        mailSender.send(message);
        return "Gönderildi";
    }

    @Override
    public String sendMultiMediaMail(String email) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
        message.setFrom(from);
        message.setTo(email);
        message.setText("Emaili yenile");
        message.setSubject("Click here!!!!");
        FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Desktop\\esmer.jpg"));
        message.addAttachment("esmer.jpg",file);
        mailSender.send(mimeMessage);
        return "Gönderildi";
    }
}