package org.example.project.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.example.project.entity.subscribetion.Subscription;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SubscriptionDTO {
    private Long subscriptionId;
    private String planName;
    private String status;
    private LocalDate startDate;
    private Double amount;
    public static SubscriptionDTO convertToDTO(Subscription subscription,Double amount) {
        SubscriptionDTO subscriptionDTO= new SubscriptionDTO(subscription.getId(),subscription.getPlan().getName(), subscription.getStatus().getStatus(),subscription.getStartDate(),amount);
        return subscriptionDTO;
    }
}
