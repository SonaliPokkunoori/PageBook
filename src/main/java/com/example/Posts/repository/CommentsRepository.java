package com.example.Posts.repository;

import com.example.Posts.entity.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentsRepository extends MongoRepository<Comments,String> {
}
