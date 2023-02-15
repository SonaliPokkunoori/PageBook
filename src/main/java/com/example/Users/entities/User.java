package com.example.Users.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = User.COLLECTION_NAME)
public class User {
    public static final String COLLECTION_NAME = "Users";

    @Id
    public String userId;

    public String userName;

    public String email;

    public Date dob;

    public Boolean isPrivate;

    public String bio;

    public String displayPicture;

    public List<String> friends;

    public List<String> categories;

    public List<String> requests;

    public List<String> listOfBusiness;

    public String password;

    public String gender;

    private String firstName;

    private String lastName;

    private String phoneNumber;

}

