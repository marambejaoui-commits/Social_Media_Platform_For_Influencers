package com.example.social_media_platform_for_influencers.repositories;

import com.example.social_media_platform_for_influencers.entities.Notification;

import com.example.social_media_platform_for_influencers.enums.NotificationType;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {
    List<Notification> findAllByUser_UserId(Long userId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM notification WHERE user_user_id = :userId", nativeQuery = true)
    void deleteAllByUser_UserId(Long userId);
    List<Notification> findAllByUser_UserIdAndReadIsFalse(Long userId); // ✅ non lues

    Long countAllByUser_UserIdAndReadIsFalse(Long userId); // ✅ nombre non lues

    List<Notification> findAllByUser_UserIdAndNotificationType(Long userId, NotificationType type);

    List<Notification> findAllByUser_UserId(Long userId, Pageable pageable);
    Long countAllByUser_UserId(Long userId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM notification WHERE notification_id = :notificationId", nativeQuery = true)
    void deleteById(@Param("notificationId") Long notificationId);
}
