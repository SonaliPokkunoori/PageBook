package com.example.Posts.services.impl;

import com.example.Posts.dto.*;
import com.example.Posts.entity.Post;
import com.example.Posts.feignclients.FeignUserService;
import com.example.Posts.repository.PostRepository;
import com.example.Posts.services.PostService;
import com.example.Posts.services.ReactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    FeignUserService feignUserService;

    @Autowired
    ReactionService reactionService;

    @Override
    public Post addNewPost (Post newPost) {
        return postRepository.insert(newPost);
    }

    @Override
    public List<AndroidPostDTO> getAllPosts(String userId) {
        List<Post> posts=postRepository.findAllByUserId(userId);
        List<AndroidPostDTO> androidPostDTOList=new ArrayList<>();


        ResponseEntity<UserDTO> responseEntity=feignUserService.getUsersByUserId(userId);
        ObjectMapper objectMapper=new ObjectMapper();
        UserDTO userDTO = objectMapper.convertValue(responseEntity.getBody(), UserDTO.class);

        for(int i=0;i<posts.size();i++){
            AndroidPostDTO androidPostDTO=new AndroidPostDTO();
            androidPostDTO.setPost(posts.get(i));
            androidPostDTO.setReacitons(reactionService.getReactionCountForPost(posts.get(i).getPostId()));
            androidPostDTOList.add(androidPostDTO);
        }
        return androidPostDTOList;
    }

    @Override
    public AndroidPostDTO getPostByPostId(String postId) {

        Optional<Post> postCheck=postRepository.findById(postId);

        if(postCheck.isPresent()){
            Post post=postCheck.get();
            AndroidPostDTO androidPostDTO=new AndroidPostDTO();

            ResponseEntity<UserDTO> responseEntity=feignUserService.getUsersByUserId(post.getUserId());
            ObjectMapper objectMapper=new ObjectMapper();
            UserDTO userDTO = objectMapper.convertValue(responseEntity.getBody(), UserDTO.class);

            androidPostDTO.setPost(post);
            androidPostDTO.setReacitons(reactionService.getReactionCountForPost(post.getPostId()));
            androidPostDTO.setDisplayPicture(userDTO.getDisplayPicture());
            //BeanUtils.copyProperties(post,androidPostDTO);
            return androidPostDTO;
        }
        return null;
    }

    @Override
    public boolean deletePostOfUser(String postId, String userId) {
        Optional<Post> postCheck=postRepository.findById(postId);
        if(postCheck.isPresent()){
            Post post=postCheck.get();
            if(post.getUserId().equals(userId)){
                postRepository.delete(post);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<AndroidPostDTO> getAllFriendsPosts(String userId) {
        System.out.println("inside getAllFriendsPost");
        ResponseEntity<FriendsDTO> response= feignUserService.getListOfFriends(userId);
        ObjectMapper mapper=new ObjectMapper();
        UserDTO userDTO=new UserDTO();
        FriendsDTO friendsDTO = mapper.convertValue(response.getBody(), FriendsDTO.class);
        List<SimpleUsersDTO> friends=friendsDTO.getFriends();
        List<Post> returningPosts=new ArrayList<Post>();
        List<AndroidPostDTO> androidPostDTOList=new ArrayList<AndroidPostDTO>();
        for (int i=0;i<friends.size();i++){
            System.out.println(friends.get(i).getUserName());
            List<Post> posts=postRepository.findAllByUserId(friends.get(i).getUserId());
            returningPosts.addAll(posts);

            ResponseEntity<UserDTO> responseEntity=feignUserService.getUsersByUserId(friends.get(i).getUserId());
            ObjectMapper objectMapper=new ObjectMapper();
            userDTO = objectMapper.convertValue(responseEntity.getBody(), UserDTO.class);
        }

        System.out.println(userDTO);

        for(int i=0;i<returningPosts.size();i++){

            AndroidPostDTO androidPostDTO=new AndroidPostDTO();
            androidPostDTO.setPost(returningPosts.get(i));
            androidPostDTO.setReacitons(reactionService.getReactionCountForPost(returningPosts.get(i).getPostId()));
            androidPostDTO.setDisplayPicture(userDTO.getDisplayPicture());
//            androidPostDTO.setFriendDisplayPicture(userDTO.getDisplayPicture());
            androidPostDTOList.add(i,androidPostDTO);

        }
        System.out.println(androidPostDTOList);
        return androidPostDTOList;
    }

    @Override
    public ValidationStatus getOthersPersonalPosts(String loggedInUserId, String otherUserId) {
        UserDTO userDTO=feignUserService.getUsersByUserId(otherUserId).getBody();
        ValidationStatus validationStatus=new ValidationStatus();
        if(Boolean.FALSE.equals(userDTO.getIsPrivate())){
            validationStatus.setIsvalid(true);
            return validationStatus;
        }
        ResponseEntity<FriendsDTO> response= feignUserService.getListOfFriends(loggedInUserId);
        ObjectMapper mapper=new ObjectMapper();
        FriendsDTO friendsDTO = mapper.convertValue(response.getBody(), FriendsDTO.class);
        List<SimpleUsersDTO> friends=friendsDTO.getFriends();
        for(int i=0;i<friends.size();i++){
            if(friends.get(i).getUserId().equals(otherUserId)){
                validationStatus.setIsvalid(true);
                return validationStatus;
            }
        }
        validationStatus.setIsvalid(false);
        return validationStatus;
    }

}
