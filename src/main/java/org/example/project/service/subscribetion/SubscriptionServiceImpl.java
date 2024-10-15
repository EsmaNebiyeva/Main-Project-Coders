package org.example.project.service.subscribetion;


import org.example.project.entity.subscribetion.Payment;
import org.example.project.entity.subscribetion.Plan;
import org.example.project.entity.subscribetion.Subscription;
import org.example.project.repository.subscribetion.PaymentRepository;
import org.example.project.repository.subscribetion.PlanRepository;
import org.example.project.repository.subscribetion.SubscriptionRepository;
import org.example.project.security.user.UserDetail;
import org.example.project.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
        @Autowired
        private SubscriptionRepository subscriptionRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PlanRepository planRepository;

        @Autowired
        private PaymentRepository paymentRepository;

        public Subscription createSubscription(Integer userId, Long planId) {
            Optional<UserDetail> user = userRepository.findById(userId);
            Optional<Plan> plan = planRepository.findById(planId);

            if (user.isPresent() && plan.isPresent()) {
                Subscription subscription = new Subscription();
                subscription.setUser(user.get());
                subscription.setPlan(plan.get());
                subscription.setStartDate(LocalDate.now());
                subscription.setEndDate(LocalDate.now().plusMonths(1)); // Plan süresine göre ayarlayabilirsiniz
                subscription.setActive(true);

                return subscriptionRepository.save(subscription);
            }

            return null;
        }

        public Payment processPayment(Long subscriptionId, Double amount, String paymentMethod) {
            Optional<Subscription> subscription = subscriptionRepository.findById(subscriptionId);

            if (subscription.isPresent()) {
                Payment payment = new Payment();
                payment.setSubscription(subscription.get());
                payment.setPaymentDate(LocalDate.now());
                payment.setAmount(amount);
                payment.setPaymentMethod(paymentMethod);

                return paymentRepository.save(payment);
            }

            return null;
        }

        public void cancelSubscription(Long subscriptionId) {
            Optional<Subscription> subscription = subscriptionRepository.findById(subscriptionId);

            if (subscription.isPresent()) {
                Subscription sub = subscription.get();
                sub.setActive(false);
                subscriptionRepository.save(sub);
            }
        }
    }


