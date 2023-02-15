package com.example.Stories.repository;

import com.example.Stories.entities.Stories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoriesRepository extends MongoRepository<Stories,String> {
    public void deleteByUserId(String userId);
    public List<Stories> findAllByUserId(String userId);
    public Optional<Stories> findByUserId(String userId);

}
