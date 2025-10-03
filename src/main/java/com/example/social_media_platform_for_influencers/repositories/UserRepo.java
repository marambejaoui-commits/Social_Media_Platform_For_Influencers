package com.example.social_media_platform_for_influencers.repositories;
import com.example.social_media_platform_for_influencers.entities.Post;
import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    boolean existsById(Long userId);
    boolean existsByEmail(String email);
    Long countUsersByRoleType(RoleType roleType);
    List<User> findAllByRoleType(RoleType roleType);
    @Query("SELECT u FROM User u WHERE u.Username LIKE %:keyword%")
    List<User> findByUsernameContaining(@Param("keyword") String keyword);
    List<User> findByEmailContaining(String email);
    Optional<User> findByEmail(String email);
    User findByUserId(Long userId);

}