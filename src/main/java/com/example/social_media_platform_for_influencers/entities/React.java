package com.example.social_media_platform_for_influencers.entities;

import com.example.social_media_platform_for_influencers.enums.ReactType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="react")
@Data

public class React{

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long  reactId;
    @CreationTimestamp
    private Timestamp createdAt;

    private Timestamp updatedAt;
    @Enumerated(EnumType.STRING)
    private ReactType reactType;
    @ManyToOne
    @JoinColumn(name="post_id",referencedColumnName = "post_id")
    @JsonIgnore
    private Post post;
    @ManyToOne
    @JoinColumn(name="comment_id",referencedColumnName = "comment_id")
    @JsonIgnore
    private  Comment comment;



}