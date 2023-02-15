package com.example.Users.services.impl;

import com.example.Users.dto.*;
import com.example.Users.entities.User;
import com.example.Users.feignClients.FeignSearchService;
import com.example.Users.repository.UserRepository;
import com.example.Users.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeignSearchService feignSearchService;

    @Override
    public LoginStatus login(Credentials credentials) {
        Optional<User> userCheck = userRepository.findByUserName(credentials.getUserName());
        LoginStatus loginStatus = new LoginStatus();
        if (userCheck.isPresent()) {
            User user = userCheck.get();
            if (user.getPassword().equals(credentials.getPassword())) {
                loginStatus.setIsvalid(true);
                loginStatus.setUserId(user.getUserId());
                return loginStatus;
            }
        }
        loginStatus.setIsvalid(false);
        return loginStatus;
    }

    @Override
    public User addUsers(UserDTO userDTO) {
        System.out.println(userDTO);
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUsersByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUsersByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public ValidationStatus sendFriendRequest(String userId, String friendUserId) {
        ValidationStatus isValid = new ValidationStatus();
        if(friendUserId.equals(userId)){
            isValid.setIsvalid(false);
            return isValid;
        }
        Optional<User> friendUser = getUsersByUserId(friendUserId);
        System.out.println(friendUser);
        List<String> requests = friendUser.get().getRequests();
//        requests.add(userId);
        if (requests == null) {
            requests = new ArrayList<>();
        }
        requests.add(userId);
        friendUser.get().setRequests(requests);
        System.out.println(friendUser);
        User user = new User();
        if (friendUser.isPresent() == true) {
            user = friendUser.get();
        }
        System.out.println(user);
//        addUsers(user);
        userRepository.save(user);
        isValid.setIsvalid(true);
        return isValid;
    }

    public ValidationStatus deleteUserByUserId(String userId) {
        ValidationStatus isValid = new ValidationStatus();
        userRepository.deleteById(userId);
        isValid.setIsvalid(true);
        return isValid;
    }

    public ValidationStatus deleteFriendRequest(String userId, String friendUserId) {
        ValidationStatus isValid = new ValidationStatus();
        Optional<User> user = getUsersByUserId(userId);
        List<String> requests = user.get().getRequests();
        requests.remove(friendUserId);
        User user1 = new User();
        if (user.isPresent()) {
            user1 = user.get();
        }
        System.out.println(requests);
        System.out.println(user);
        userRepository.save(user1);
        isValid.setIsvalid(true);
        return isValid;
    }

    @Override
    public ValidationStatus deleteFriend(String userId, String friendUserId) {
        ValidationStatus isValid=new ValidationStatus();
        Optional<User> user=getUsersByUserId(userId);
        Optional<User> friendUser=getUsersByUserId(friendUserId);
        List<String> friends=user.get().getFriends();
        List<String> friends1=friendUser.get().getFriends();
        System.out.println(friends);
        Iterator<String> userIterator=friends.iterator();
        Iterator<String> friendIterator=friends1.iterator();
        while(userIterator.hasNext()){

            String friend = userIterator.next();
            if(friend.equals(friendUserId)) {
                userIterator.remove();
            }
        }
        while(friendIterator.hasNext()){

            String friend = friendIterator.next();
            if(friend.equals(userId)) {
                friendIterator.remove();
            }
        }
        User user1 =new User();
        if(user.isPresent()){
            user1=user.get();
        }
        User user2 =new User();
        if(friendUser.isPresent()){
            user2=friendUser.get();
        }
        userRepository.save(user1);
        userRepository.save(user2);
        isValid.setIsvalid(true);
        return isValid;
    }

    @Override
    public ValidationStatus isFriend(String userId, String friendUserId) {
        FriendsDTO friendsDTO=getListOfFriends(userId);
        ValidationStatus isValid=new ValidationStatus();
        System.out.println(friendsDTO);
        List<SimpleUserDTO> friendsSimpleUserDTO=friendsDTO.getFriends();
        for (int i = 0; i < friendsSimpleUserDTO.size(); i++) {
         String friendUser= friendsSimpleUserDTO.get(i).getUserId();
         if(friendUser.equals(friendUserId)){
             isValid.setIsvalid(true);
             return isValid;
         }
        }
        isValid.setIsvalid(false);
        return isValid;
    }

    @Override
    public List<SimpleUserDTO> getAllFriendRequests(String userId) {
        Optional<User> userCheck = userRepository.findById(userId);
        User user1 = new User();
        List<SimpleUserDTO> simpleUserDTOList = new ArrayList<>();
        if (userCheck.isPresent()) {
            user1 = userCheck.get();
            for (int i = 0; i < user1.getRequests().size(); i++) {
                SimpleUserDTO simpleUserDTO = new SimpleUserDTO();
                Optional<User> userCheck1 = userRepository.findById(user1.getRequests().get(i));
                if (userCheck1.isPresent()) {
                    User user2 = userCheck1.get();
                    BeanUtils.copyProperties(user2, simpleUserDTO);
                    simpleUserDTOList.add(simpleUserDTO);
                }
            }
        }
        //System.out.println(user1.getRequests());
        return simpleUserDTOList;
    }


    public ValidationStatus acceptFriendRequest(String userId, String friendUserId) {
        Optional<User> friendUser = getUsersByUserId(friendUserId);
        Optional<User> user = getUsersByUserId(userId);
        User user1 = new User();
        User user2 = new User();
        if (friendUser.isPresent()) {
            user1 = friendUser.get();
        }
        if (user.isPresent()) {
            user2 = user.get();
        }

        List<String> requests1 = user2.getRequests();
        if (requests1 == null) {
            requests1 = new ArrayList<>();
        }
        List<String> friends1 = user1.getFriends();
        if (friends1 == null) {
            friends1 = new ArrayList<>();
        }

        List<String> friends2 = user2.getFriends();
        if (friends2 == null) {
            friends2 = new ArrayList<>();
        }

        friends1.add(userId);
        friends2.add(friendUserId);
        requests1.remove(friendUserId);
//        r2.remove(userId);

        System.out.println(friends1);
        System.out.println(friends2);
        System.out.println(requests1);
        user2.setRequests(requests1);
        user1.setFriends(friends1);
        user2.setFriends(friends2);
//        user2.setRequests(r2);
        userRepository.save(user1);
        userRepository.save(user2);

        ValidationStatus isValid = new ValidationStatus();
        isValid.setIsvalid(true);

        return isValid;
    }

    @Override
    public FriendsDTO getListOfFriends(String userId) {
        Optional<User> userCheck = userRepository.findById(userId);
        List<SimpleUserDTO> simpleUserDTOList = new ArrayList<>();
        FriendsDTO friendsDTO = new FriendsDTO();
        User user1 = new User();
        if (userCheck.isPresent()) {
            user1 = userCheck.get();
            if(user1.getFriends()==null){
                friendsDTO.setFriends(new ArrayList<>());
                return friendsDTO;
            }
            for (int i = 0; i < user1.getFriends().size(); i++) {
                SimpleUserDTO simpleUserDTO = new SimpleUserDTO();
                Optional<User> userCheck1 = userRepository.findById(user1.getFriends().get(i));
                if (userCheck1.isPresent()) {
                    User user2 = userCheck1.get();
                    BeanUtils.copyProperties(user2, simpleUserDTO);
                    simpleUserDTOList.add(simpleUserDTO);
                }
            }
        }
        //System.out.println(user1.getRequests());
        friendsDTO.setFriends(simpleUserDTOList);
        return friendsDTO;
    }

    @Override
    public ValidationStatus addOrUpdateBio(String userId, String bio) {
        Optional<User> user = userRepository.findById(userId);
        User user1 = new User();
        if (user.isPresent()) {
            user1 = user.get();
        }
        System.out.println(user1);
        user1.setBio(bio);
        userRepository.save(user1);
        ValidationStatus isValid = new ValidationStatus();
        isValid.setIsvalid(true);
        return isValid;
    }

    @Override
    public ValidationStatus addOrUpdateDisplayPicture(DisplayPictureDTO displayPictureDTO) {
        String userId=displayPictureDTO.getUserId();
        String displayPicture=displayPictureDTO.getDisplayPicture();
        Optional<User> user=userRepository.findById(userId);
        User user1=new User();
        if(user.isPresent()){
            user1=user.get();
        }
        user1.setDisplayPicture(displayPicture);
        userRepository.save(user1);
        ValidationStatus isValid=new ValidationStatus();
        isValid.setIsvalid(true);
        return isValid;
    }

}
