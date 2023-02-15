package com.example.Posts.services.impl;

import com.example.Posts.dto.UserDTO;
import com.example.Posts.entity.SharePost;
import com.example.Posts.feignclients.FeignUserService;
import com.example.Posts.repository.SharedRepository;
import com.example.Posts.services.SharePostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SharePostServiceImpl implements SharePostService {

    @Autowired
    SharedRepository sharedRepository;

    @Autowired
    FeignUserService feignUserService;

    @Override
    public SharePost shareNewPost(SharePost sharePost) {
        return sharedRepository.save(sharePost);
    }

    @Override
    public List<SharePost> getAllSharedPostsOfUser(String userId) {
        List<SharePost> sharePostList = sharedRepository.findAll();
        List<SharePost> returningSharePost = new ArrayList<SharePost>();
        for (int i = 0; i < sharePostList.size(); i++) {
            if (sharePostList.get(i).getReceiverUserId().equals(userId) || sharePostList.get(i).getSenderUserId().equals(userId)) {
                ResponseEntity<UserDTO> senderResponseEntity = feignUserService.getUsersByUserId(sharePostList.get(i).getSenderUserId());
                ResponseEntity<UserDTO> recieverResponseEntity = feignUserService.getUsersByUserId(sharePostList.get(i).getReceiverUserId());
                ObjectMapper sendermapper = new ObjectMapper();
                UserDTO senderUser = sendermapper.convertValue(senderResponseEntity.getBody(), UserDTO.class);
                ObjectMapper recievermapper = new ObjectMapper();
                UserDTO reciverUser = recievermapper.convertValue(recieverResponseEntity.getBody(), UserDTO.class);
                sharePostList.get(i).setSenderUserName(senderUser.getUserName());
                sharePostList.get(i).setRecieverUserName(reciverUser.getUserName());
                returningSharePost.add(sharePostList.get(i));
            }
        }
        return returningSharePost;
    }


}
