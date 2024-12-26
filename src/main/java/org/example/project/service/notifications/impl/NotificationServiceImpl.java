package org.example.project.service.notifications.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.example.project.entity.notification.Notification;
import org.example.project.model.NotificationDto;
import org.example.project.repository.notifications.NotificationRepository;
import org.example.project.service.notifications.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private final NotificationRepository nRepository;

   // private final SimpMessageSendingOperations messagingTemplate;

    public void sendNotification(String email, String text, String description) {
        // Bildirişi verilənlər bazasına saxlayın
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setEmail(email);
        notification.setText(text);
        notification.setAccess("true");
        notification.setDate(LocalDateTime.now());
        List<Notification> byEmail = nRepository.findByEmail(email);
        for(Notification n:byEmail){
            n.setAccess("false");
            nRepository.save(n);
        }
        nRepository.save(notification);

    }

    @Override
    public List<NotificationDto> getNotification(String email) {
        List<Notification> byEmail = nRepository.findByEmail(email);
        List<NotificationDto> aDtos = new ArrayList<>();
        for (Notification n : byEmail) {

            NotificationDto nDto = new NotificationDto(n.getText(), n.getDescription());
            String formattedDateTime;
            
                if (n.getAccess().equalsIgnoreCase("true")) {
                    if (n.getDate() == null) {
                        nDto.setDescription("Date:qeyd olunmayib" + " " + n.getDescription());
                    
                        aDtos.add(nDto);
                       
                    } else {
                        Month month = n.getDate().getMonth();
                        int year = n.getDate().getYear();
                        int dayOfMonth = n.getDate().getDayOfMonth();
                        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
                        if (localDate.isEqual(LocalDate.now())) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                            formattedDateTime = n.getDate().format(formatter);
                            nDto.setDescription("Date:" + formattedDateTime + " " + n.getDescription());
            
                            aDtos.add(nDto);
                        }
                    }
                }
            
        }
        return aDtos;
    }

}
