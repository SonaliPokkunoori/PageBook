package com.example.Users.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = Business.COLLECTION_NAME2)
public class Business {

    public static final String COLLECTION_NAME2 = "Business" ;

    @Id
    public String businessId;
    public String businessName;
    public String bio;
    public String businessEmail;
    public String businessPassword;
    public String displayPicture;
    public String category;
    public List<String> interests;
    public List<String> followers;
    public List<String> following;
    public List<String> moderators;

}
