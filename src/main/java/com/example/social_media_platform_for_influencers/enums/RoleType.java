package com.example.social_media_platform_for_influencers.enums;

import com.example.social_media_platform_for_influencers.entities.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
    Advertiser,
    Influencer,
    Admin,
    User

}
