package com.example.Posts.controller;


import com.example.Posts.dto.*;
import com.example.Posts.entity.Post;
import com.example.Posts.feignclients.FeignUserService;
import com.example.Posts.services.PostService;
import com.example.Posts.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FeignUserService feignUserService;

    @PostMapping("/addNewPost")
    public ResponseEntity<ValidationStatus> addNewPost(@RequestBody PostDTO postDTO)
    {
        ValidationStatus validationStatus = new ValidationStatus();
        System.out.println(postDTO);
        Post postObject = new Post();
        BeanUtils.copyProperties(postDTO, postObject);
        ResponseEntity<UserDTO> responseEntity=feignUserService.getUsersByUserId(postDTO.getUserId());
        ObjectMapper mapper=new ObjectMapper();
        UserDTO userDTO = mapper.convertValue(responseEntity.getBody(), UserDTO.class);
        System.out.println(userDTO);
        postObject.setUserName(userDTO.getUserName());
        System.out.println(postObject);
        Post postCreated = postService.addNewPost(postObject);
        validationStatus.setIsvalid(true);
        return new ResponseEntity<ValidationStatus>(validationStatus, HttpStatus.CREATED);
    }

    @GetMapping("/getAllFriendsPosts/{userId}")
    public ResponseEntity<AndroidPostListDTO> getAllFriendsPosts(@PathVariable("userId") String userId){
        AndroidPostListDTO androidPostListDTO=new AndroidPostListDTO();
        androidPostListDTO.setAndroidPostDTOList(postService.getAllFriendsPosts(userId));


        return new ResponseEntity<AndroidPostListDTO>(androidPostListDTO,HttpStatus.OK);
    }

    @GetMapping("/getAllPersonalPosts/{userId}")
    public ResponseEntity<AndroidPostListDTO> getAllPosts(@PathVariable("userId") String userId)
    {
        AndroidPostListDTO androidPostListDTO=new AndroidPostListDTO();
        androidPostListDTO.setAndroidPostDTOList(postService.getAllPosts(userId));
        //androidPostListDTO.getAndroidPostDTOList().addAll(postService.getAllPosts(userId));
        return new ResponseEntity<AndroidPostListDTO>(androidPostListDTO, HttpStatus.OK);
    }
    @GetMapping("/getOthersPersonalPosts/{loggedInUserId}/{otherUserId}")
    public ResponseEntity<ValidationStatus> getOthersPersonalPosts(@PathVariable("loggedInUserId") String loggedInUserId,@PathVariable("otherUserId") String otherUserId){

        return new ResponseEntity<ValidationStatus>(postService.getOthersPersonalPosts(loggedInUserId,otherUserId),HttpStatus.OK);
    }
    @GetMapping("/getPostByPostId/{postId}")
    public ResponseEntity<AndroidPostDTO> getPostByPostId(@PathVariable("postId") String postId)
    {
        return new ResponseEntity<AndroidPostDTO>(postService.getPostByPostId(postId), HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{postId}/{userId}")
    public ResponseEntity<ValidationStatus> deletepost(@PathVariable("postId") String postId, @PathVariable("userId") String userId)
    {
        ValidationStatus validationStatus = new ValidationStatus();
        validationStatus.setIsvalid(postService.deletePostOfUser(postId,userId));
        //validationStatus.setIsvalid(true);
        return new ResponseEntity<ValidationStatus>(validationStatus,HttpStatus.OK);
    }

}
