package com.example.awssns.service;

import com.example.awssns.pojo.SubscriptionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Slf4j
@Service
public class TopicRequestServiceImpl implements TopicRequestService {

    CredentialService credentialService;
    SnsRequestFactoryService snsRequestFactoryService;

    public TopicRequestServiceImpl(CredentialService credentialService, SnsRequestFactoryService snsRequestFactoryService) {
        this.credentialService = credentialService;
        this.snsRequestFactoryService = snsRequestFactoryService;
    }

    @Override
    public ResponseEntity<String> createTopic(String topicName) {
        final CreateTopicRequest createTopicRequest = snsRequestFactoryService.getCreateTopicRequest(topicName);
        SnsClient snsClient = credentialService.getSnsClient();
        final CreateTopicResponse response = snsClient.createTopic(createTopicRequest);

        validate(snsClient, response);
        return new ResponseEntity<>(String.format("Create %s topic", response.topicArn()), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> deleteTopic(String topicArn) {
        final DeleteTopicRequest deleteTopicRequest = snsRequestFactoryService.getDeleteTopicRequest(topicArn);
        SnsClient snsClient = credentialService.getSnsClient();
        final DeleteTopicResponse response = snsClient.deleteTopic(deleteTopicRequest);

        validate(snsClient, response);
        return new ResponseEntity<>(String.format("%s removed.", topicArn), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> subscribe(SubscriptionData payload) {
        final SubscribeRequest subscribeRequest = snsRequestFactoryService.getSubscribeRequest(payload);
        SnsClient snsClient = credentialService.getSnsClient();
        final SubscribeResponse response = snsClient.subscribe(subscribeRequest);

        validate(snsClient, response);
        return new ResponseEntity<>(String.format("subscription to %s", response.subscriptionArn()), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> unsubscribe(String subscriptionArn) {
        final UnsubscribeRequest request = snsRequestFactoryService.getUnsubscribeRequest(subscriptionArn);

        SnsClient snsClient = credentialService.getSnsClient();
        final UnsubscribeResponse response = snsClient.unsubscribe(request);

        validate(snsClient, response);
        return new ResponseEntity<>("unsubscription success.", HttpStatus.OK);
    }

    private void validate(SnsClient snsClient, SnsResponse response) {
        if (!response.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(response);
        }
        snsClient.close();
    }

    private ResponseStatusException getResponseStatusException(SnsResponse response) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, response.sdkHttpResponse().statusText().get()
        );
    }

}
