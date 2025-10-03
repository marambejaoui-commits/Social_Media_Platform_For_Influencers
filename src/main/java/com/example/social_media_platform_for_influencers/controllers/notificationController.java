package com.example.social_media_platform_for_influencers.controllers;

import com.example.social_media_platform_for_influencers.entities.Notification;
import com.example.social_media_platform_for_influencers.enums.NotificationType;
import com.example.social_media_platform_for_influencers.services.NotificationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notif")
public class notificationController {

    @Autowired
    private NotificationInterface notificationInterface;

    // Ajouter une notification (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add/{userId}")
    public Notification addNotificationToUserId(@RequestBody Notification notification, @PathVariable Long userId) {
        return notificationInterface.addNotificationToUserId(notification, userId);
    }

    // Supprimer une notification (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("del/{notificationId}")
    public void deleteNotificationById(@PathVariable Long notificationId){
        notificationInterface.deleteNotificationById(notificationId);
    }

    // Récupérer une notification par ID (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("getnotif/{notificationId}")
    public Notification getNotificationById(@PathVariable Long notificationId){
        return notificationInterface.getNotificationById(notificationId);
    }

    // Récupérer toutes les notifications d’un utilisateur (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("get/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable Long userId){
        return notificationInterface.getNotificationsByUserId(userId);
    }

    // Marquer une notification comme lue (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("AsRead/{notificationId}")
    public Notification markAsRead(@PathVariable Long notificationId){
        return notificationInterface.markAsRead(notificationId);
    }

    // Marquer toutes les notifications comme lues (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("AsReadAll/{userId}")
    public void markAllAsReadByUserId(@PathVariable Long userId){
        notificationInterface.markAllAsReadByUserId(userId);
    }

    // Compter les notifications non lues (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("UnRead/{userId}")
    public Long countUnreadByUserId(@PathVariable Long userId){
        return notificationInterface.countUnreadByUserId(userId);
    }

    // Récupérer les notifications non lues (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("UnReadAll/{userId}")
    public List<Notification> getUnreadNotificationsByUserId(@PathVariable Long userId){
        return notificationInterface.getUnreadNotificationsByUserId(userId);
    }

    // Supprimer toutes les notifications d’un utilisateur (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("deleteAll/{userId}")
    public void deleteAllByUserId(@PathVariable Long userId){
        notificationInterface.deleteAllByUserId(userId);
    }

    // Récupérer les notifications par type (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("{userId}/{type}")
    public List<Notification> getNotificationsByUserIdAndType(@PathVariable Long userId, @PathVariable NotificationType type){
        return notificationInterface.getNotificationsByUserIdAndType(userId,type);
    }

    // Récupérer les dernières notifications (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("lates/{userId}/{limit}")
    public List<Notification> getLatestNotificationsByUserId(@PathVariable Long userId,@PathVariable int limit){
        return notificationInterface.getLatestNotificationsByUserId(userId,limit);
    }

    // Compter toutes les notifications (propriétaire ou ADMIN)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("countall/{userId}")
    public Long countAllByUserId(@PathVariable Long userId){
        return notificationInterface.countAllByUserId(userId);
    }
}
