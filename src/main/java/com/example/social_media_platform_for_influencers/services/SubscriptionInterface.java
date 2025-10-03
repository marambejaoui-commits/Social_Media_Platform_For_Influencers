package com.example.social_media_platform_for_influencers.services;

import com.example.social_media_platform_for_influencers.entities.Subscription;

import com.example.social_media_platform_for_influencers.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface SubscriptionInterface {
    Subscription createSubscription(Long subscriberId, Long followedUserId);
    String  cancelSubscription(Long subscriberId, Long followedUserId);
    void removeAllFollowers(Long followedUserId);  // supprimer tous les abonnés d’un utilisateur
    void unfollowAll(Long subscriberId);   // annuler tous les suivis d’un utilisateur
    boolean isSubscribed(Long subscriberId, Long followedUserId);
    Long countSubscriptions(Long subscriberId);//nbre de suivi
    List<User> getRecentFollowers(Long followedUserId ); // derniers abonnés
    List<User> getRecentFollowing(Long subscriberId); // derniers suivis
    double getFollowerRatio(Long followedUserId );         // ratio abonnés / suivis
    List<User> getMostFollowedUsers(int limit);   // top utilisateurs les plus suivis
    Long countFollowers(Long userId);
    List<User> getMutualFollowers(Long userId1, Long userId2);// → followers en commun.


}
