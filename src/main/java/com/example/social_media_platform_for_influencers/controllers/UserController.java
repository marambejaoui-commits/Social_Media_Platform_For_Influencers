package com.example.social_media_platform_for_influencers.controllers;

import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.enums.RoleType;
import com.example.social_media_platform_for_influencers.services.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInterface userInterface;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add")
    public User adduser(@RequestBody User user) {

        return userInterface.adduser(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("addall")
    public List<User> addallusers(@RequestBody List<User> users) {
        return userInterface.addallusers(users);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userInterface.deleteUser(userId);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("verifpassword/{userId}/{rawPassword}")
    public boolean verifPassword(@PathVariable Long userId,@PathVariable String rawPassword) {
        return userInterface.verifPassword(userId, rawPassword);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("exist")
    public boolean existById(@RequestParam("a") Long userId) {
        return userInterface.existById(userId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("count")
    public long CountUsers() {
        return userInterface.CountUsers();
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PutMapping("update/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userInterface.updateUser(userId, user);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("get/{userId}")
    public User getById(@PathVariable Long userId) {
        return userInterface.getById(userId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("all")
    public List<User> getAllUsers() {
        return userInterface.getAllUsers();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("countbyrole/{roleType}")
    public Long CountUsersByRoleType(@PathVariable RoleType roleType) {
        return userInterface.countUsersByRole(roleType);

    }
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PutMapping("change/{userId}/{oldPassword}/{newPassword}")
    public String  changePassword(@PathVariable Long userId, @PathVariable String oldPassword,  @PathVariable String newPassword){
        return userInterface.changePassword(  userId,   oldPassword, newPassword);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getbyemail")//
    public List<User> getUserByEmail(@RequestParam("e") String email){

        return userInterface.getUserByEmail(email);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getbyusername")//
    public   List<User> getUserByUsername(@RequestParam("u") String username)
    {
        return userInterface.getUserByUsername(username);
    }
    @PreAuthorize("hasRole('ADMIN')")
   @GetMapping("detect")
   public List<User> detectSuspiciousUsers(){
        return userInterface.detectSuspiciousUsers();
   }
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
   @PostMapping("login/{email}/{rawPassword}")
    public User login(@PathVariable String email,  @PathVariable String rawPassword){
        return userInterface.login(email,rawPassword);
   }
    @PreAuthorize("hasRole('ADMIN')")
@GetMapping("influencer")
    public List<User> getAllInfluencers(){
        return userInterface.getAllInfluencers();
}


}

