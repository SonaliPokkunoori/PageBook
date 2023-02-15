package com.example.Posts.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = SharePost.COLLECTION_NAME)
public class SharePost {

    public static final String COLLECTION_NAME="Shared";

    private String receiverUserId;
    private String senderUserId;
    private String postId;
    private String postCaption;
    private String senderUserName;
    private String recieverUserName;

}
