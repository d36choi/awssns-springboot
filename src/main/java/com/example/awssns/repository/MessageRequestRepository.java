package com.example.awssns.repository;

import com.example.awssns.entity.MessageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRequestRepository extends MongoRepository<MessageRequest,String> {

}
