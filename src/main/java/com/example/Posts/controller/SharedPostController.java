package com.example.Posts.controller;

import com.example.Posts.dto.SharePostDTO;
import com.example.Posts.dto.ValidationStatus;

import com.example.Posts.entity.SharePost;
import com.example.Posts.services.SharePostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/share")
public class SharedPostController {

    @Autowired
    SharePostService sharePostService;

    @PostMapping("/shareNewPost")
    public ResponseEntity<ValidationStatus> shareNewPost (@RequestBody SharePostDTO sharePostDTO)
    {
        ValidationStatus validationStatus = new ValidationStatus();
        System.out.println(sharePostDTO);
        SharePost sharePostObject = new SharePost();
        BeanUtils.copyProperties(sharePostDTO, sharePostObject);
        sharePostService.shareNewPost(sharePostObject);
        validationStatus.setIsvalid(true);
        return new ResponseEntity<ValidationStatus>(validationStatus, HttpStatus.CREATED);

    }

    @GetMapping("/getAllSharedPosts/{userId}")
    public ResponseEntity<List<SharePost>> getAllSharedPosts(@PathVariable("userId") String userId)
    {
        return new ResponseEntity<List<SharePost>>(sharePostService.getAllSharedPostsOfUser(userId), HttpStatus.OK);
    }
}
