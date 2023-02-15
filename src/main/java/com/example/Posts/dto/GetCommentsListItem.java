package com.example.Posts.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetCommentsListItem {
    String userId;
    String userName;
    String comment;
    List<CommentReply> replies;
    String displayPicture;
}
