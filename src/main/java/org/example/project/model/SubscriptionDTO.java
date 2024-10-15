package org.example.project.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.project.entity.subscribetion.Subscription;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SubscriptionDTO {
    private String subscriptionId;
    private String planName;
    private String status;
    private LocalDate startDate;
    public static SubscriptionDTO convertToDTO(Subscription subscription) {
        SubscriptionDTO subscriptionDTO= new SubscriptionDTO(subscription.getSubscriptionId(),subscription.getPlan().getName(), subscription.getStatus().getStatus(),subscription.getStartDate());
    return subscriptionDTO;
    }
}
