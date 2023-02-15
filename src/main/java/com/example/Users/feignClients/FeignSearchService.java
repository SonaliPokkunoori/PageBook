package com.example.Users.feignClients;

import com.example.Users.dto.UserDTO;
import com.example.Users.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignSearch",url="http://localhost:8084/search")
public interface FeignSearchService {


    @PostMapping("/addUsers")
    public String addUserDetails(@RequestBody User user);
}
