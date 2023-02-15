package com.example.Posts.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnalyticsReactionDTO {

    public String platformId="pagebook";
    public String postId;
    public String userId;
    String actionType;
    LocalDateTime actionTime;
    List<String> categories;
}
