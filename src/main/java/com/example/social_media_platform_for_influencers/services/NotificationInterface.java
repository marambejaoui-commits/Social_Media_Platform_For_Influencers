package com.example.social_media_platform_for_influencers.services;
import com.example.social_media_platform_for_influencers.entities.Notification;
import com.example.social_media_platform_for_influencers.enums.NotificationType;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface NotificationInterface {
     Notification addNotificationToUserId(Notification notification,Long userId  );
     void deleteNotificationById(Long notificationId);
     Notification getNotificationById(Long notificationId);
     List<Notification> getNotificationsByUserId(Long userId);
     Notification markAsRead(Long notificationId);
     void markAllAsReadByUserId(Long userId);
     Long countUnreadByUserId(Long userId);
     List<Notification> getUnreadNotificationsByUserId(Long userId);
     void deleteAllByUserId(Long userId);
     List<Notification> getNotificationsByUserIdAndType(Long userId, NotificationType type);
     List<Notification> getLatestNotificationsByUserId(Long userId, int limit);
     Long countAllByUserId(Long userId);







}
