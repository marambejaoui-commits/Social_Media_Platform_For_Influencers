package com.example.social_media_platform_for_influencers.DTO;

import com.example.social_media_platform_for_influencers.entities.User;
import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String tokenType= "Bearer";
    private User user;
    public AuthResponseDto(String accessToken,User user){
        this.accessToken=accessToken;
        this.user=user;
    }
}
