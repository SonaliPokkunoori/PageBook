package com.example.Stories.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class StoriesDTO {

    public String userId;
    public String storyType;
//    public List<String> viewers;
    public String imageUrl;
    public String videoUrl;
    public String text;
    public String displayPicture;

}
