package com.example.social_media_platform_for_influencers.repositories;

import com.example.social_media_platform_for_influencers.entities.Post;
import com.example.social_media_platform_for_influencers.entities.Subscription;
import com.example.social_media_platform_for_influencers.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepo  extends JpaRepository<Subscription,Long> {



        boolean existsBySubscriberAndFollowedUser(User subscriber, User followedUser);

        void deleteBySubscriberAndFollowedUser(User subscriber, User followedUser);
     Long countAllBySubscriber(User subscriber);

        Long countAllByFollowedUser(User followedUser);
 // Supprimer tous les abonnés d’un utilisateur
 @Modifying
 @Query(value = "DELETE FROM subscription WHERE followed_user_id  = :followedUserId", nativeQuery = true)
 void deleteAllByFollowedUserId(Long followedUserId);
    @Modifying
    @Query(value = "DELETE FROM subscription WHERE subscriber_id = :subscriberId", nativeQuery = true)
    void deleteAllBySubscriberId(Long subscriberId);


    // 🔹 Récupérer directement les followers (Users)
    @Query("SELECT s.subscriber FROM Subscription s WHERE s.followedUser.id = :followedUserId ORDER BY s.createdAt DESC")
    List<User> findSubscribersByFollowedUserId(@Param("followedUserId") Long followedUserId );

    // 🔹 Récupérer directement les followings (Users)
    @Query("SELECT s.followedUser FROM Subscription s WHERE s.subscriber.id = :subscriberId ORDER BY s.createdAt DESC")
    List<User> findFollowedUsersBySubscriberId(@Param("subscriberId")Long subscriberId );
    @Query("SELECT s.followedUser AS user, COUNT(s) AS followersCount " +
            "FROM Subscription s " +
            "GROUP BY s.followedUser " +
            "ORDER BY COUNT(s) DESC")
    List<Object[]> findMostFollowedUsers(Pageable pageable);
    @Query("SELECT s1.subscriber FROM Subscription s1 " +
            "JOIN Subscription s2 ON s1.subscriber.id = s2.subscriber.id " +
            "WHERE s1.followedUser.id = :userId1 " +
            "AND s2.followedUser.id = :userId2")
    List<User> findMutualFollowers(@Param("userId1") Long userId1,
                                   @Param("userId2") Long userId2);

    @Override
    boolean existsById(Long subscriptionId  );
}





