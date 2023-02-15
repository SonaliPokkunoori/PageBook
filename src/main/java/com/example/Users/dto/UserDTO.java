package com.example.Users.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.awt.*;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO {

    public String userId;

    private String userName;

    private String email;

    private Date dob;

    private Boolean isPrivate;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String gender;

    private List<String> categories;


//    private String age;

//    private String bio;

//    private String displayPicture;
//
//    private List<String> friends;
//
//    private List<String> interests;
//
//    private List<String> requests;
//
//    private List<String> listOfBusinessPages;

}
