package com.example.social_media_platform_for_influencers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.List;
@Entity
@Table(name="comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long commentId;

    private String content;

    private  Timestamp updatedAt;
    @CreationTimestamp
    private  Timestamp createdAt;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<React> reacts;


}
