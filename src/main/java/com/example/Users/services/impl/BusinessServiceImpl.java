package com.example.Users.services.impl;

import com.example.Users.dto.BusinessDTO;
import com.example.Users.dto.ValidationStatus;
import com.example.Users.entities.Business;
import com.example.Users.entities.User;
import com.example.Users.repository.BusinessRepository;
import com.example.Users.repository.UserRepository;
import com.example.Users.services.BusinessService;
import com.example.Users.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public Business addBusinessUsers(BusinessDTO businessDTO) {
        Business business=new Business();
        BeanUtils.copyProperties(businessDTO,business);
        return business;
    }

    @Override
    public Business getBusinessById(String businessId) {
        Optional<Business> business=businessRepository.findById(businessId);
        Business business1=new Business();
        if(business.isPresent()){
            business1=business.get();
        }
        return business1;
    }

    @Override
    public Business getBusinessByName(String businessName) {
        Optional<Business> business=businessRepository.findByBusinessName(businessName);
        Business business1=new Business();
        if(business.isPresent()){
            business1=business.get();
        }
        return business1;
    }

    @Override
    public ValidationStatus addOrUpdateBio(String businessId, String bio) {
        ValidationStatus isValid=new ValidationStatus();
        Optional<Business> business=businessRepository.findById(businessId);
        Business business1=new Business();
        if(business.isPresent()){
            business1=business.get();
        }
        business1.setBio(bio);
        businessRepository.save(business1);
        isValid.setIsvalid(true);
        return isValid;
    }

    @Override
    public ValidationStatus sendModeratorRequest(String businessId, String userId) {
        ValidationStatus isValid=new ValidationStatus();
        Optional<Business> user1=businessRepository.findById(businessId);
        Optional<User> user2=userRepository.findById(userId);
        Business businessUser=new Business();
        User moderatorUser=new User();
        if(user1.isPresent()){
            businessUser=user1.get();
        }
        if(user2.isPresent()){
            moderatorUser=user2.get();
        }
        List<String> requests = moderatorUser.getRequests();
        if (requests == null) {
            requests = new ArrayList<>();
        }
        requests.add(businessId);
        moderatorUser.setRequests(requests);
        System.out.println(moderatorUser);
//        addUsers(user);
        userRepository.save(moderatorUser);
        isValid.setIsvalid(true);
        return isValid;
    }
}
