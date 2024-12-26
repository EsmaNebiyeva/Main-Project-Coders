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
        return "Gönderildi"+s;
    }

    @Override
    public String sendPaymentMediaMail(String email) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
        message.setFrom(from);
        message.setTo(email);
        String s = randomString(6);
        message.setText("Posive APP\n" +
                "Payment verifyCode is there:\n"+
                s);
        message.setSubject("Posive Verify Paymentation ");
        FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
        message.addAttachment("posiveApp.jpg",file);
        mailSender.send(mimeMessage);
        return "Gönderildi"+s;
    }

    private String randomString(int len) {
    Random random = new Random();
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder randomString = new StringBuilder();

    //10 karakter uzunluğunda rastgele bir String üretmek
    for (int i = 0; i < len; i++) {
        randomString.append(alphabet.charAt(random.nextInt(alphabet.length())));
    }
    return randomString.toString();
}
@Override
public String sendOrdeString(String email,String action) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "Order:\n"+" "+action+"\n ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}


@Override
public String sendOrderCount(String email,Integer action) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "Orders:\n"+"TƏBRİKLƏRRR!!!!!\n"+" Artıq "+action+". sifarişiniz oldu!!!! ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}
@Override
public String sendOrderIncome(String email,Double action) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "Orders:\n"+"TƏBRİKLƏRRR!!!!!\n"+"Son 1 ay erzinde gelirinizde"+action+"% artim olub !!!! ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}
@Override
public String sendProductString(String email,String action) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "Product:\n"+" "+action+"\n ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}

@Override
public String sendUserPer(String email, String action) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "User Permission:\n"+"Deyisiklik:"+" "+action+"\n ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}
@Override
public String sendAccount(String email) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "ACCOUNT:\n"+"Deyisiklik"+" OLDU\n ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}
@Override
public String sendBusiness(String email) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "BUSINESS And ADDRESS:\n"+"Deyisiklik"+" OLDU\n ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}
@Override
public String sendSetting(String email) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "Setting:\n"+"Deyisiklik"+" OLDU\n ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}

@Override
public String sendAccountDeleting(String email) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "ACCOUNT:\n"+"Diqqet:"+" 14 gun icazerisinde log in edilmese hesab silinecek!!!!!!\n ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}

@Override
public String sendEmail(String email, String register) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "Possive Appdə qeydiyyatınız  uğurlu olduƏ");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}
@Override
public  String sendRegister(String email) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
    message.setFrom(from);
    message.setTo(email);
    message.setText("Posive APP\n" +
            "Possive Appdə Google ile qeydiyyatınız  uğurlu oldu");
    message.setSubject("Posive ");
    FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Asus\\Downloads\\posiveApp.jpeg"));
    message.addAttachment("posiveApp.jpg",file);
    mailSender.send(mimeMessage);
    return "Gönderildi";
}
}