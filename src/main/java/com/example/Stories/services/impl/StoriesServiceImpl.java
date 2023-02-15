package com.example.Stories.services.impl;

import com.example.Stories.dto.*;
import com.example.Stories.entities.Stories;
import com.example.Stories.feignClients.FeignUserService;
import com.example.Stories.repository.StoriesRepository;
import com.example.Stories.services.StoriesService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoriesServiceImpl implements StoriesService {

    @Autowired
    StoriesRepository storiesRepository;

    @Autowired
    FeignUserService feignUserService;


    @Override
    public ValidationStatus uploadStory(StoriesDTO storiesDTO) {
        Stories stories = new Stories();
        ValidationStatus isValid = new ValidationStatus();
        BeanUtils.copyProperties(storiesDTO, stories);
        stories.setLocalDateTime(LocalDateTime.now());
        System.out.println(stories.getLocalDateTime());
        String userId1= stories.getUserId();
        List<Stories> storiesList=new ArrayList<>();
        List<Stories> stories1=storiesRepository.findAllByUserId(userId1);
        for (Stories story : stories1) {
            LocalDateTime currentTime=LocalDateTime.now();
            LocalDateTime uploadTime = story.getLocalDateTime();
            long noOfMinutes = ChronoUnit.MINUTES.between(uploadTime,currentTime);
            System.out.println(noOfMinutes);
            if(noOfMinutes<= 10){
                storiesList.add(story);
            }
        }
        if(storiesList.size() >= 1){
            isValid.setIsvalid(false);
            return isValid;
        }
        storiesRepository.save(stories);
        isValid.setIsvalid(true);
        return isValid;
    }

    @Override
    public StoriesListDTO getArchiveStories(String userId) {
        List<Stories> storiesList=new ArrayList<>();
        List<Stories> stories=storiesRepository.findAllByUserId(userId);
        System.out.println(stories);
        StoriesListDTO storiesListDTO=new StoriesListDTO();
        for (Stories story : stories) {
            LocalDateTime currentTime=LocalDateTime.now();
            LocalDateTime uploadTime = story.getLocalDateTime();
            long noOfMinutes = ChronoUnit.MINUTES.between(uploadTime,currentTime);
            System.out.println(noOfMinutes);
            if(noOfMinutes> 10){
                storiesList.add(story);
            }
        }
        System.out.println(storiesList);
        storiesListDTO.setStoriesList(storiesList);
        return storiesListDTO;
    }


    @Override
    public ValidationStatus deleteStory(String userId) {
        storiesRepository.deleteByUserId(userId);
        ValidationStatus isValid = new ValidationStatus();
        isValid.setIsvalid(true);
        return isValid;
    }

    @Override
    public StoriesListDTO getActiveStories(String userId) {
        ResponseEntity<FriendsDTO> response= feignUserService.getListOfFriends(userId);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FriendsDTO friendsDTO = mapper.convertValue(response.getBody(), FriendsDTO.class);
        System.out.println(friendsDTO);
        ValidationStatus isValid=new ValidationStatus();
        List<SimpleUserDTO> friendsList=friendsDTO.getFriends();
        List<Stories> friendStories=new ArrayList<>();
        List<Stories> storiesList=new ArrayList<>();
        StoriesListDTO storiesListDTO=new StoriesListDTO();
        if(friendsList==null){
            isValid.setIsvalid(false);
            storiesListDTO.setStoriesList(null);
            return storiesListDTO;
        }

        ResponseEntity<UserDTO> responseEntity= feignUserService.getUsersByUserId(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = objectMapper.convertValue(responseEntity.getBody(), UserDTO.class);

        for (int i=0;i<friendsList.size();i++) {
//            System.out.println(friend);
//            friendsList.get(i).setDisplayPicture(userDTO.getDisplayPicture());
            List<Stories> stories = storiesRepository.findAllByUserId(friendsList.get(i).getUserId());
            System.out.println(stories);

            for (Stories story : stories) {
                friendStories.add(story);
                System.out.println(friendStories);
            }
        }
        for (Stories story:friendStories) {
            LocalDateTime loginTime=LocalDateTime.now();
            LocalDateTime uploadTime=story.getLocalDateTime();
            long noOfMinutes = ChronoUnit.MINUTES.between(uploadTime,loginTime);
            System.out.println(noOfMinutes);
            if(noOfMinutes<= 10){
                storiesList.add(story);
            }
        }
        System.out.println(storiesList);
        storiesListDTO.setStoriesList(storiesList);
        return storiesListDTO;

        }

    @Override
    public StoriesListDTO getUserActiveStories(String userId) {
        List<Stories> storiesList=new ArrayList<>();
        List<Stories> stories=storiesRepository.findAllByUserId(userId);

//        ResponseEntity<UserDTO> responseEntity= feignUserService.getUsersByUserId(userId);
//        ObjectMapper objectMapper = new ObjectMapper();
//        UserDTO userDTO = objectMapper.convertValue(responseEntity.getBody(), UserDTO.class);
//
//        for (int i=0;i<stories.size();i++) {
//            stories.get(i).setDisplayPicture(userDTO.getDisplayPicture());
//        }
        StoriesListDTO activeStoriesListDTO= new StoriesListDTO();
        for (Stories story : stories) {
            LocalDateTime currentTime=LocalDateTime.now();
            LocalDateTime uploadTime = story.getLocalDateTime();
            long noOfMinutes = ChronoUnit.MINUTES.between(uploadTime,currentTime);
            System.out.println(noOfMinutes);
            if(noOfMinutes<= 10){
                storiesList.add(story);
            }
        }
        System.out.println(storiesList);
        activeStoriesListDTO.setStoriesList(storiesList);
        return activeStoriesListDTO;

    }

    @Override
    public ValidationStatus deleteCurrentStory(String userId) {
        Optional<Stories> stories =storiesRepository.findByUserId(userId);
        Stories stories1=new Stories();
        if(stories.isPresent()){
            stories1=stories.get();
        }
        String storyId=stories1.getStoryId();
        storiesRepository.deleteById(storyId);
        ValidationStatus isValid=new ValidationStatus();
        isValid.setIsvalid(true);
        return isValid;
    }


    @Override
    public Stories getStoriesByStoryId(String storyId){
        Optional<Stories> stories= storiesRepository.findById(storyId);
        Stories stories1=new Stories();
        if(stories.isPresent()){
            stories1=stories.get();
        }
        return stories1;
    }


}
