package com.example.social_media_platform_for_influencers.controllers;

import com.example.social_media_platform_for_influencers.entities.Comment;
import com.example.social_media_platform_for_influencers.services.CommentsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class commentsController {

    @Autowired
    private CommentsInterface commentsInterface;

    // Ajouter un commentaire à un post (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("add/{postId}")
    public Comment createCommentBypostId(@RequestBody Comment comment, @PathVariable Long postId){
        return commentsInterface.createCommentBypostId(comment, postId);
    }

    // Supprimer un commentaire par ID (ADMIN ou propriétaire)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{commentId}")
    public void deleteCommentBycommentId(@PathVariable Long commentId) {
        commentsInterface.deleteCommentBycommentId(commentId);
    }

    // Supprimer tous les commentaires d’un post (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("post/{postId}")
    public void deleteAllCommentBypostId(@PathVariable Long postId) {
        commentsInterface.deleteAllCommentBypostId(postId);
    }

    // Mettre à jour un commentaire (ADMIN ou propriétaire)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PutMapping("update/{commentId}")
    public Comment updateCommentById(@PathVariable Long commentId, @RequestBody Comment comment){
        return commentsInterface.updateCommentById(commentId, comment);
    }

    // Compter les commentaires d’un post (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("countcomments/{postId}")
    public Long countCommentsByPost(@PathVariable Long postId){
        return commentsInterface.countCommentsByPost(postId);
    }

    // Récupérer un commentaire par ID (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("comment/{commentId}")
    public Comment getCommentBycommentId(@PathVariable Long commentId ){
        return commentsInterface.getCommentBycommentId(commentId);
    }

    // Récupérer tous les commentaires d’un post (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("get/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId){
        return commentsInterface.getCommentsByPostId(postId);
    }

    // Récupérer les commentaires contenant un mot clé (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("getbykeyword/{postId}/{keyword}")
    public List<Comment> getCommentsContainingText(@PathVariable String keyword, @PathVariable Long postId){
        return commentsInterface.getCommentsContainingText(keyword, postId);
    }

    // Récupérer les top commentaires par réactions (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("top/{postId}/{limit}")
    public List<Comment> getTopCommentsByReacts(@PathVariable Long postId, @PathVariable int limit){
        return commentsInterface.getTopCommentsByReacts(postId, limit);
    }

    // Vérifier si un commentaire est spam (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("CommentSpam/{commentId}")
    public boolean isCommentSpam(@PathVariable Long commentId){
        return commentsInterface.isCommentSpam(commentId);
    }

    // Récupérer les commentaires suspects d’un post (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("SuspiciousComments/{postId}")
    public List<Comment> getSuspiciousCommentsByPost(@PathVariable Long postId){
        return commentsInterface.getSuspiciousCommentsByPost(postId);
    }
}
