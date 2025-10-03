package com.example.social_media_platform_for_influencers.controllers;

import com.example.social_media_platform_for_influencers.entities.Subscription;
import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.services.SubscriptionInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub")
public class subscriptionController {
    private final SubscriptionInterface subscriptionInterface;

    public subscriptionController(SubscriptionInterface subscriptionInterface) {
        this.subscriptionInterface = subscriptionInterface;
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("create/{subscriberId}/{followedUserId}")
    public Subscription createSubscription(@PathVariable Long subscriberId,@PathVariable  Long followedUserId){
     return subscriptionInterface.createSubscription(subscriberId,followedUserId);
}  @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @DeleteMapping("cancel/{subscriberId}/{followedUserId}")
        public  String  cancelSubscription(@PathVariable Long subscriberId, @PathVariable Long followedUserId){
            return subscriptionInterface.cancelSubscription(subscriberId,followedUserId);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("is/{subscriberId}/{followedUserId}")
    public   boolean isSubscribed(@PathVariable Long subscriberId, @PathVariable Long followedUserId){
        return subscriptionInterface.isSubscribed(subscriberId,followedUserId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("count/{subscriberId}")
    public  Long countSubscriptions(@PathVariable Long subscriberId){
        return subscriptionInterface.countSubscriptions(subscriberId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("gettop/{followedUserId}")//
    public List<User> getRecentFollowers( @PathVariable Long followedUserId ){
        return subscriptionInterface.getRecentFollowers(followedUserId ) ;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("removeall/{followedUserId}")
public void removeAllFollowers(@PathVariable Long followedUserId){
        subscriptionInterface.removeAllFollowers(followedUserId);
}
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @DeleteMapping("unfollow/{subscriberId}")
    public void unfollowAll(@PathVariable Long subscriberId){

        subscriptionInterface.unfollowAll(subscriberId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
@GetMapping("getrecent")//
    public List<User> getRecentFollowing(@RequestParam("s") Long subscriberId ){
        return subscriptionInterface.getRecentFollowing(subscriberId );
}
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
@GetMapping("getfollowersratio/{followedUserId}")
public double getFollowerRatio(@PathVariable Long followedUserId){
        return subscriptionInterface.getFollowerRatio(followedUserId);
}

    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
@GetMapping("followers/{userId}")
    public Long countFollowers(@PathVariable Long userId){
        return subscriptionInterface.countFollowers(userId);
}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("mostsuivi/{limit}")
    public List<User> getMostFollowedUsers( @PathVariable int limit) {
        return subscriptionInterface.getMostFollowedUsers(limit);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("/mutual/{userId1}/{userId2}")
    public List<User> getMutualFollowers(@PathVariable Long userId1, @PathVariable  Long userId2) {
        return subscriptionInterface.getMutualFollowers(userId1, userId2);
    }











}
