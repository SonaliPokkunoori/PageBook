package com.example.Posts.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.Posts.entity.SharePost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharedRepository extends MongoRepository<SharePost, String > {

    //List<SharePost> findAllByReceiverUserId (String receiverUserId);
}
