package com.example.Posts.feignclients;

import com.example.Posts.dto.FriendsDTO;
import com.example.Posts.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value = "feignUser",url="http://10.20.5.60:8081/users")
public interface FeignUserService {

    @GetMapping("/getListOfFriends/{userId}")
    public ResponseEntity<FriendsDTO> getListOfFriends(@PathVariable("userId") String userId);

    @GetMapping("/getUsersByUserId/{userId}")
    public ResponseEntity<UserDTO> getUsersByUserId(@PathVariable("userId") String userId);

}