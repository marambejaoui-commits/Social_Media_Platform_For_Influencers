package com.example.social_media_platform_for_influencers.services;

import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.enums.RoleType;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface UserInterface {
        User adduser(User user );//admin
        List<User> addallusers(List<User> user);//admin
        void deleteUser(Long userId);//admin
        boolean verifPassword(Long userId,String rawPassword);
        boolean existById (Long userId);//admin
        long CountUsers();//admin
        User  updateUser( Long userId,User user);//admin et user pour son propre compte
        User getById(Long userId);//admin et user soi meme
        List<User> getAllUsers();//admin
        Long countUsersByRole(RoleType roleType);//admin
        List<User> getAllInfluencers();
        User login(String email, String rawPassword);//tout
        List<User> getUserByEmail(String email);// //admin ou user soi meme
        List<User> getUserByUsername(String username);
        String changePassword(Long userId, String oldPassword, String newPassword); //user
        List<User> detectSuspiciousUsers(); //admin

}