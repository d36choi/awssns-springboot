package com.example.awssns.service;

import com.example.awssns.entity.MessageRequest;
import com.example.awssns.repository.MessageRequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageRequestServiceImpl implements MessageRequestService {

    MessageRequestRepository messageRequestRepository;
    ObjectMapper mapper;
    CredentialService credentialService;

    @Override
    public void insert(PublishRequest request, Map<String, String> message) {

        messageRequestRepository.insert(MessageRequest.builder()
                .topicArn(request.topicArn())
                .targetArn(request.targetArn())
                .message(message)
                .subject(request.subject())
                .build());
    }

    @Override
    public Optional<MessageRequest> findById(String id) {
        return messageRequestRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        messageRequestRepository.deleteById(id);
    }

    @Override
    public List<MessageRequest> findAll() {
        return messageRequestRepository.findAll();
    }

    @Override
    public ResponseEntity<String> publishMessage(String topicArn, Map<String, String> message) throws JsonProcessingException {
        SnsClient snsClient = credentialService.getSnsClient();
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("11st CX team")
                .message(mapper.writeValueAsString(message))
                .build();

        PublishResponse publishResponse = snsClient.publish(publishRequest);
        snsClient.close();

        insert(publishRequest,message);

        return new ResponseEntity<>(publishResponse.messageId(), HttpStatus.OK);
    }
}
