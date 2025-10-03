package com.example.social_media_platform_for_influencers.controllers;

import com.example.social_media_platform_for_influencers.DTO.AuthResponseDto;
import com.example.social_media_platform_for_influencers.DTO.RegisterDto;
import com.example.social_media_platform_for_influencers.DTO.loginDto;
import com.example.social_media_platform_for_influencers.Security.JWTGenerator;
import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.enums.RoleType;
import com.example.social_media_platform_for_influencers.repositories.UserRepo;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins ="*",allowedHeaders = "*")
public class AuthController {
    private final AuthenticationManager authenticationManage;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepo, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManage = authenticationManager;
        this.userRepo = userRepo;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createDefaultAdminAccount() {
        if (!userRepo.existsByEmail("admin@demo.com")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@demo.com");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setFirstName("admin");
            adminUser.setLastName("demo");
            adminUser.setAdress("tunis");
            adminUser.setRoleType(RoleType.Admin);
            userRepo.save(adminUser);

        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginDto loginDto) {
        try {
            Authentication  authentication=authenticationManage.authenticate(
               new UsernamePasswordAuthenticationToken(
                       loginDto.getEmail(),
                       loginDto.getPassword()
               )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            String token =jwtGenerator.generatorToken(authentication);
            User user = userRepo.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            AuthResponseDto authResponseDto=new AuthResponseDto(token,user);
            return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("invalid email or password",HttpStatus.UNAUTHORIZED);
        }

    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerDto) {
        if (userRepo.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // hash password
        user.setConfirmPassword(passwordEncoder.encode(registerDto.getConfirmPassword()));
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setAdress(registerDto.getAdress());
        user.setRoleType(registerDto.getRoleType());
        user.setBio(registerDto.getBio());// par défaut User
        userRepo.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

}