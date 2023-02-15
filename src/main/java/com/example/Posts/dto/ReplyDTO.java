package com.example.Posts.dto;

import lombok.Data;

@Data
public class ReplyDTO {
    String postId;
    String commenterId;
    int index;
    String replierId;
    String comment;
}
