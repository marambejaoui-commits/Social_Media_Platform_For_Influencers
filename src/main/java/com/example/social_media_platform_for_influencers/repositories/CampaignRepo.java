package com.example.social_media_platform_for_influencers.repositories;

import com.example.social_media_platform_for_influencers.entities.Campaign;

import com.example.social_media_platform_for_influencers.enums.RoleType;
import com.example.social_media_platform_for_influencers.enums.StatusType;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
@Repository
public interface CampaignRepo extends JpaRepository<Campaign,Long> {
    void deleteByPost_PostId(Long postPostId);

    @Override
    boolean existsById(Long id);

    Campaign findByPost_PostId(Long postId);

    List<Campaign> findAllByStatusType(StatusType statusType);

    Long countAllByStatusType(StatusType statusType);

    @Query("select u.roleType from User u where u.userId=:userId")
    RoleType findRoleTypeByUserId(@Param("userId") Long userId);




}
