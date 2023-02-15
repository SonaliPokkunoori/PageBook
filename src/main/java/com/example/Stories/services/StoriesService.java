package com.example.Stories.services;

import com.example.Stories.dto.FriendsDTO;
import com.example.Stories.dto.StoriesDTO;
import com.example.Stories.dto.StoriesListDTO;
import com.example.Stories.dto.ValidationStatus;
import com.example.Stories.entities.Stories;

import java.util.List;

public interface StoriesService {

    public ValidationStatus uploadStory(StoriesDTO storiesDTO);
    public StoriesListDTO getArchiveStories(String userId);
    public Stories getStoriesByStoryId(String storyId);
    public ValidationStatus deleteStory(String userId);
    public StoriesListDTO getActiveStories(String userId);
    public StoriesListDTO getUserActiveStories(String userId);
    public ValidationStatus deleteCurrentStory(String userId);
}
