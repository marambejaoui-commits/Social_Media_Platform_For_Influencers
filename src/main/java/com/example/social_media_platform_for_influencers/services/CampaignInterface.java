package com.example.social_media_platform_for_influencers.services;
import com.example.social_media_platform_for_influencers.entities.Campaign;
import com.example.social_media_platform_for_influencers.entities.Post;
import com.example.social_media_platform_for_influencers.enums.StatusType;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface CampaignInterface {
    Campaign  createCampaign (Campaign campaign,Long advertiserId, Long influencerId) ;
    Post addPostToCampaign(Long id, Post post,Long userId);
    Campaign updateCampaign(Long id, Campaign campaign,Long userId);
    void deleteCampaignById(Long id,Long userId);
    Campaign getCampaignById(Long id);
    Campaign getCampaignBypostId(Long postId);
    StatusType ActiveCampaignById(Long id,Long userId);
    StatusType deactivateCampaignById(Long id,Long userId);
    boolean isCampaignActive(Long id);
    List<Campaign> findByStatus(StatusType status);
    Long countActiveCampaigns( ) ;
    Long countDesActiveCampaigns( ) ;
    Long countAllCampaigns();


}
