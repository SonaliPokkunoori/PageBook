package com.example.Users.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnalyticsFollowDTO {

    public String platformId="pagebook";
    public String postId="";
    public String userId;
    String actionType;
    LocalDateTime actionTime;
    List<String> categories;
}
