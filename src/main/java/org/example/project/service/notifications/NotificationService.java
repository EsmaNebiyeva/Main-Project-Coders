package org.example.project.service.notifications;

import java.util.List;

import org.example.project.model.NotificationDto;

public interface NotificationService {
    void sendNotification(String email,String text,String description);
    List<NotificationDto> getNotification(String email);
}
