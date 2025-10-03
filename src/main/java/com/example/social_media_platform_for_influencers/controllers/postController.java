package com.example.social_media_platform_for_influencers.controllers;

import com.example.social_media_platform_for_influencers.entities.Post;
import com.example.social_media_platform_for_influencers.services.PostInterface;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/post")
public class postController {

    @Autowired
    private PostInterface postInterface;

    // Créer un post (accessible à tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("createpost/{userId}")
    public Post createPostByUserId(@Valid @RequestBody Post post, @PathVariable Long userId) {
        return postInterface.createPostByuserId(post,userId);
    }

    // Supprimer tous les posts d’un utilisateur (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("deleteAll/{userId}")
    public void deleteAllPostsByUserId(@PathVariable Long userId){
        postInterface.deleteAllPostsByUserId(userId);
    }

    // Vérifier l’existence d’un post (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PostMapping("exist")
    public boolean existsById(@RequestParam("a") Long postId){
        return postInterface.existsById(postId);
    }

    // Mettre à jour un post (ADMIN ou propriétaire)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @PutMapping("update/{userId}/{postId}")
    public Post updatePostBypostIdByuserId(@PathVariable Long userId,@PathVariable Long postId,@RequestBody Post post) {
        return postInterface.updatePostBypostIdByuserId(userId, postId,post);
    }

    // Récupérer un post par ID (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("get/{postId}")
    public Post getPostBypostIdByuserId(@PathVariable Long postId) {
        return postInterface.getPostBypostId(postId);
    }

    // Récupérer tous les posts d’un utilisateur (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("getByUserId/{userId}")
    public List<Post> getAllPostsByUser(@PathVariable Long userId){
        return postInterface.getAllPostsByUser(userId);
    }

    // Supprimer un post par ID (ADMIN ou propriétaire)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{postId}")
    public void deletePostByPostId(@PathVariable Long postId){
        postInterface.deletePostByPostId(postId);
    }

    // Récupérer un post par campagne (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("bycampaign/{id}")
    public Post getPostByCampaignId(@PathVariable Long id){
        return postInterface.getPostByCampaignId(id);
    }

    // Compter les posts par utilisateur
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("countbyuser/{userId}")
    public Long countPostsByUser(@PathVariable Long userId){
        return postInterface.countPostsByUser(userId);
    }

    // Compter les posts par date
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("date/{dateParam}")
    public Long countPostsByDate(@PathVariable LocalDate dateParam){
        return postInterface.countPostsByDate(dateParam);
    }

    // Top posts par réactions
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("top/{topN}")
    public List<Post> getTopPostsByReacts(@PathVariable int topN){
        return postInterface.getTopPostsByReacts(topN);
    }

    // Récupérer les posts par date
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("getbydate/{dateParam}")
    public List<Post> getPostsByDate(@PathVariable LocalDate dateParam){
        return postInterface.getPostsByDate(dateParam);
    }

    // Compter les posts d’aujourd’hui pour un utilisateur
    @PreAuthorize("hasAnyRole('ADMIN','USER','INFLUENCER','ADVERTISER')")
    @GetMapping("postsToday/{userId}")
    public Long countPostsToday(@PathVariable Long userId){
        return postInterface.countPostsToday(userId);
    }

    // Récupérer les posts signalés comme spam (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("spam/{userId}")
    public List<Post> getSpamPostsByUser(@PathVariable Long userId){
        return postInterface.getSpamPostsByUser(userId);
    }

    // Vérifier si un post est spam (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("IsSpam/{postId}")
    public boolean isPostSpam(@PathVariable Long postId){
        return postInterface.isPostSpam(postId);
    }
}
