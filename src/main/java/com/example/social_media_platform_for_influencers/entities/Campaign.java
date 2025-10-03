package com.example.social_media_platform_for_influencers.entities;

import com.example.social_media_platform_for_influencers.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
@Entity
@Table (name = "campaign")
@Data

public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private StatusType statusType;
    private String description;
    private Double budget;

    private Timestamp startDate;
    private Timestamp endDate;
    @OneToOne(mappedBy = "campaign", cascade = CascadeType.ALL,orphanRemoval = true)
    private Post post;

    @OneToOne(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Invoice invoice;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "influencer_id", referencedColumnName = "user_id")
    private User influencer;


}