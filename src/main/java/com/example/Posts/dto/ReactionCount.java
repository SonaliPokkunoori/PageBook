package com.example.Posts.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ReactionCount {
    Map<Integer,Integer> reactionCount;
}
