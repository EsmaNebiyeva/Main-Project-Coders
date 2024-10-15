package org.example.project.service.subscribetion;



import org.example.project.entity.subscribetion.Plan;
import org.example.project.entity.subscribetion.Status;
import org.example.project.entity.subscribetion.Subscription;

import org.example.project.model.SubscriptionDTO;
import org.example.project.repository.subscribetion.PlanRepository;
import org.example.project.repository.subscribetion.SubscriptionRepository;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.project.model.SubscriptionDTO.convertToDTO;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
        @Autowired
        private SubscriptionRepository subscriptionRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PlanRepository planRepository;


    @Override
    public SubscriptionDTO saveSubscription( Subscription subscription) {
        Plan byName = planRepository.findByName(subscription.getPlan().getName());
        if (byName != null) {
            subscription.setPlan(byName);
            subscription.setStatus(Status.SUCCESS);
            if(subscription.getPlan().getDuration().equalsIgnoreCase("YEAR")) {
                subscription.setEndDate(subscription.getStartDate().plusYears(1));
            }else if(subscription.getPlan().getDuration().equalsIgnoreCase("MONTH")) {
                subscription.setEndDate(subscription.getStartDate().plusMonths(1));
            }else if(subscription.getPlan().getDuration().equalsIgnoreCase("WEEK")) {
                subscription.setEndDate(subscription.getStartDate().plusWeeks(1));
            }
            Subscription save = subscriptionRepository.save(subscription);
            return convertToDTO(save);
        }else{
           return null;
        }

    }

    @Override
    public List<SubscriptionDTO> cancelSubscription(String email,List<SubscriptionDTO> subscriptions) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByEmail(email);
        if (subscriptionList != null) {
            List<SubscriptionDTO> subscriptionDTOList = new ArrayList<>();
            for (Subscription subscription : subscriptionList) {
                SubscriptionDTO subscriptionDTO = convertToDTO(subscription);
                subscriptionDTOList.add(subscriptionDTO);
            }
            return subscriptionDTOList;
        } else{
            return List.of();
        }
    }

    @Override
    public List<SubscriptionDTO> getSubscriptionsByUserId(String email) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByEmail(email);
       if(!subscriptionList.isEmpty()){
           List<SubscriptionDTO> subscriptionDTOList = new ArrayList<>();
        for (Subscription subscription : subscriptionList) {
            LocalDate endDate = subscription.getEndDate();
            LocalDate now = LocalDate.now();
            if (endDate.isBefore(now) || endDate.isEqual(now)) {
                subscription.setStatus(Status.EXPIRED);
                SubscriptionDTO subscriptionDTO = convertToDTO(subscription);
                subscriptionDTOList.add(subscriptionDTO);
            } else{
                SubscriptionDTO subscriptionDTO = convertToDTO(subscription);
                subscriptionDTOList.add(subscriptionDTO);
            }
        }
        return subscriptionDTOList;
    } else{
        return List.of();
    }
    }
}


