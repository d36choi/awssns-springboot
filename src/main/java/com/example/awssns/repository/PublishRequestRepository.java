package com.example.awssns.repository;

import com.example.awssns.entity.PublishMessageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublishRequestRepository extends MongoRepository <PublishMessageRequest, String> {

}
