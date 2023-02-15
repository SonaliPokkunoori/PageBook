package com.example.Posts.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetCommentsList {
    //Map<String,List<PostComment>> postComments;
    //String postId;
    List<GetCommentsListItem> comments;
}
