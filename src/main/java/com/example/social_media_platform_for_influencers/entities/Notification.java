package com.example.social_media_platform_for_influencers.entities;
import com.example.social_media_platform_for_influencers.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


import java.sql.Timestamp;

@Entity
@Data
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;
    private  Long relatedId;
    @Column(name = "is_read")
    private boolean read;

    private Timestamp sentAt;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @ManyToOne
    @JoinColumn(name = "user_user_id")
    @JsonIgnore
    private User user;
}
