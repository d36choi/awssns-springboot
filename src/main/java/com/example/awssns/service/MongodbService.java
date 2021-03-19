package com.example.awssns.service;

import com.example.awssns.entity.PublishMessageRequest;
import com.example.awssns.repository.PublishRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongodbService {

    PublishRequestRepository publishRequestRepository;

    public MongodbService(PublishRequestRepository publishRequestRepository) {
        this.publishRequestRepository = publishRequestRepository;
    }

    public void insert(PublishMessageRequest publishMessageRequest) {
        publishRequestRepository.insert(publishMessageRequest);

    }

    public void deleteById(String id) {
        publishRequestRepository.deleteById(id);
    }
    public List<PublishMessageRequest> getAllMessages() {
        return publishRequestRepository.findAll();
    }
}
