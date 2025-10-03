package com.example.social_media_platform_for_influencers.repositories;

import com.example.social_media_platform_for_influencers.entities.Comment;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CommentsRepo extends JpaRepository<Comment,Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment WHERE comment_id = :commentId", nativeQuery = true)
    void deleteByCommentId( @Param("commentId") Long commentId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment WHERE post_id = :postId", nativeQuery = true)
    void deleteAllByPost_PostId(Long postId);
    Long countAllByPost_PostId(Long postId);
    Comment findByCommentId(Long commentId);
    List<Comment> findByPost_PostId(Long postId);
    List<Comment> findAllByCommentId(Long commentId);
    Long countAllByCommentId(Long commentId);
    List<Comment> findAllByContentContainingAndPost_PostId(String keyword, Long postId);
    @Query(""" 
    SELECT c 
    FROM Comment c 
    WHERE c.post.id = :postI
    ORDER BY SIZE(c.reacts) DESC
""")
    List<Comment> getTopCommentsByReacts(@Param("postId") Long postId, Pageable pageable);

    boolean existsById(Long commentId);


@Query("SELECT c FROM Comment c WHERE c.post.postId = :postId AND (LOWER(c.content) LIKE '%spam%' OR LOWER(c.content) LIKE '%http%')")
List<Comment> findSpamCommentsByPost(@Param("postId") Long postId);
}





