package com.example.social_media_platform_for_influencers.controllers;
import com.example.social_media_platform_for_influencers.entities.React;
import com.example.social_media_platform_for_influencers.enums.ReactType;
import com.example.social_media_platform_for_influencers.services.ReactInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/react")
public class reactController {
    private final ReactInterface reactInterface;

    public reactController(ReactInterface reactInterface) {
        this.reactInterface = reactInterface;
    }

    // Ajouter une réaction à un post
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("/post/{postId}")
    public React addReactByPost(@RequestBody React react, @PathVariable Long postId) {
        return reactInterface.addreactBypostId(react, postId);
    }

    // Ajouter une réaction à un commentaire
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("/comment/{commentId}")
    public React addReactByComment(@RequestBody React react, @PathVariable Long commentId) {
        return reactInterface.addreactBycommentId(react, commentId);
    }

    // Supprimer une réaction d’un post (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("deleteReact/{postId}")
    public void deleteReactByPost(@PathVariable Long postId) {
        reactInterface.deletereactbypostId(postId);
    }

    // Supprimer toutes les réactions d’un commentaire (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("all/{commentId}")
    public void deleteAllByCommentId(@PathVariable Long commentId){
        reactInterface.deleteAllByCommentId(commentId);
    }

    // Compter les réactions d’un post
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("post/{postId}/count")
    public Long countReactsByPost(@PathVariable Long postId) {
        return reactInterface.countReactByPost(postId);
    }

    // Compter les réactions d’un post par type
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("post/{postId}/count/{type}")
    public Long countReactsByPostByType(@PathVariable Long postId, @PathVariable ReactType type) {
        return reactInterface.countReactByPostByreactType(postId, type);
    }

    // Compter les réactions d’un commentaire
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("comment/{commentId}/count")
    public Long countReactsByComment(@PathVariable Long commentId) {
        return reactInterface.countReactByComment(commentId);
    }

    // Compter les réactions d’un commentaire par type
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("comment/{commentId}/count/{type}")
    public Long countReactsByCommentByType(@PathVariable Long commentId, @PathVariable ReactType type) {
        return reactInterface.countReactByCommentByreactType(commentId, type);
    }

    // Lister les réactions d’un commentaire
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("reacts/{commentId}")
    public List<React> getReactsByComment(@PathVariable Long commentId){
        return reactInterface.getReactsByComment(commentId);
    }

    // Lister les réactions d’un post
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("getall/{postId}")
    public List<React> getReactsByPost(@PathVariable Long postId) {
        return reactInterface.getReactsByPost(postId);
    }

    // Mettre à jour une réaction (ADMIN uniquement ou utilisateur propriétaire de la réaction)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("post/{reactId}/update/{reactType}")
    public React updateReact(@PathVariable ReactType reactType, @PathVariable Long reactId) {
        return reactInterface.updateReactByPostIdByreactType(reactType, reactId);
    }

    // Supprimer une réaction par ID (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("reactId/{reactId}")
    public void deleteReactById(@PathVariable Long reactId){
        reactInterface.deleteReactById(reactId);
    }
}
