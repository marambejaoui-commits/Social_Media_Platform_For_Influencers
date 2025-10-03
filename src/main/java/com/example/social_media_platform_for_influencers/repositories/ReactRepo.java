package com.example.social_media_platform_for_influencers.repositories;

import com.example.social_media_platform_for_influencers.entities.Comment;
import com.example.social_media_platform_for_influencers.entities.React;
import com.example.social_media_platform_for_influencers.enums.ReactType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ReactRepo extends JpaRepository<React,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM React r WHERE r.post.id = :postId")
    void deleteAllByPost_PostId(Long postId);
    @Modifying
    @Transactional
    @Query("DELETE FROM React r WHERE r.comment.id = :commentId")
    void deleteAllByCommentId(@Param("commentId") Long commentId);

    List<React> findAllByComment_CommentId(Long commentCommentId);


    Long countAllByPost_PostId(Long postPostId);

    Long countByPost_PostIdAndReactType(Long postId, ReactType reactType);

    Long countAllByComment_CommentId(Long commentId);

    Long countByComment_CommentIdAndReactType(Long commentCommentId, ReactType reactType);

    @Override
    boolean existsById(Long reactId);

    List<React> findByPost_PostId(Long postId);




}
