package com.example.social_media_platform_for_influencers.services;
import com.example.social_media_platform_for_influencers.entities.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;


@Service
public interface PostInterface {
    Post createPostByuserId(Post post,Long userId);
    boolean existsById(Long postId);
    void deletePostByPostId( Long postId);
    void deleteAllPostsByUserId(Long userId);
    Post updatePostBypostIdByuserId(Long postId,Long userId,Post post);
    Post getPostBypostId (Long postId);//
    List<Post> getAllPostsByUser(Long userId);
    Post getPostByCampaignId(Long id);
    Long countPostsByUser(Long userId);
    Long  countPostsByDate(LocalDate dateParam);
    List<Post> getPostsByDate(LocalDate dateParam);
    List<Post> getTopPostsByReacts(  int topN);

    Long countPostsToday(Long userId);
    List<Post> getSpamPostsByUser(Long userId);
    boolean isPostSpam(Long postId);





}
