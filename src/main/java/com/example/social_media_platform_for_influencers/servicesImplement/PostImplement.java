package com.example.social_media_platform_for_influencers.servicesImplement;
import com.example.social_media_platform_for_influencers.entities.Post;
import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.repositories.CommentsRepo;
import com.example.social_media_platform_for_influencers.repositories.PostRepo;
import com.example.social_media_platform_for_influencers.repositories.ReactRepo;
import com.example.social_media_platform_for_influencers.repositories.UserRepo;
import com.example.social_media_platform_for_influencers.services.PostInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;


@Service
public class PostImplement  implements PostInterface {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private CommentsRepo commentsRepo;
    @Autowired
    private ReactRepo reactRepo;

    @Override
    public Post createPostByuserId(Post post, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepo.save(user);
        post.setCreatedAt((LocalDateTime.now()));
        post.setUpdatedAt(null);
        post.setUser(user);

        return postRepo.save(post);
    }


    @Override
    public boolean existsById(Long postId) {
        return postRepo.existsById(postId);

    }

    @Override
    public void deletePostByPostId(Long postId) {
        Post pp=postRepo.findById(postId).orElseThrow(()-> new RuntimeException("post not found "));
        commentsRepo.deleteAllByPost_PostId(postId);
        reactRepo.deleteAllByPost_PostId(postId);
        postRepo.deletePostBypostId(postId);
    }


    @Override
  @Transactional
    public void deleteAllPostsByUserId(Long userId) {
        User user=userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.getPosts().clear();
         userRepo.save(user);
    }

    @Override
    public Post  updatePostBypostIdByuserId(Long userId,Long postId,Post post){
            Post p =postRepo.findByUser_UserIdAndPostId(   userId,postId);
        if (p == null) {
            throw new RuntimeException("Post not found or does not belong to this user");
        }

        p.setContent(post.getContent());
        p.setUpdatedAt(Timestamp.from(Instant.now()));
        p.setImageUrl(post.getImageUrl());
        return postRepo.save(p);
    }



    @Override
    public Post getPostBypostId(Long postId )
    {
        return postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

    }

    @Override
    public List<Post> getAllPostsByUser(Long userId)
    {
        return postRepo.findAllByUser_UserId(userId);
    }

    @Override
    public Post getPostByCampaignId(Long id) {

        return postRepo.findByCampaignId(id);
    }

    @Override
    public Long countPostsByUser(Long userId) {

        return postRepo.countAllByUser_UserId(userId);
    }

    @Override
    public Long countPostsByDate(LocalDate dateParam)
    {
        return postRepo.countPostsByDay(dateParam);
    }

    @Override
    public List<Post> getPostsByDate(LocalDate dateParam) {

        return postRepo.getPostsByDay(dateParam);
    }

    @Override
    public List<Post> getTopPostsByReacts( int topN)
    {
        return  postRepo.getTopPostsByReacts(topN);
    }
    @Override
    public Long countPostsToday(Long userId) {
        return postRepo.countPostsToday(userId);
    }
@Override
    public List<Post> getSpamPostsByUser(Long userId) {
        return postRepo.findSpamPosts(userId);
    }
@Override
    public boolean isPostSpam(Long postId) {
        return postRepo.findById(postId)
                .map(p -> p.getContent().toLowerCase().contains("spam") ||
                        p.getContent().toLowerCase().contains("http"))
                .orElseThrow(()-> new RuntimeException("post not found "));
    }


}
