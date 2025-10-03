package com.example.social_media_platform_for_influencers.services;
import com.example.social_media_platform_for_influencers.entities.Comment;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CommentsInterface {
    Comment createCommentBypostId(Comment comment, Long postId);
    void deleteCommentBycommentId(Long commentId);
    void deleteAllCommentBypostId(Long postId);
    Comment getCommentBycommentId(Long commentId );
    Comment updateCommentById( Long commentId ,Comment comment);
    List<Comment> getCommentsByPostId(Long postId);
    Long  countCommentsByPost(Long postId);
    List<Comment> getCommentsContainingText(String keyword,Long postId);
    List<Comment> getTopCommentsByReacts(Long postId, int limit);
    List<Comment> getSuspiciousCommentsByPost(Long postId);
    boolean isCommentSpam(Long commentId);


}
