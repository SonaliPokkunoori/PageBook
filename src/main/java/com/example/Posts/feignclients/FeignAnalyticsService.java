package com.example.Posts.feignclients;

import com.example.Posts.dto.AnalyticsReactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignAnalytics",url="http://10.20.5.13:8075/kafka")
public interface FeignAnalyticsService {

    @PostMapping("/postMsg")
    public void postMsg(@RequestBody AnalyticsReactionDTO analyticsReactionDTO);
}
