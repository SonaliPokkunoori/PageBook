package com.example.searchUsersNew.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@SolrDocument(solrCoreName = "searchUser")
@Component
public class User {

    @Indexed(name = "userId", type = "string")
    public String userId;
    @Indexed(name = "userName", type = "string")
    public String userName;
    @Indexed(name = "accountType", value = "string")
    public boolean isPrivate;
    @Indexed(name = "userEmail", value = "string")
    private String email;
    @Indexed(name = "bio",value = "string")
    private String bio;
    @Indexed(name = "friends",value = "string", stored = true,searchable = true)
    private List<String> friends;
    @Indexed(name = "interests",value = "string", stored = true,searchable = true)
    private List<String> categories;
    @Indexed(name = "requests",value = "string", stored = true,searchable = true)
    private List<String> requests;
    @Indexed(name = "userPassword",value = "string")
    private String password;
    @Indexed(name = "gender",value = "string")
    private String gender;
    @Indexed(name = "dateOfBirth",value = "tdate",searchable = true)
    private Date dob;
    @Indexed(name = "firstName",value = "string")
    private String firstName;
    @Indexed(name = "lastName",value = "string")
    private String lastName;
    @Indexed(name = "phoneNumber",value = "string")
    private String phoneNumber;
    @Indexed(name = "displayPicture",value = "string")
    private String displayPicture;

}
