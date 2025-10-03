package com.example.social_media_platform_for_influencers.entities;

import com.example.social_media_platform_for_influencers.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 @Entity
@Table(name="user")
@Data
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    private String FirstName;
    private String LastName;
    @Column ( length = 100,nullable = true,unique = true)
    private String email;
    @Column ( nullable = true)
    @Size(min = 8)
    private String Password;
    private String Bio;
    private String Adress;
    private String Username;
    private  String ConfirmPassword;
    private boolean isSuspicious;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
   @JsonIgnore
    private List<Post> posts=new ArrayList<>();
     @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<Subscription> subscriptions = new ArrayList<>();

     @OneToMany(mappedBy = "followedUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<Subscription> followers = new ArrayList<>();

     @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Notification> notifications=new ArrayList<>();
     @OneToMany(mappedBy = "influencer")
@JsonIgnore
     private List<Campaign> campaigns = new ArrayList<>();

}


