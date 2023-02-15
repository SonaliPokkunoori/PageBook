package com.example.Users.services;

import com.example.Users.dto.*;
import com.example.Users.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public LoginStatus login(Credentials credentials);

    public User addUsers(UserDTO userDTO);

    public Optional<User> getUsersByUserId(String userId);

    public Optional getUsersByUserName(String userName);

    public ValidationStatus sendFriendRequest(String userId, String friendUserId);

    public ValidationStatus deleteUserByUserId(String userId);

    public ValidationStatus deleteFriendRequest(String userId, String friendUserId);

    public List<SimpleUserDTO> getAllFriendRequests(String userId);
//public RequestDTO getAllFriendRequests(String userId);

    public ValidationStatus acceptFriendRequest(String userId, String friendUserId);

    public FriendsDTO getListOfFriends(String userId);

    public ValidationStatus addOrUpdateBio(String userId,String bio);

    public ValidationStatus addOrUpdateDisplayPicture(DisplayPictureDTO displayPictureDTO);

    public ValidationStatus deleteFriend(String userId,String friendUserId);

    public ValidationStatus isFriend(String userId,String friendUserId);
}
