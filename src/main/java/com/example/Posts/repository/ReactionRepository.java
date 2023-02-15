package com.example.Posts.repository;

import com.example.Posts.entity.Reactions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReactionRepository extends MongoRepository<Reactions,String> {
}
