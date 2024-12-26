package org.example.project.service.notifications;

import java.util.List;

import org.example.project.entity.notification.EmailNot;
import org.example.project.model.EmailNotDTO;

public interface EmailNotService {

EmailNot addActivity(List<String> nots,String email);
EmailNotDTO getActivity(String email);
} 