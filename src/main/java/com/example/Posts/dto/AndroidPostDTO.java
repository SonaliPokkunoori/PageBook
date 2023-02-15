package com.example.Posts.dto;

import com.example.Posts.entity.Post;
import lombok.Data;

import java.util.Map;

@Data
public class AndroidPostDTO {
    Post post;
    Map<Integer,Integer> reacitons;
    String displayPicture;
//    String friendDisplayPicture;
}
