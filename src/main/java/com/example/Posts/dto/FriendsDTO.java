package com.example.Posts.dto;

import lombok.Data;

import java.util.List;

@Data
public class FriendsDTO {
    public List<SimpleUsersDTO> friends;
}
