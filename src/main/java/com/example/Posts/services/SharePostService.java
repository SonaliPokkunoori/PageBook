package com.example.Posts.services;


import com.example.Posts.entity.SharePost;

import java.util.List;

public interface SharePostService {

    SharePost shareNewPost (SharePost sharePost);

    List<SharePost> getAllSharedPostsOfUser(String receiverUserId);



}
