package com.example.searchUsersNew.service.impl;

import com.example.searchUsersNew.controller.SearchUserController;
import com.example.searchUsersNew.dto.UserDTO;
import com.example.searchUsersNew.feignClient.FeignUserService;
import com.example.searchUsersNew.repository.SolrRepository;
import com.example.searchUsersNew.service.SolrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SolrServiceImpl implements SolrService {

    @Autowired
    SolrRepository solrRepository;

    @Autowired
    FeignUserService feignUserService;

    public SearchUserController updateSolrData(String userId, String displayPicture){
        Optional<SearchUserController> solrUser =solrRepository.findById(userId);
        SearchUserController solrUser1=new SearchUserController();
        if(solrUser.isPresent()){
            solrUser1=solrUser.get();
        }

        ResponseEntity<UserDTO> responseEntity=feignUserService.getUsersByUserId(userId);
        ObjectMapper objectMapper=new ObjectMapper();
        UserDTO userDTO = objectMapper.convertValue(responseEntity.getBody(), UserDTO.class);

        UserDTO userDTO1=new UserDTO();
        userDTO1.setDisplayPicture(userDTO.getDisplayPicture());
        solrUser1.addUserDetails(userDTO1);

        return solrUser1;
    }
}
