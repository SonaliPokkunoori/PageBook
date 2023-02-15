package com.example.Users.controller;

import com.example.Users.dto.*;
import com.example.Users.entities.User;
import com.example.Users.feignClients.FeignAnalyticsService;
import com.example.Users.feignClients.FeignSearchService;
//import com.example.Users.feignClients.FeignStoryService;
import com.example.Users.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FeignSearchService feignSearchService;

    @Autowired
    FeignAnalyticsService feignAnalyticsService;

    @PostMapping("/addUsers")
    public ResponseEntity<User> addUsers(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        User user1= userService.addUsers(userDTO);
        User user= new User();
        user.setUserId(user1.getUserId());
        user.setUserName(user1.getUserName());
        user.setEmail(user1.getEmail());
        user.setPassword(user1.getPassword());
        user.setBio(user1.getBio());
        user.setFriends(user1.getFriends());
        user.setRequests(user1.getRequests());
        user.setCategories(user1.getCategories());
        user.setGender(user1.getGender());
        user.setDob(user1.getDob());
        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        user.setPhoneNumber(user1.getPhoneNumber());
//        Boolean isPrivate= user1.isPrivate;
//        user.setPrivate(isPrivate);
        user.setDisplayPicture(user.getDisplayPicture());
//        System.out.println(isPrivate);

        feignSearchService.addUserDetails(user);
        return new ResponseEntity<User>(user1, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginStatus> login(@RequestBody Credentials credentials){
        return new ResponseEntity<LoginStatus>(userService.login(credentials),HttpStatus.OK);
    }

    @GetMapping("/getUsersByUserId/{userId}")
    public ResponseEntity<User> getUsersByUserId(@PathVariable("userId") String userId) {
        Optional<User> userCheck = userService.getUsersByUserId(userId);
        User user=new User();
        if(userCheck.isPresent()==true){
            user=userCheck.get();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/getUsersByUserName/{userName}")
    public ResponseEntity<User> getUsersByUserName(@PathVariable("userName") String userName) {
        Optional<User> userCheck = userService.getUsersByUserName(userName);
        User user=new User();
        if(userCheck.isPresent()==true){
            user=userCheck.get();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/sendFriendRequest/{userId}/{friendUserId}")
    public ResponseEntity<ValidationStatus> sendFriendRequest(@PathVariable("userId") String userId, @PathVariable("friendUserId") String friendUserId){
        ValidationStatus isValid=userService.sendFriendRequest(userId,friendUserId);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserByUserId/{userId}")
    public ResponseEntity<ValidationStatus> deleteUserByUserId(@PathVariable("userId") String userId){
        ValidationStatus isValid=userService.deleteUserByUserId(userId);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }

    @DeleteMapping("/deleteFriendRequest/{userId}/{friendUserId}")
    public ResponseEntity<ValidationStatus> deleteFriendRequest(@PathVariable("userId") String userId,@PathVariable("friendUserId") String friendUserId){
        ValidationStatus isValid=userService.deleteFriendRequest(userId,friendUserId);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }

    @GetMapping("/getAllFriendRequests/{userId}")
    public ResponseEntity<SimpleUsersList> getAllFriendRequests(@PathVariable("userId") String userId) {
        SimpleUsersList simpleUsersList=new SimpleUsersList();
        simpleUsersList.setSimpleusers(userService.getAllFriendRequests(userId));
        return new ResponseEntity<SimpleUsersList>(simpleUsersList, HttpStatus.OK);
    }

    @PostMapping("/acceptFriendRequest/{userId}/{friendUserId}")
    public ResponseEntity<ValidationStatus> acceptFriendRequest(@PathVariable("userId") String userId,@PathVariable("friendUserId") String friendUserId){
        ValidationStatus isValid=userService.acceptFriendRequest(userId,friendUserId);

        ResponseEntity<User> responseEntity=getUsersByUserId(userId);
        ObjectMapper objectMapper=new ObjectMapper();
        User user = objectMapper.convertValue(responseEntity.getBody(), User.class);

        AnalyticsFollowDTO analyticsFollowDTO=new AnalyticsFollowDTO();
        analyticsFollowDTO.setActionTime(LocalDateTime.now());
        analyticsFollowDTO.setActionType("follow");
        analyticsFollowDTO.setUserId(friendUserId);
        analyticsFollowDTO.setCategories(user.getCategories());
        feignAnalyticsService.postMsg(analyticsFollowDTO);

        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }

    @GetMapping("/getListOfFriends/{userId}")
    public ResponseEntity<FriendsDTO> getListOfFriends(@PathVariable("userId") String userId){
        FriendsDTO friendsList=userService.getListOfFriends(userId);
        return new ResponseEntity<FriendsDTO>(friendsList,HttpStatus.OK);
    }

    @PostMapping("/addOrUpdateBio/{userId}/{bio}")
    public ResponseEntity<ValidationStatus> addOrUpdateBio(@PathVariable("userId") String userId,@PathVariable("bio") String bio){
        ValidationStatus isValid=userService.addOrUpdateBio(userId,bio);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }

    @PostMapping("/addOrUpdateDisplayPicture")
    public ResponseEntity<ValidationStatus> addOrUpdateDisplayPicture(@RequestBody DisplayPictureDTO displayPictureDTO){
        ValidationStatus isValid=userService.addOrUpdateDisplayPicture(displayPictureDTO);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }

    @DeleteMapping("/deleteFriend/{userId}/{friendUserId}")
    public ResponseEntity<ValidationStatus> deleteFriend(@PathVariable("userId") String userId,@PathVariable("friendUserId") String friendUSerId){
        ValidationStatus isValid=userService.deleteFriend(userId,friendUSerId);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }

    @GetMapping("/isFriend/{userId}/{friendUserId}")
    public ResponseEntity<ValidationStatus> isFriend(@PathVariable("userId") String userId,@PathVariable("friendUserId") String friendUserId){
        ValidationStatus isValid=userService.isFriend(userId,friendUserId);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }
}
