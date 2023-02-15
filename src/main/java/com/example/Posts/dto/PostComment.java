package com.example.Posts.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostComment {
    String comment;
    List<CommentReply> replies;
}
