package com.example.awssns.service;

import com.example.awssns.entity.MessageRequest;
import com.example.awssns.repository.MessageRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageRequestService {

    MessageRequestRepository messageRequestRepository;
    ObjectMapper mapper;
    public MessageRequestService(MessageRequestRepository messageRequestRepository,ObjectMapper mapper) {
        this.messageRequestRepository = messageRequestRepository;
        this.mapper = mapper;
    }

    public void insert(PublishRequest request,Map<String, String> message) {

        messageRequestRepository.insert(MessageRequest.builder()
                .topicArn(request.topicArn())
                .targetArn(request.targetArn())
                .message(message)
                .subject(request.subject())
                .build());
    }

    public Optional<MessageRequest> findById(String id) {
        return messageRequestRepository.findById(id);
    }

    public void deleteById(String id) {
        messageRequestRepository.deleteById(id);
    }

    public List<MessageRequest> findAll() {
        return messageRequestRepository.findAll();
    }

}
