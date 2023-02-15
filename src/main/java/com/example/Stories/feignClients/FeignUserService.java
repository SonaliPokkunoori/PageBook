package com.example.Stories.feignClients;

import com.example.Stories.dto.FriendsDTO;
import com.example.Stories.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "feignUser",url="http://localhost:8081/users")
public interface FeignUserService {

    @GetMapping("/getListOfFriends/{userId}")
    public ResponseEntity<FriendsDTO> getListOfFriends(@PathVariable("userId") String userId);

    @GetMapping("/getUsersByUserId/{userId}")
    public ResponseEntity<UserDTO> getUsersByUserId(@PathVariable("userId") String userId);
}
