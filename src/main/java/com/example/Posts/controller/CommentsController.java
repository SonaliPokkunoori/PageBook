package com.example.Posts.controller;

import com.example.Posts.dto.*;
import com.example.Posts.entity.Post;
import com.example.Posts.feignclients.FeignAnalyticsService;
import com.example.Posts.feignclients.FeignUserService;
import com.example.Posts.services.CommentsService;
import com.example.Posts.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    CommentsService commentsService;

    @Autowired
    FeignUserService feignUserService;

    @Autowired
    FeignAnalyticsService feignAnalyticsService;

    @Autowired
    PostService postService;

    @GetMapping("/getCommentsForPost/{postId}")
    public ResponseEntity<GetCommentsList> getCommentsForPost(@PathVariable("postId") String postId){
        GetCommentsList getCommentsList =new GetCommentsList();
        //getCommentsList.setPostId(postId);
        getCommentsList.setComments(new ArrayList<>());
        Map<String,List<PostComment>> hashMap=commentsService.getCommentsForPost(postId);

        for(String userId:hashMap.keySet()){
            List<PostComment> userComments=hashMap.get(userId);
            for(int j=0;j<userComments.size();j++){
                GetCommentsListItem getCommentsListItem=new GetCommentsListItem();
                getCommentsListItem.setComment(userComments.get(j).getComment());
                if(userComments.get(j).getReplies()==null){
                    getCommentsListItem.setReplies(new ArrayList<CommentReply>());
                }
                else {
                    getCommentsListItem.setReplies(userComments.get(j).getReplies());
                }
                ResponseEntity<UserDTO> responseEntity=feignUserService.getUsersByUserId(userId);
                ObjectMapper objectMapper=new ObjectMapper();
                UserDTO userDTO = objectMapper.convertValue(responseEntity.getBody(), UserDTO.class);

                getCommentsListItem.setUserName(feignUserService.getUsersByUserId(userId).getBody().getUserName());
                getCommentsListItem.setUserId(userId);
                getCommentsListItem.setDisplayPicture(userDTO.getDisplayPicture());
                getCommentsList.getComments().add(getCommentsListItem);
            }
        }
        return new ResponseEntity<GetCommentsList>(getCommentsList,HttpStatus.OK);
    }
    @PostMapping("/putCommentsForPost")
    public ResponseEntity<Object> putCommentsForPost(@RequestBody CommentDTO commentDTO){

        AndroidPostDTO androidPostDTO=postService.getPostByPostId(commentDTO.getPostId());
        AnalyticsReactionDTO analyticsReactionDTO=new AnalyticsReactionDTO();
        analyticsReactionDTO.setUserId(commentDTO.getUserId());
        analyticsReactionDTO.setPostId(commentDTO.getPostId());
        analyticsReactionDTO.setCategories(androidPostDTO.getPost().getPostCategory());
        analyticsReactionDTO.setActionTime(LocalDateTime.now());
        analyticsReactionDTO.setActionType("comment");
        feignAnalyticsService.postMsg(analyticsReactionDTO);


        return new ResponseEntity<Object>(commentsService.putCommentsForPost(commentDTO.getPostId(),commentDTO.getUserId(),commentDTO.getComment()),HttpStatus.OK);
    }
    @PostMapping("/putReplyForComment")
    public ResponseEntity<Object> putReplyForComment(@RequestBody ReplyDTO replyDTO){
        return new ResponseEntity<Object>(commentsService.putReplyForComment(replyDTO.getPostId(),replyDTO.getCommenterId(),replyDTO.getIndex(),replyDTO.getReplierId(),replyDTO.getComment()),HttpStatus.OK);
    }
}
