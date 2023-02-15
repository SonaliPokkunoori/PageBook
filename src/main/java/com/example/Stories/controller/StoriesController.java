package com.example.Stories.controller;


import com.example.Stories.dto.FriendsDTO;
import com.example.Stories.dto.StoriesDTO;
import com.example.Stories.dto.StoriesListDTO;
import com.example.Stories.dto.ValidationStatus;
import com.example.Stories.entities.Stories;
import com.example.Stories.services.StoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stories")
public class StoriesController {

    @Autowired
    StoriesService storiesService;

    @PostMapping("/uploadStory")
    public ResponseEntity<ValidationStatus> uploadStory(@RequestBody StoriesDTO storiesDTO) {
        ValidationStatus isValid = storiesService.uploadStory(storiesDTO);
        return new ResponseEntity<ValidationStatus>(isValid, HttpStatus.OK);
    }

    @DeleteMapping("/deleteStory/{userId}")
    public ResponseEntity<ValidationStatus> deleteStory(@PathVariable("userId") String userId) {
        ValidationStatus isValid = storiesService.deleteStory(userId);
        return new ResponseEntity<ValidationStatus>(isValid, HttpStatus.OK);
    }

    @GetMapping("/getArchiveStories/{userId}")
    public ResponseEntity<StoriesListDTO> getStoriesByUserId(@PathVariable("userId") String userId) {
        StoriesListDTO storiesListDTO=storiesService.getArchiveStories(userId);
        return new ResponseEntity<StoriesListDTO>(storiesListDTO, HttpStatus.OK);
    }

    @GetMapping("/getStoriesByStoryId/{storyId}")
    public ResponseEntity<Stories> getStoriesByStoryId(@PathVariable("storyId") String storyId) {
        Stories stories = storiesService.getStoriesByStoryId(storyId);
        return new ResponseEntity<Stories>(stories, HttpStatus.OK);
    }

    @GetMapping("/getFriendsActiveStories/{userId}")
    public ResponseEntity<StoriesListDTO> getActiveStories(@PathVariable("userId") String userId){
        StoriesListDTO storiesListDTO=storiesService.getActiveStories(userId);
        return new ResponseEntity<StoriesListDTO>(storiesListDTO,HttpStatus.OK);
//        FriendsDTO friendsDTO=storiesService.getActiveStories(userId);
//        return new ResponseEntity<FriendsDTO>(friendsDTO,HttpStatus.OK);
    }

    @GetMapping("/getUserActiveStories/{userId}")
    public ResponseEntity<StoriesListDTO> getUserActiveStories(@PathVariable("userId") String userId){
        StoriesListDTO storiesListDTO = storiesService.getUserActiveStories(userId);
        return new ResponseEntity<StoriesListDTO>(storiesListDTO,HttpStatus.OK);
    }

//    @DeleteMapping("/deleteUserStories/{userId}")
//    public ResponseEntity<ValidationStatus> deleteUserStories(@PathVariable("userId") String userId){
//        ValidationStatus isValid=storiesService.deleteCurrentStory(userId);
//        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
//    }

}
