package com.example.Posts.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = Post.COLLECTION_NAME)
@Data
public class Post {
    public static final String COLLECTION_NAME = "Posts";

    @Id
    private String postId;
    private String userId;
    private String postType;
    private String imageId;
    private String videoId;
    private String textId;
    private String caption;
    private List<String> postCategory;
    private String userName;

}
