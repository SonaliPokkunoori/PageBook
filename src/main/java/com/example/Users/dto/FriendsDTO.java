package com.example.Users.dto;

import lombok.Data;

import java.util.List;

@Data
public class FriendsDTO {

    public List<SimpleUserDTO> friends;
}
