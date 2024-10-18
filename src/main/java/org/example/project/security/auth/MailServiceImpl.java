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
import java.util.Random;


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
        String s = randomString(6);
        message.setText("Posive APP\n" +
                "Passwordu dəyişmək  üçün təsdiqləmə kodu:\n"+
                s);
        message.setSubject("Posive ");
        FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
        message.addAttachment("posiveApp.jpg",file);
        mailSender.send(mimeMessage);
        return "Gönderildi";
    }

private String randomString(int len) {
    Random random = new Random();
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder randomString = new StringBuilder();

    // 10 karakter uzunluğunda rastgele bir String üretmek
    for (int i = 0; i < len; i++) {
        randomString.append(alphabet.charAt(random.nextInt(alphabet.length())));
    }
    return randomString.toString();
}

}