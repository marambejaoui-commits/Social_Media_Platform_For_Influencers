package com.example.social_media_platform_for_influencers.controllers;

import com.example.social_media_platform_for_influencers.entities.Campaign;
import com.example.social_media_platform_for_influencers.entities.Post;
import com.example.social_media_platform_for_influencers.enums.StatusType;
import com.example.social_media_platform_for_influencers.services.CampaignInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaign")
public class campaignController {

    @Autowired
    private CampaignInterface campaignInterface;

    // Créer une campagne (ADMIN ou ADVERTISER)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER')")
    @PostMapping("addcampaign/{advertiserId}/{influencerId}")
    public Campaign createCampaign(@RequestBody Campaign campaign,
                                   @PathVariable Long advertiserId,
                                   @PathVariable Long influencerId) {
        return campaignInterface.createCampaign(campaign, advertiserId, influencerId);
    }

    // Ajouter un post à une campagne (ADMIN ou INFLUENCER)
    @PreAuthorize("hasAnyRole('ADMIN','INFLUENCER')")
    @PostMapping("{id}/addpost/{userId}")
    public Post addPostToCampaign(@PathVariable Long id, @RequestBody Post post, @PathVariable Long userId) {
        return campaignInterface.addPostToCampaign(id, post, userId);
    }

    // Mettre à jour une campagne (ADMIN ou ADVERTISER)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER')")
    @PutMapping("{id}/updatecampaign/{userId}")
    public Campaign updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign, @PathVariable Long userId) {
        return campaignInterface.updateCampaign(id, campaign, userId);
    }

    // Supprimer une campagne (ADMIN ou ADVERTISER)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER')")
    @DeleteMapping("delete/{id}/{userId}")
    public void deleteCampaignById(@PathVariable Long id, @PathVariable Long userId) {
        campaignInterface.deleteCampaignById(id, userId);
    }

    // Récupérer une campagne par ID (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER','INFLUENCER','USER')")
    @GetMapping("/get/{id}")
    public Campaign getCampaignById(@PathVariable Long id) {
        return campaignInterface.getCampaignById(id);
    }

    // Récupérer une campagne par post ID (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER','INFLUENCER','USER')")
    @GetMapping("/get/by-post/{postId}")
    public Campaign getCampaignBypostId(@PathVariable Long postId){
        return campaignInterface.getCampaignBypostId(postId);
    }

    // Activer une campagne (ADMIN ou ADVERTISER)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER')")
    @PostMapping("active/{id}/{userId}")
    public StatusType ActiveCampaignById(@PathVariable Long id, @PathVariable Long userId){
        return campaignInterface.ActiveCampaignById(id, userId);
    }

    // Désactiver une campagne (ADMIN ou ADVERTISER)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER')")
    @PostMapping("desactive/{id}/{userId}")
    public StatusType deactivateCampaignById(@PathVariable Long id, @PathVariable Long userId){
        return campaignInterface.deactivateCampaignById(id, userId);
    }

    // Vérifier si une campagne est active (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER','INFLUENCER','USER')")
    @PostMapping("tester/{id}")
    public boolean isCampaignActive(@PathVariable Long id){
        return campaignInterface.isCampaignActive(id);
    }

    // Filtrer par statut (tous les rôles)
    @PreAuthorize("hasAnyRole('ADMIN','ADVERTISER','INFLUENCER','USER')")
    @GetMapping("bystatus/{status}")
    public List<Campaign> findByStatus(@PathVariable StatusType status){
        return campaignInterface.findByStatus(status);
    }

    // Compter les campagnes actives (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("countactive")
    public Long countActiveCampaigns(){
        return campaignInterface.countActiveCampaigns();
    }

    // Compter les campagnes désactivées (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("countdesactive")
    public Long countDesActiveCampaigns(){
        return campaignInterface.countDesActiveCampaigns();
    }

    // Compter toutes les campagnes (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("count")
    public Long countAllCampaigns(){
        return campaignInterface.countAllCampaigns();
    }
}
