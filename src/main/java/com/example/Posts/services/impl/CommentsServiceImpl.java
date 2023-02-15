package com.example.Posts.services.impl;

import com.example.Posts.dto.CommentDTO;
import com.example.Posts.dto.CommentReply;
import com.example.Posts.dto.PostComment;
import com.example.Posts.dto.ValidationStatus;
import com.example.Posts.entity.Comments;
import com.example.Posts.entity.Post;
import com.example.Posts.repository.CommentsRepository;
import com.example.Posts.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;


@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsRepository commentsRepository;

    @Override
    public Map<String,List<PostComment>> getCommentsForPost(String postId) {
        Optional<Comments> commentsCheck=commentsRepository.findById(postId);
        Map<String, List<PostComment>> commentsMap=new HashMap<>();
        if(commentsCheck.isPresent()) {
            commentsMap = commentsCheck.get().getComments();
        }
        return commentsMap;
    }

    @Override
    public ValidationStatus putCommentsForPost(String postId, String userId, String comment) {
        Optional<Comments> commentCheck=commentsRepository.findById(postId);
        if(commentCheck.isPresent()){
            Comments comments=commentCheck.get();
            Map<String,List<PostComment>> commentsMap=comments.getComments();
            PostComment postComment=new PostComment();
            postComment.setComment(comment);
            if(!commentsMap.containsKey(userId)){
                commentsMap.put(userId,new ArrayList<PostComment>());
            }
            commentsMap.get(userId).add(postComment);
            commentsRepository.save(comments);
            ValidationStatus validationStatus=new ValidationStatus();
            validationStatus.setIsvalid(true);
            return validationStatus;
        }
        else{
            Comments newComment=new Comments();
            PostComment postComment=new PostComment();
            postComment.setComment(comment);
            newComment.setPostId(postId);
            Map<String,List<PostComment>> hashMap=new HashMap<String,List<PostComment>>();
            List<PostComment> postCommentList=new ArrayList<PostComment>();
            postCommentList.add(postComment);
            hashMap.put(userId,postCommentList);
            newComment.setComments(hashMap);
            commentsRepository.insert(newComment);
            ValidationStatus validationStatus=new ValidationStatus();
            validationStatus.setIsvalid(true);
            return validationStatus;
        }


    }

    @Override
    public ValidationStatus putReplyForComment(String postId, String commenterId,int index, String replierId, String comment) {
        Comments comments=commentsRepository.findById(postId).get();
        Map<String,List<PostComment>> commentsMap=comments.getComments();
        PostComment postComment=commentsMap.get(commenterId).get(index);
        if(postComment.getReplies()==null){
            postComment.setReplies(new ArrayList<CommentReply>());
        }
        CommentReply commentReply=new CommentReply();
        commentReply.setUserId(replierId);
        commentReply.setComment(comment);
        postComment.getReplies().add(commentReply);
        commentsMap.get(commenterId).set(index, postComment);
        commentsRepository.save(comments);
        ValidationStatus validationStatus = new ValidationStatus();
        validationStatus.setIsvalid(true);
        return validationStatus;
    }
}
