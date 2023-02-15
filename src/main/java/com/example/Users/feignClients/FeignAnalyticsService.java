package com.example.Users.feignClients;

import com.example.Users.dto.AnalyticsFollowDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignAnalytics",url="http://10.20.5.13:8075/kafka")
public interface FeignAnalyticsService {

    @PostMapping("/postMsg")
    public void postMsg(@RequestBody AnalyticsFollowDTO analyticsFollowDTO);
}
