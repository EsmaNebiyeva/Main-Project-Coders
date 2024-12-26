package org.example.project.service.notifications.impl;

import java.util.ArrayList;
import java.util.List;

import org.example.project.entity.notification.MoreActivity;

import org.example.project.model.MoreActivitiesEnum;
import org.example.project.model.MoreActivityDTO;

import org.example.project.repository.notifications.MoreActivityRepository;
import org.example.project.service.notifications.MoreActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class MoreActivityServiceImpl implements MoreActivityService{
    @Autowired
    private final MoreActivityRepository moreActivitiesRepository;

    
    @Override
    public MoreActivity addActivity(List<String> activity, String email) {
        MoreActivity moreActivity = moreActivitiesRepository.findByEmail(email);
        if(moreActivity!=null){
            List<MoreActivitiesEnum> not = moreActivity.getNotList();
            not.clear();
            for(String n:activity){
                not.add(MoreActivitiesEnum.valueOf(n.toUpperCase()));
            }
            moreActivity.setNotList(not);
             MoreActivity save = moreActivitiesRepository.save(moreActivity);
            return save;
        }
        MoreActivity moreActivity2=new MoreActivity();
        moreActivity2.setEmail(email);
        List<MoreActivitiesEnum> not=new ArrayList<>();
        for(String n:activity){
            not.add(MoreActivitiesEnum.valueOf(n.toUpperCase()));
        }
        moreActivity2.setNotList(not);
          MoreActivity save = moreActivitiesRepository.save(moreActivity2);
        return save;
    }

    @Override
    public MoreActivityDTO getActivity(String email) {
        MoreActivity list = moreActivitiesRepository.findByEmail(email);
        if(list!=null){
          List<MoreActivitiesEnum> not = list.getNotList();
            List<String> act=new ArrayList<>();
            for(MoreActivitiesEnum n:not){
                act.add(n.toString());
            }
            MoreActivityDTO moreActivityDTO=new MoreActivityDTO();
            moreActivityDTO.setActivity(act);
            moreActivityDTO.setEmail(email);
            return moreActivityDTO;
        }
        return null;
    }
    
}
