package com.example.Posts.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
public class Reactions {
    @Id
    String postId;
    Map<String,Integer> reactions;  // In map String is userId, Integer is reaction type
}
