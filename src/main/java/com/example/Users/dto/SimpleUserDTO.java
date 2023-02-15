package com.example.Users.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SimpleUserDTO {
    private String userId;
    private String userName;
    private String email;
    private Date dob;
    private boolean isPrivate;
    private String gender;
    private String displayPicture;
}
