package com.example.Posts.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {

    @Id
    private String userId;

    private String userName;

    private String email;

    private Date dob;

    private Boolean isPrivate;

    private String bio;

    private List<String> friends;

    private List<String> categories;

    private List<String> requests;

    private String password;

    private String gender;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String displayPicture;

}
