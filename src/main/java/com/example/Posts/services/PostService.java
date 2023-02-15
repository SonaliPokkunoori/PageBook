package com.example.Posts.services;

import com.example.Posts.dto.AndroidPostDTO;
import com.example.Posts.dto.ValidationStatus;
import com.example.Posts.entity.Post;

import java.util.List;
import java.util.Optional;


public interface PostService {
    Post addNewPost (Post newPost);
    List<AndroidPostDTO> getAllPosts(String userId);
    AndroidPostDTO getPostByPostId(String postId);
    boolean deletePostOfUser(String postId, String userId);
    List<AndroidPostDTO> getAllFriendsPosts(String userId);
    ValidationStatus getOthersPersonalPosts(String loggedInUserId, String otherUserId);
}
