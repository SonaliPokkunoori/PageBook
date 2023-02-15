package com.example.searchUsersNew.repository;

import com.example.searchUsersNew.controller.SearchUserController;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolrRepository extends SolrCrudRepository<SearchUserController,String> {

}
