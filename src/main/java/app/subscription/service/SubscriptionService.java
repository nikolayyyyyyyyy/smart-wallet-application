package app.subscription.service;

import app.subscription.model.Subscription;
import app.subscription.model.SubscriptionPeriod;
import app.subscription.model.SubscriptionType;
import app.subscription.repository.SubscriptionRepository;
import app.user.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription createSubscription(User user){
        return this.subscriptionRepository.save(
                Subscription.builder()
                        .type(SubscriptionType.DEFAULT)
                        .owner(user)
                        .period(SubscriptionPeriod.MONTHLY)
                        .price(BigDecimal.valueOf(0))
                        .renewalAllowed(true)
                        .createdOn(LocalDateTime.now())
                        .completedOn(LocalDateTime.now().plusMonths(1))
                        .build()
        );
    }
}
