package com.example.Posts.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SimpleUsersDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private Date dateOfBirth;
    private String accountType;
    private String gender;
}
