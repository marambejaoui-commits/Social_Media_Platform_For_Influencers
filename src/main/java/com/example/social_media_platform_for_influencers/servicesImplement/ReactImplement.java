package com.example.social_media_platform_for_influencers.servicesImplement;

import com.example.social_media_platform_for_influencers.entities.Comment;
import com.example.social_media_platform_for_influencers.entities.Post;
import com.example.social_media_platform_for_influencers.entities.React;

import com.example.social_media_platform_for_influencers.enums.ReactType;
import com.example.social_media_platform_for_influencers.repositories.CommentsRepo;
import com.example.social_media_platform_for_influencers.repositories.PostRepo;
import com.example.social_media_platform_for_influencers.repositories.ReactRepo;
import com.example.social_media_platform_for_influencers.repositories.UserRepo;
import com.example.social_media_platform_for_influencers.services.ReactInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@Service
public class ReactImplement implements ReactInterface {

    @Autowired
    private ReactRepo reactRepo;
    @Autowired
    private CommentsRepo commentsRepo;
    @Autowired
    private PostRepo postRepo;
@Autowired
private UserRepo userRepo;
    public React addReact(React react, Object target) {
        react.setUpdatedAt(null);

        if (target instanceof Post post) {
            react.setPost(post);
        } else if (target instanceof Comment comment) {
            react.setComment(comment);
        } else {
            throw new IllegalArgumentException("Target must be a Post or Comment");
        }

        return reactRepo.save(react);
    }

    @Override
    public React addreactBypostId(React react, Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post id not found"));
        return addReact(react, post);
    }

    @Override
    public React addreactBycommentId(React react, Long commentId) {
        Comment comment = commentsRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment id not found"));
        return addReact(react, comment);
    }



    // Supprimer toutes les réactions d’un post
    @Override
    @Transactional
    public void deletereactbypostId(Long postId)
    {
        reactRepo.deleteAllByPost_PostId(postId);
    }

    @Override
    @Transactional
    public void deleteAllByCommentId(Long commentId) {
       if( commentsRepo.findByCommentId(commentId)==null){
         throw new RuntimeException("comment not found ");
       }
       reactRepo.deleteAllByCommentId(commentId);

    }

    @Override
    @Transactional
    public void deleteReactById(Long reactId) {
       reactRepo.findById(reactId).orElseThrow(()-> new RuntimeException("React not found"));

        reactRepo.deleteById(reactId);

    }


    // Compter les réactions d’un post par type
    @Override
    public Long countReactByPostByreactType(Long postId, ReactType reactType) {
        return reactRepo.countByPost_PostIdAndReactType(postId, reactType);
    }

    @Override
    public Long countReactByPost(Long postId) {
        return reactRepo.countAllByPost_PostId(postId);
    }

    @Override
    public Long countReactByComment(Long commentId) {
        return reactRepo.countAllByComment_CommentId(commentId);
    }

    @Override
    public Long countReactByCommentByreactType(Long commentId, ReactType reactType) {
        return reactRepo.countByComment_CommentIdAndReactType(commentId, reactType);
    }

    @Override
    public List<React> getReactsByPost(Long postId) {
        return reactRepo.findByPost_PostId(postId);
    }

    @Override
    public List<React> getReactsByComment(Long commentId) {
        return reactRepo.findAllByComment_CommentId(commentId);
    }

    private React updateReactType(React react, ReactType reactType) {
        react.setUpdatedAt(Timestamp.from(Instant.now()));
        react.setReactType(reactType);
        return reactRepo.save(react);
    }


    // Mise à jour d’une réaction sur un post
    @Override
    public React updateReactByPostIdByreactType(ReactType reactType, Long reactId) {
        React react = reactRepo.findById(reactId)
                .orElseThrow(() -> new RuntimeException("React not found"));
        return updateReactType(react, reactType);
    }








}
