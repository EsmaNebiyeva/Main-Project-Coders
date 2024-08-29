package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.subscribetion.Payment;
import org.example.project.entity.subscribetion.Subscription;
import org.example.project.service.subscribetion.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    @Autowired
    private final SubscriptionService subscriptionService;

    @PostMapping("/create")
    public Subscription createSubscription(@RequestParam Long userId, @RequestParam Long planId) {
        return subscriptionService.createSubscription(userId, planId);
    }

    @PostMapping("/save/{subscriptionId}/pay")
    public Payment processPayment(@PathVariable Long subscriptionId, @RequestParam Double amount, @RequestParam String paymentMethod) {
        return subscriptionService.processPayment(subscriptionId, amount, paymentMethod);
    }

    @PostMapping("/cancel/{subscriptionId}")
    public void cancelSubscription(@RequestParam Long subscriptionId) {
        subscriptionService.cancelSubscription(subscriptionId);
    }
}