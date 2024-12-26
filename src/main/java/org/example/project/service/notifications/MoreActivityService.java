package org.example.project.service.notifications;

import java.util.List;

import org.example.project.entity.notification.MoreActivity;
import org.example.project.model.MoreActivityDTO;

public interface MoreActivityService {
   MoreActivity addActivity(List<String> activity,String email);
   MoreActivityDTO getActivity(String email);
}
