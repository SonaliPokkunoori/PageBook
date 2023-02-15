package com.example.Posts.controller;

import com.example.Posts.dto.AnalyticsReactionDTO;
import com.example.Posts.dto.AndroidPostDTO;
import com.example.Posts.dto.ReactionCount;
import com.example.Posts.dto.ValidationStatus;
import com.example.Posts.entity.Post;
import com.example.Posts.entity.Reactions;
import com.example.Posts.feignclients.FeignAnalyticsService;
import com.example.Posts.services.PostService;
import com.example.Posts.services.impl.PostServiceImpl;
import com.example.Posts.services.impl.ReactionsServiceImpl;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reactions")
public class ReactionsController {
    @Autowired
    ReactionsServiceImpl reactionsService;

    @Autowired
    FeignAnalyticsService feignAnalyticsService;

    @Autowired
    PostService postService;

    @GetMapping("/getReactionStatus/{postId}/{userId}")
    public ResponseEntity<Integer> getReactionStatus(@PathVariable("postId") String postId,@PathVariable("userId") String userId){
        System.out.println("getting like status");
        int reactionStatus=reactionsService.getReactionStatus(postId,userId);
        return new ResponseEntity<Integer>(reactionStatus,HttpStatus.OK);
    }

    @PostMapping("/putOrUpdateReactionForPost/{postId}/{userId}/{reactionType}")
    public ResponseEntity<ValidationStatus> putReactionForPost(@PathVariable("postId") String postId, @PathVariable("userId") String userId, @PathVariable("reactionType") int reactionType){
        System.out.println("updating likes");
        Reactions reactions=new Reactions();
        reactions.setPostId(postId);
        Map<String,Integer> hashMap=new HashMap<String,Integer>();
        hashMap.put(userId,reactionType);
        reactions.setReactions(hashMap);
        reactionsService.putReactionForPost(postId,userId,reactionType);
        ValidationStatus validationStatus=new ValidationStatus();
        validationStatus.setIsvalid(true);

        AndroidPostDTO androidPostDTO =postService.getPostByPostId(postId);
        AnalyticsReactionDTO analyticsReactionDTO=new AnalyticsReactionDTO();
//        analyticsReactionDTO.setPlatformId("pagebook");
        analyticsReactionDTO.setUserId(userId);
        analyticsReactionDTO.setPostId(postId);
        analyticsReactionDTO.setCategories(androidPostDTO.getPost().getPostCategory());
        analyticsReactionDTO.setActionTime(LocalDateTime.now());
        if(reactionType==1) {
            analyticsReactionDTO.setActionType("like");
        }
        if (reactionType==2){
            analyticsReactionDTO.setActionType("dislike");
        }
        feignAnalyticsService.postMsg(analyticsReactionDTO);


        return new ResponseEntity<ValidationStatus>(validationStatus,HttpStatus.OK);
    }
    @DeleteMapping("/removeReactionFromPost/{postId}/{userId}")
    public ResponseEntity<ValidationStatus> removeReactionFromPost(@PathVariable("postId") String postId, @PathVariable("userId") String userId){
        System.out.println("removing likes");
        ValidationStatus validationStatus=reactionsService.removeReactionForPost(postId,userId);
        return new ResponseEntity<ValidationStatus>(validationStatus,HttpStatus.OK);
    }
    @GetMapping("/getReactionCountForPost/{postId}")
    public ResponseEntity<ReactionCount> getReactionCountForPost(@PathVariable("postId") String postId){
        ReactionCount reactionCount=new ReactionCount();
        reactionCount.setReactionCount(reactionsService.getReactionCountForPost(postId));
        return new ResponseEntity<ReactionCount>(reactionCount,HttpStatus.OK);
    }

}
