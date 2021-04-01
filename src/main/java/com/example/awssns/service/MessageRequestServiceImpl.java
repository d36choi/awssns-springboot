package com.example.awssns.service;

import com.example.awssns.entity.MessageRequest;
import com.example.awssns.repository.MessageRequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class MessageRequestServiceImpl implements MessageRequestService {

    private final MessageRequestRepository messageRequestRepository;
    private final CredentialService credentialService;
    private final SnsRequestFactoryService snsRequestFactoryService;

    public MessageRequestServiceImpl(MessageRequestRepository messageRequestRepository, CredentialService credentialService, SnsRequestFactoryService snsRequestFactoryService) {
        this.messageRequestRepository = messageRequestRepository;
        this.credentialService = credentialService;
        this.snsRequestFactoryService = snsRequestFactoryService;
    }

    @Override
    public void addRequestToDocument(PublishRequest request, Map<String, String> message) {

        messageRequestRepository.insert(MessageRequest.builder()
                .topicArn(request.topicArn())
                .targetArn(request.targetArn())
                .message(message)
                .subject(request.subject())
                .build());
    }

    @Override
    public MessageRequest getMessageRequest(String id) {
        Optional<MessageRequest> optionalMessageRequest = messageRequestRepository.findById(id);
        return optionalMessageRequest.orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void removeMessageRequest(String id) {
        messageRequestRepository.deleteById(id);
    }

    @Override
    public List<MessageRequest> getAllMessageRequests() {
        return messageRequestRepository.findAll();
    }

    @Override
    public ResponseEntity<String> publishMessage(String topicArn, Map<String, String> message) throws JsonProcessingException, SnsException {
        try (SnsClient snsClient = credentialService.getSnsClient()) {
            final PublishRequest publishRequest = snsRequestFactoryService.getPublishRequest(topicArn, message);
            PublishResponse publishResponse = snsClient.publish(publishRequest);
            addRequestToDocument(publishRequest, message);
            return new ResponseEntity<>(publishResponse.messageId(), HttpStatus.OK);
        }
    }


}
