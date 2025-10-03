package com.example.social_media_platform_for_influencers.repositories;
import com.example.social_media_platform_for_influencers.entities.Post;
 import com.example.social_media_platform_for_influencers.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface PostRepo extends JpaRepository<Post,Long> {


    boolean existsByPostId(Long postId);
    Post findByPostId(Long postId);
    Post findByUser_UserIdAndPostId(Long userId, Long postId);
   void deleteById(Long postId);
    List<Post> findAllByUser_UserId(Long userId);
    Post findByCampaignId(Long campaignId);
    Long countAllByUser_UserId(Long userId);
    @Query(value = "SELECT COUNT(*)  FROM post p WHERE DATE(p.created_at) = :dateParam", nativeQuery = true)
    Long countPostsByDay(@Param("dateParam") LocalDate dateParam);

    @Query("SELECT p FROM Post p WHERE FUNCTION('DATE', p.createdAt) = :dateParam")
    List<Post> getPostsByDay(@Param("dateParam") LocalDate dateParam);


    @Query(value = "SELECT * FROM post p " +
            "LEFT JOIN react r ON p.id = r.post_id " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(r.id) DESC " +
            "LIMIT :topN", nativeQuery = true)
    List<Post> getTopPostsByReacts(@Param("topN") int topN);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM post WHERE post_id = :postId", nativeQuery = true)
    void deletePostBypostId(@Param("postId") Long postId);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.user.id = :userId AND p.createdAt >= CURRENT_DATE")
    Long countPostsToday(@Param("userId") Long userId);
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.createdAt >= CURRENT_DATE")
    List<Post> findPostsToday(@Param("userId") Long userId);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND " +
            "(LOWER(p.content) LIKE '%spam%' OR LOWER(p.content) LIKE '%http%')")
    List<Post> findSpamPosts(@Param("userId") Long userId);






}
