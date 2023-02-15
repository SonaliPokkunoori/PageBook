package com.example.Posts.dto;


import lombok.Data;

@Data
public class SharePostDTO {

    //    @Id
//    private String postId;
    private String receiverUserId;
    private String senderUserId;
    private String postId;
    private String postCaption;
}
