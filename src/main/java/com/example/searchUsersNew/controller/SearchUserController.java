package com.example.searchUsersNew.controller;

import com.example.searchUsersNew.dto.User;
import com.example.searchUsersNew.dto.UserDTO;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/search")
public class SearchUserController extends SolrDocumentList {


    @Autowired
    SolrClient solrClient;

    @GetMapping("/query/{userName}")
    public ResponseEntity<SolrDocumentList> searchUsers(@PathVariable("userName") String userName) throws IOException, SolrServerException {
        SolrClient solrClient = new HttpSolrClient.Builder("http://localhost:8981/solr/searchUsersNew").build();
        SolrQuery solrQuery = new SolrQuery();

        String uName="";
        for (int i=0;i<userName.length();i++){
            uName+=userName.charAt(i)+"*";
        }
        solrQuery.setQuery("userName:*"+uName);
//        solrQuery.setParam("0", productName);
        QueryResponse response = solrClient.query(solrQuery);
//        System.out.println(response);
        Object solrDocument = response.getResults().toArray();
        System.out.println(solrDocument);
        return new ResponseEntity<SolrDocumentList>(response.getResults(),HttpStatus.OK);
    }



    @PostMapping("/addUsers")
    public String addUserDetails(@RequestBody UserDTO users) {
        SolrInputDocument doc = new SolrInputDocument();
        User user=new User();
        User user1 = objectMapper(user, users);
        doc.addField("userId", user1.getUserId());
        doc.addField("userName", user1.getUserName());
        doc.addField("email",user1.getEmail());
        doc.addField("bio",user1.getBio());
        doc.addField("gender",user1.getGender());
        doc.addField("password",user1.getPassword());
        doc.addField("friends",user1.getFriends());
        doc.addField("requests",user1.getRequests());
        doc.addField("categories",user1.getCategories());
        doc.addField("dob",user1.getDob());
        doc.addField("firstName",user1.getFirstName());
        doc.addField("lastName",user1.getLastName());
        doc.addField("phoneNumber",user1.getPhoneNumber());
        doc.addField("private",user1.isPrivate);
        doc.addField("displayPicture",user1.getDisplayPicture());


        try {

            solrClient.add(doc);
            solrClient.commit();
            System.out.println("Data added");
            return "Data added to Solr successfully";

        } catch (SolrServerException | IOException e) {
            return "Failed to add data to Solr: " + e.getMessage();
        }

    }
    public User objectMapper(User user, UserDTO userDTO){
        user.setUserId(userDTO.getUserId());
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setBio(userDTO.getBio());
        user.setFriends(userDTO.getFriends());
        user.setRequests(userDTO.getRequests());
        user.setCategories(userDTO.getCategories());
        user.setGender(userDTO.getGender());
        user.setDob(userDTO.getDob());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPrivate(userDTO.isPrivate());
        user.setDisplayPicture(userDTO.getDisplayPicture());


        if(user.getUserId() == null){
            user.setUserId("876");
        }
        System.out.println(user);
        return user;
    }

}