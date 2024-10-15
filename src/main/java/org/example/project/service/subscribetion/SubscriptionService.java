package org.example.project.service.subscribetion;

import org.example.project.entity.subscribetion.Payment;
import org.example.project.entity.subscribetion.Subscription;

public interface SubscriptionService {
    Subscription createSubscription(Integer userId, Long planId);
    Payment processPayment(Long subscriptionId, Double amount, String paymentMethod);
    void cancelSubscription(Long subscriptionId);
}
