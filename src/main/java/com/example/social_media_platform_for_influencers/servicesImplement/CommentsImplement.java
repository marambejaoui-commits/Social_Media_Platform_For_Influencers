package com.example.social_media_platform_for_influencers.servicesImplement;
import com.example.social_media_platform_for_influencers.entities.Comment;
import com.example.social_media_platform_for_influencers.entities.Post;


import com.example.social_media_platform_for_influencers.repositories.CommentsRepo;
import com.example.social_media_platform_for_influencers.repositories.PostRepo;

import com.example.social_media_platform_for_influencers.repositories.ReactRepo;
import com.example.social_media_platform_for_influencers.services.CommentsInterface;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;



@Service
public class CommentsImplement implements CommentsInterface {

@Autowired
    private CommentsRepo commentsRepo;

@Autowired
    private PostRepo postRepo;
@Autowired
private ReactRepo reactRepo;


    @Override
    public Comment createCommentBypostId(Comment comment, Long postId) {
        Post post =postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post id not found"));
        comment.setCreatedAt(Timestamp.from(Instant.now()));
        comment.setUpdatedAt(null);
        comment.setPost(post);
        return commentsRepo.save(comment);
    }
    @Override
    @Transactional
    public void deleteCommentBycommentId(Long commentId) {
        if (!commentsRepo.existsById(commentId)) {
            throw new RuntimeException("comment not found");
        }
        commentsRepo.deleteByCommentId(commentId);
    }



    @Override
    @Transactional
    public void deleteAllCommentBypostId(Long postId) {
         commentsRepo.deleteAllByPost_PostId(postId);
    }

    @Override
    public Comment getCommentBycommentId(Long commentId ) {
        return commentsRepo.findByCommentId(commentId);
    }


    @Override
    public Comment updateCommentById( Long commentId ,Comment comment)
     {
    Comment c=commentsRepo.findByCommentId(commentId);
    c.setContent(comment.getContent());
    c.setUpdatedAt(Timestamp.from(Instant.now()));
      return  commentsRepo.save(c);


    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentsRepo.findByPost_PostId(postId);

    }

    @Override
    public  Long  countCommentsByPost(Long postId) {
        return commentsRepo.countAllByPost_PostId( postId);
    }

    @Override
    public List<Comment> getCommentsContainingText(String keyword,Long postId ) {
        return commentsRepo.findAllByContentContainingAndPost_PostId(keyword,postId);
    }



    @Override
    public List<Comment> getTopCommentsByReacts(Long postId, int limit) {

        return commentsRepo.getTopCommentsByReacts(postId, PageRequest.of(0, limit));
    }
    @Override
    public boolean isCommentSpam(Long commentId) {
        return commentsRepo.findById(commentId)
                .map(c -> c.getContent().toLowerCase().contains("spam") ||
                        c.getContent().toLowerCase().contains("http"))
                .orElse(false);
    }
@Override
    public List<Comment> getSuspiciousCommentsByPost(Long postId) {
        return commentsRepo.findSpamCommentsByPost(postId);
    }


}
