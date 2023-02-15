package com.example.Users.controller;

import com.example.Users.dto.BusinessDTO;
import com.example.Users.dto.ValidationStatus;
import com.example.Users.entities.Business;
import com.example.Users.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @PostMapping("/addBusinessUsers")
    public ResponseEntity<Business> addBusinessUsers(@RequestBody BusinessDTO businessDTO) {
        Business business=businessService.addBusinessUsers(businessDTO);
        return new ResponseEntity<Business>(business,HttpStatus.OK);
    }

    @GetMapping("/getBusinessByBusinessId/{businessId}")
    public ResponseEntity<Business> getBusinessByBusinessId(@PathVariable("businessId") String businessId){
        Business business=businessService.getBusinessById(businessId);
        return new ResponseEntity<Business>(business,HttpStatus.OK);
    }

    @GetMapping("/getBusinessByBusinessName/{businessName}")
    public ResponseEntity<Business> getBusinessByBusinessName(@PathVariable("businessName") String businessName){
        Business business=businessService.getBusinessByName(businessName);
        return new ResponseEntity<Business>(business,HttpStatus.OK);
    }

    @PostMapping("/addOrUpdateBio/{businessId}/{bio}")
    public ResponseEntity<ValidationStatus> addOrUpdateBio(@PathVariable("businessId") String businessId,@PathVariable("bio") String bio){
        ValidationStatus isValid=businessService.addOrUpdateBio(businessId,bio);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }

    @PostMapping("/sendModeratorRequest/{businessId}/{userId}")
    public ResponseEntity<ValidationStatus> sendModeratorRequest(@PathVariable("businessId") String businessId,@PathVariable("userId") String userId){
        ValidationStatus isValid=businessService.sendModeratorRequest(businessId,userId);
        return new ResponseEntity<ValidationStatus>(isValid,HttpStatus.OK);
    }
}
