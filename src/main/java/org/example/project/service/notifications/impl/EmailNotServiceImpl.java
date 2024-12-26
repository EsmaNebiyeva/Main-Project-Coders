package org.example.project.service.notifications.impl;

import java.util.ArrayList;
import java.util.List;


import org.example.project.entity.notification.EmailNot;
import org.example.project.model.EmailNotDTO;
import org.example.project.model.EmailNots;

import org.example.project.repository.notifications.EmailNotRepository;
import org.example.project.service.notifications.EmailNotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Transactional
@Service
public class EmailNotServiceImpl implements EmailNotService{
    @Autowired
    private final EmailNotRepository emailNotRepository;
    
    @Override
    public EmailNot addActivity(List<String> nots, String email) {
        EmailNot byEmail = emailNotRepository.findByEmail(email);
        if(byEmail!=null){
            List<EmailNots> not = byEmail.getNotifList();
            not.clear();
            for(String n:nots){
                not.add(EmailNots.valueOf(n.toUpperCase()));
            }
            byEmail.setNotifList(not);
            EmailNot save = emailNotRepository.save(byEmail);
            return save;
        }
        EmailNot emailNot=new EmailNot();
        emailNot.setEmail(email);
        List<EmailNots> not=new ArrayList<>();
        for(String n:nots){
            not.add(EmailNots.valueOf(n));
        }
        emailNot.setNotifList(not);
        EmailNot save = emailNotRepository.save(emailNot);
        return save;
    }

    @Override
    public EmailNotDTO getActivity(String email) {
        EmailNot list = emailNotRepository.findByEmail(email);
        if(list!=null){
           List<EmailNots> not = list.getNotifList();
            List<String> act=new ArrayList<>();
            for(EmailNots n:not){
                act.add(n.toString());
            }
            EmailNotDTO emailNotDTO=new EmailNotDTO();
            emailNotDTO.setNots(act);
            emailNotDTO.setEmail(email);
            return emailNotDTO;
        }
        return null;
    }
    
}
