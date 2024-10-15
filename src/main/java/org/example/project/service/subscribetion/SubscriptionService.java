package org.example.project.service.subscribetion;

import org.example.project.entity.subscribetion.Subscription;
import org.example.project.model.SubscriptionDTO;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDTO saveSubscription(Subscription subscription);
  //  Payment processPayment(Long subscriptionId, Double amount, String paymentMethod);
  List<SubscriptionDTO> cancelSubscription(String email,List<SubscriptionDTO> subscriptions);
   // Subscription getSubscriptionById(Long subscriptionId);
  //  List<Subscription> getSubscriptions();
    List<SubscriptionDTO> getSubscriptionsByUserId(String esmail);
//    chooseSubcription(String email,)

}
