package com.example.social_media_platform_for_influencers.servicesImplement;

import com.example.social_media_platform_for_influencers.entities.Campaign;
import com.example.social_media_platform_for_influencers.entities.Invoice;
import com.example.social_media_platform_for_influencers.entities.Post;

import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.enums.RoleType;
import com.example.social_media_platform_for_influencers.enums.StatusType;
import com.example.social_media_platform_for_influencers.repositories.CampaignRepo;
import com.example.social_media_platform_for_influencers.repositories.InvoiceRepo;
import com.example.social_media_platform_for_influencers.repositories.PostRepo;
import com.example.social_media_platform_for_influencers.repositories.UserRepo;
import com.example.social_media_platform_for_influencers.services.CampaignInterface;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CampaignImplement implements CampaignInterface {

    @Autowired
    private CampaignRepo campaignRepo;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private InvoiceRepo invoiceRepo;

    @Override
    public Campaign createCampaign(Campaign campaign, Long advertiserId, Long influencerId) {
        // Vérification que l’advertiser est bien Advertiser
        if (!campaignRepo.findRoleTypeByUserId(advertiserId).equals(RoleType.Advertiser)) {
            throw new RuntimeException("You can't create campaign");
        }

        // Vérification des dates
        if (campaign.getEndDate().before(campaign.getStartDate())) {
            throw new RuntimeException("The dates are invalid");
        }

        // Associer l’influenceur
        User influencer = userRepo.findById(influencerId)
                .orElseThrow(() -> new RuntimeException("Influencer not found"));

        if (!influencer.getRoleType().equals(RoleType.Influencer)) {
            throw new RuntimeException("This user is not an influencer");
        }

        campaign.setInfluencer(influencer);
        campaign.setStatusType(StatusType.NOT_STARTED);

        return campaignRepo.save(campaign);
    }



    @Override
    public Post addPostToCampaign(Long campaignId, Post post, Long userId) {
        // Vérification du rôle
        if (!campaignRepo.findRoleTypeByUserId(userId).equals(RoleType.Influencer)) {
            throw new RuntimeException("You can't add post to campaign");
        }

// Vérification campagne
        Campaign campaign = campaignRepo.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

// Vérifier que cet influenceur est bien celui lié à la campagne
        if (!campaign.getInfluencer().getUserId().equals(userId)) {
            throw new RuntimeException("This influencer is not assigned to this campaign");
        }


        // Vérification facture associée
        Invoice invoice = invoiceRepo.findByCampaign_Id(campaignId)
                .orElseThrow(() -> new RuntimeException("No invoice found for this campaign"));

        if (!invoice.isPaid()) {
            throw new RuntimeException("You must pay to add post to campaign");
        }

        // Vérification statut
        if (!StatusType.INPROGRESS.equals(campaign.getStatusType())) {
            throw new RuntimeException("Cannot add post: campaign is not active");
        }

        // Dates automatiques
        if (post.getCreatedAt() == null) post.setCreatedAt((LocalDateTime.now()));
        post.setUpdatedAt(Timestamp.from(Instant.now()));

        // Associer user et campagne
        post.setUser(userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
        post.setUpdatedAt(null);
        post.setCampaign(campaign);

        // Si la relation est bidirectionnelle
        campaign.setPost(post);

        return postRepo.save(post);
    }




    @Override
    public Campaign updateCampaign(Long id, Campaign campaign,Long userId) {
        if (!campaignRepo.findRoleTypeByUserId(userId).equals(RoleType.Advertiser)){
            throw new  RuntimeException(("You can't update campaign"));
        }
        Campaign existingCampaign = campaignRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
        if (!StatusType.INPROGRESS.equals(existingCampaign.getStatusType())) {
            throw new RuntimeException("Cannot update post: campaign is not active");
        }

        existingCampaign.setBudget(campaign.getBudget());
        existingCampaign.setTitle(campaign.getTitle());
        existingCampaign.setDescription(campaign.getDescription());
        existingCampaign.setEndDate(campaign.getEndDate());
        existingCampaign.setStartDate(campaign.getStartDate());
        existingCampaign.setStatusType(campaign.getStatusType());
        return campaignRepo.save(existingCampaign);
    }

    @Override
    public void deleteCampaignById(Long id,Long userId) {
        Campaign campaign = campaignRepo.findById(id).orElseThrow(() -> new RuntimeException("Campaign id not found"));
        RoleType userRole = campaignRepo.findRoleTypeByUserId(userId);
        if (!(userRole.equals(RoleType.Advertiser) || userRole.equals(RoleType.Admin))) {
            throw new RuntimeException("You don't have permission to activate this campaign");
        }
        if (StatusType.INPROGRESS.equals(campaign.getStatusType())) {
            throw new RuntimeException("Cannot delete  campaign");
        }
            campaignRepo.deleteById(id);

    }

    @Override
    public Campaign getCampaignById(Long id) {
        return campaignRepo.findById(id).orElseThrow(() -> new RuntimeException("Campaign id not found"));
    }

    @Override
    public Campaign getCampaignBypostId(Long postId) {
        return campaignRepo.findByPost_PostId(postId);
    }

    @Override
    public StatusType ActiveCampaignById(Long id, Long userId) {
        Campaign cc = campaignRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign id not found"));

        RoleType userRole = campaignRepo.findRoleTypeByUserId(userId);
        if (!(userRole.equals(RoleType.Advertiser) || userRole.equals(RoleType.Admin))) {
            throw new RuntimeException("You don't have permission to activate this campaign");
        }

        // Vérification paiement via Invoice
        Invoice invoice = invoiceRepo.findByCampaign_Id(id)
                .orElseThrow(() -> new RuntimeException("No invoice found for this campaign"));

        if (!invoice.isPaid()) {
            throw new RuntimeException("You must pay before activating this campaign");
        }

        // Vérifications sur le statut
        if (StatusType.INPROGRESS.equals(cc.getStatusType())) {
            throw new RuntimeException("Campaign is already active");
        }

        if (StatusType.COMPLETED.equals(cc.getStatusType())) {
            throw new RuntimeException("You cannot activate a completed campaign");
        }

        // Activation
        cc.setStatusType(StatusType.INPROGRESS);
        campaignRepo.save(cc);

        return cc.getStatusType();
    }


    @Override
    public StatusType deactivateCampaignById(Long id,Long userId) {
        Campaign cc = campaignRepo.findById(id).orElseThrow(() -> new RuntimeException("Campaign id not found"));
        RoleType userRole = campaignRepo.findRoleTypeByUserId(userId);
        if (!(userRole.equals(RoleType.Advertiser) || userRole.equals(RoleType.Admin))) {
            throw new RuntimeException("You don't have permission to desactivate this campaign");
        }
        if (StatusType.COMPLETED.equals(cc.getStatusType())) {
            throw new RuntimeException("Campaign is already completed");
        }

        if (StatusType.NOT_STARTED.equals(cc.getStatusType())) {
            throw new RuntimeException("You cannot deactivate a campaign that hasn't started");
        }

        cc.setStatusType(StatusType.COMPLETED);
        campaignRepo.save(cc);
        return cc.getStatusType();
    }

    @Override
    public boolean isCampaignActive(Long id) {
        StatusType s = campaignRepo.findById(id).get().getStatusType();
        if (StatusType.INPROGRESS.equals(s)) {
            return true;
        }
        return  false;
    }

    @Override
    public List<Campaign> findByStatus(StatusType status) {
        return campaignRepo.findAllByStatusType(status);
    }



    @Override
    public Long countActiveCampaigns() {
        return campaignRepo.countAllByStatusType(StatusType.INPROGRESS);
    }

    @Override
    public Long countDesActiveCampaigns( ) {
        return campaignRepo.countAllByStatusType(StatusType.COMPLETED);
    }

    @Override
    public Long countAllCampaigns() {
        return campaignRepo.count();
    }




}
