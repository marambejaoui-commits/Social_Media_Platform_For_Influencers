package com.example.social_media_platform_for_influencers.servicesImplement;

import com.example.social_media_platform_for_influencers.entities.Subscription;
import com.example.social_media_platform_for_influencers.entities.User;

import com.example.social_media_platform_for_influencers.repositories.SubscriptionRepo;
import com.example.social_media_platform_for_influencers.repositories.UserRepo;
import com.example.social_media_platform_for_influencers.services.SubscriptionInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubscriptionImplement implements SubscriptionInterface {

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Subscription createSubscription(Long subscriberId, Long followedUserId) {
        if (subscriberId.equals(followedUserId)) {
            throw new RuntimeException("❌ You cannot follow yourself");
        }

        User subscriber = userRepo.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("❌ Subscriber not found"));
        User followedUser = userRepo.findById(followedUserId)
                .orElseThrow(() -> new RuntimeException("❌ User to follow not found"));

        boolean alreadyExists = subscriptionRepo.existsBySubscriberAndFollowedUser(subscriber, followedUser);
        if (alreadyExists) {
            throw new RuntimeException("⚠️ You are already following this user");
        }

        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setFollowedUser(followedUser);


        return subscriptionRepo.save(subscription);
    }

    @Override
    public String cancelSubscription(Long subscriberId, Long followedUserId) {
        User subscriber = userRepo.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("❌ Subscriber not found"));
        User followedUser = userRepo.findById(followedUserId)
                .orElseThrow(() -> new RuntimeException("❌ User to follow not found"));

        boolean exists = subscriptionRepo.existsBySubscriberAndFollowedUser(subscriber, followedUser);
        if (!exists) {
            throw new RuntimeException("⚠️ You are not following this user");
        }

        subscriptionRepo.deleteBySubscriberAndFollowedUser(subscriber, followedUser);
        return "✅ Subscription has been cancelled";
    }


    @Override
    @Transactional
    public void removeAllFollowers(Long followedUserId) {
         userRepo.findById(followedUserId)
                .orElseThrow(() -> new RuntimeException("❌ User not found"));
        subscriptionRepo.deleteAllByFollowedUserId(followedUserId); ;
    }

    @Override
    @Transactional
    public void unfollowAll(Long subscriberId) {
         userRepo.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("❌ User not found"));
        subscriptionRepo.deleteAllBySubscriberId(subscriberId);
    }



    @Override
    public boolean isSubscribed(Long subscriberId, Long followedUserId) {
        User subscriber = userRepo.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("❌ Subscriber not found"));
        User followedUser = userRepo.findById(followedUserId)
                .orElseThrow(() -> new RuntimeException("❌ User not found"));
        return subscriptionRepo.existsBySubscriberAndFollowedUser(subscriber, followedUser);
    }

    @Override
    public Long countSubscriptions(Long subscriberId) {
        User subscriber = userRepo.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("❌ User not found"));
        return subscriptionRepo.countAllBySubscriber(subscriber);
    }
    @Override
    public List<User> getRecentFollowers(Long followedUserId) {
        if (followedUserId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "❌ followedUserId must not be null"
            );
        }

        userRepo.findById(followedUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "❌ User not found"
                ));

        return subscriptionRepo.findSubscribersByFollowedUserId(followedUserId);
    }


    @Override
    public List<User> getRecentFollowing(Long subscriberId) {
        userRepo.findById(subscriberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"❌ User not found"));
        return subscriptionRepo.findFollowedUsersBySubscriberId(subscriberId);
    }
    @Override
    public Long countFollowers(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ User not found"));
        return subscriptionRepo.countAllByFollowedUser(user);
    }



    @Override
    public double getFollowerRatio(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ User not found"));

        long followers = subscriptionRepo.countAllByFollowedUser(user);
        long following = subscriptionRepo.countAllBySubscriber(user);

        if (following == 0) {
            return followers > 0 ? Double.POSITIVE_INFINITY : 0.0;
        }
        return (double) followers / following;
    }
    @Override
    public List<User> getMostFollowedUsers(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = subscriptionRepo.findMostFollowedUsers(pageable);

        return results.stream()
                .map(r -> (User) r[0])  // r[0] = User
                .toList();
    }
    @Override
    public List<User> getMutualFollowers(Long userId1, Long userId2) {
        return subscriptionRepo.findMutualFollowers(userId1, userId2);
    }


}



