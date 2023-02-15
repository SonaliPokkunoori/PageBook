package com.example.searchUsersNew.feignClient;


import com.example.searchUsersNew.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "feignUser",url="http://10.20.5.60:8081/users")
public interface FeignUserService {

    @GetMapping("/getUsersByUserId/{userId}")
    public ResponseEntity<UserDTO> getUsersByUserId(@PathVariable("userId") String userId);

}