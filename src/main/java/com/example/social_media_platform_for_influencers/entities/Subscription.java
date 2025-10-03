package com.example.social_media_platform_for_influencers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;



import java.sql.Timestamp;

@Entity
@Table(name="Subscription")
@Data

public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long subscriptionId;
    @CreationTimestamp
    private Timestamp createdAt;

    // L'utilisateur qui s'abonne
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name="subscriber_id", referencedColumnName = "user_id")
    private User subscriber;

    // L'utilisateur suivi (peut être un influencer, advertiser, admin, etc.)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name="followed_user_id", referencedColumnName = "user_id")
    private User followedUser;


}
