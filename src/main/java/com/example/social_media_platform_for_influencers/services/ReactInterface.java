package com.example.social_media_platform_for_influencers.services;

import com.example.social_media_platform_for_influencers.entities.React;
import com.example.social_media_platform_for_influencers.enums.ReactType;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public interface ReactInterface {
    React addreactBypostId(React react,Long postId);
    React addreactBycommentId (React react,Long commentId);
    void deletereactbypostId(Long postId );
    void deleteAllByCommentId(Long commentId);
    void deleteReactById(Long reactId);
    Long countReactByPostByreactType(Long postId,ReactType reactType);
    Long countReactByPost(Long postId);
    Long countReactByComment(Long commentId);
    Long countReactByCommentByreactType(Long commentId,ReactType reactType);
    List<React> getReactsByPost(Long postId);
    List<React> getReactsByComment(Long commentId);
    React updateReactByPostIdByreactType(ReactType reactType, Long reactId);


}
