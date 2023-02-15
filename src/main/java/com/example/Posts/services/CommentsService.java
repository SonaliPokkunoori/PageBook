package com.example.Posts.services;

import com.example.Posts.dto.CommentDTO;
import com.example.Posts.dto.PostComment;
import com.example.Posts.dto.ValidationStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface CommentsService {
    public Map<String,List<PostComment>> getCommentsForPost(String postId);
    public ValidationStatus putCommentsForPost(String postId, String userId, String comment);
    public ValidationStatus putReplyForComment(String postId,String commenterId,int index,String replierId,String comment);
}
