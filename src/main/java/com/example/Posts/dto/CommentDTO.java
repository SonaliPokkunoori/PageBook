package com.example.Posts.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {
    String postId;
    String userId;
    String comment;
    List<ReplyDTO> replies;
}
