package com.example.Posts.services;

import com.example.Posts.dto.ValidationStatus;
import com.example.Posts.entity.Reactions;

import java.util.Map;

public interface ReactionService {
    public Integer getReactionStatus(String postId,String userId);
    public ValidationStatus putReactionForPost(String postId,String userId,int reactionType);
    public ValidationStatus removeReactionForPost(String postId,String userId);
    public Map<Integer,Integer> getReactionCountForPost(String postId);
}
