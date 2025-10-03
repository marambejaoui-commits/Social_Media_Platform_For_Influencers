package com.example.social_media_platform_for_influencers.servicesImplement;
import com.example.social_media_platform_for_influencers.entities.Notification;
import com.example.social_media_platform_for_influencers.entities.Subscription;
import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.enums.NotificationType;
import com.example.social_media_platform_for_influencers.repositories.*;
import com.example.social_media_platform_for_influencers.services.NotificationInterface;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.util.List;
@Service
public class NotificationImplement implements NotificationInterface {
    @Autowired
        private NotificationRepo  notificationRepo;
    @Autowired
        private UserRepo userRepo;
    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private CampaignRepo campaignRepo;
    @Autowired
    private CommentsRepo commentsRepo;
    @Autowired
    private ReactRepo reactRepo;
    @Autowired
    private InvoiceRepo invoiceRepo;


    @Override
    @Transactional
    public Notification addNotificationToUserId(Notification notification, Long userId) {
        User u = userRepo.findByUserId(userId);
        if (u == null) {
            throw new RuntimeException("User not found with id " + userId);
        }

        boolean shouldAdd = false;

        switch (notification.getNotificationType()) {
            case newFollower:
                Subscription sub = subscriptionRepo.findById(notification.getRelatedId())
                        .orElseThrow(() -> new RuntimeException("Subscription not found with id " + notification.getRelatedId()));

                // 👉 Ici on change le user cible de la notification
                User uv = sub.getFollowedUser();
                if (uv.equals(u)){
                    shouldAdd = true;
                }
                else throw new RuntimeException("this user does not correspond to the followed user ");
                break;
            case campaignUpdate:
                shouldAdd = campaignRepo.existsById(notification.getRelatedId());
                break;
            case newComment:
                shouldAdd = commentsRepo.existsById(notification.getRelatedId());
                break;
            case  newLike:
                shouldAdd = reactRepo.existsById(notification.getRelatedId());
                break;
            case paymentReceived:
                shouldAdd = invoiceRepo.existsById(notification.getRelatedId());
                break;
        }

        if (!shouldAdd) {
             throw new RuntimeException("ID not found");
        }


        notification.setUser(u);
        notification.setNotificationType(notification.getNotificationType());
        notification.setRead(false);
        notification.setSentAt(new Timestamp(System.currentTimeMillis()));

        return notificationRepo.save(notification);
    }


    @Override
    @Transactional
    public void deleteNotificationById(Long notificationId) {
        notificationRepo.deleteById(notificationId);
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
      return   notificationRepo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id " + notificationId));

    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return  notificationRepo.findAllByUser_UserId(userId);
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        Notification notification =notificationRepo.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found with id " + notificationId));
        if(notification.isRead()){
            throw new RuntimeException("Notification is already read");
        }
        notification.setRead(true);
        return notificationRepo.save(notification);
    }

    @Override

    public void markAllAsReadByUserId(Long userId) {
        List<Notification> notifications = notificationRepo.findAllByUser_UserIdAndReadIsFalse(userId);
        for (Notification notification : notifications) {
            notification.setRead(true);

        }
        notificationRepo.saveAll(notifications);
    }

    @Override
    public Long countUnreadByUserId(Long userId) {
         return notificationRepo.countAllByUser_UserIdAndReadIsFalse(userId);
     }

    @Override
    public List<Notification> getUnreadNotificationsByUserId(Long userId) {
        return notificationRepo.findAllByUser_UserIdAndReadIsFalse(userId);
    }

    @Override
    @Transactional
    public void deleteAllByUserId(Long userId) {
        notificationRepo.deleteAllByUser_UserId(userId);
    }

    @Override
    public List<Notification> getNotificationsByUserIdAndType(Long userId, NotificationType type) {
        return notificationRepo.findAllByUser_UserIdAndNotificationType(userId, type);
    }

    @Override
    public List<Notification> getLatestNotificationsByUserId(Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("sentAt").descending());
        return notificationRepo.findAllByUser_UserId(userId,pageable);
    }


    @Override
    public Long countAllByUserId(Long userId) {
        return notificationRepo.countAllByUser_UserId(userId);
    }
}
