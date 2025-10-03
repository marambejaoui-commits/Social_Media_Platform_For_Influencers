package com.example.social_media_platform_for_influencers.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="post")
@Data

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long postId;
    private String imageUrl;

    private LocalDateTime createdAt;
    private Timestamp updatedAt;
    private String  content;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<React> reacts=new ArrayList<React>();
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")

    private User user;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments=new ArrayList<Comment>();
    @OneToOne
    @JsonIgnore
    private Campaign campaign;



}
