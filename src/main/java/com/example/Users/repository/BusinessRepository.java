package com.example.Users.repository;

import com.example.Users.entities.Business;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends MongoRepository<Business,String> {

    public Optional<Business> findByBusinessName(String businessName);
}
