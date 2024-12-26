package org.example.project.security.auth;

import jakarta.mail.MessagingException;

public interface MailService {
    String sendMail(String email);
    String sendMultiMediaMail(String email) throws MessagingException;
    String sendPaymentMediaMail(String email) throws MessagingException;
    String sendOrdeString(String email,String action) throws MessagingException;
    String sendProductString(String email,String action) throws MessagingException ;
    String sendOrderCount(String email,Integer action) throws MessagingException;
    String sendOrderIncome(String email,Double action) throws MessagingException;
    String sendUserPer(String email,String action) throws MessagingException;
    String sendAccount(String email) throws MessagingException;
    String sendBusiness(String email) throws MessagingException;
    String sendSetting(String email) throws MessagingException ;
    String sendAccountDeleting(String email) throws MessagingException;
    String sendEmail(String email,String register) throws MessagingException;
    String sendRegister(String email) throws MessagingException;
   // Boolean sendVerifyMail(String email) throws MessagingException;
}
