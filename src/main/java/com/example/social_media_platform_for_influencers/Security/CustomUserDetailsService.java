package com.example.social_media_platform_for_influencers.Security;

import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.enums.RoleType;
import com.example.social_media_platform_for_influencers.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Autowired
    public CustomUserDetailsService(UserRepo userRepo){
        this.userRepo=userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found :" + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRoles(user.getRoleType()));
    }
    private Collection<GrantedAuthority> mapRoles(RoleType roleType){
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(("ROLE_"+roleType.toString())));
        return authorities;








    }
}
