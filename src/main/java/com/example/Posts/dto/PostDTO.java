package com.example.Posts.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {

    //    private String postId;
    private String userId;
    private String postType;
    private String imageId;
    private String videoId;
    private String textId;
    private String caption;
    private List<String> postCategory;
//    private String friendDisplayPicture;
}
