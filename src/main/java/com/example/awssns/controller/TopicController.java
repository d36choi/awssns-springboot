package com.example.awssns.controller;

import com.example.awssns.configuration.AWSConfig;
import com.example.awssns.pojo.SubscriptionData;
import com.example.awssns.service.CredentialService;
import com.example.awssns.service.MongodbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Slf4j
@RequestMapping("/topic")
@RestController
public class TopicController {

    AWSConfig awsConfig;
    CredentialService credentialService;
    MongodbService mongodbService;

    public TopicController(AWSConfig awsConfig, CredentialService credentialService, MongodbService mongodbService) {
        this.awsConfig = awsConfig;
        this.credentialService = credentialService;
        this.mongodbService = mongodbService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTopic(@RequestParam final String topicName) {
        final CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();
        SnsClient snsClient = credentialService.getSnsClient();
        final CreateTopicResponse createTopicResponse = snsClient.createTopic(createTopicRequest);

        if (!createTopicResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(createTopicResponse);
        }
        log.info("topic name = " + createTopicResponse.topicArn());
        log.info("topic list = " + snsClient.listTopics());
        snsClient.close();
        return new ResponseEntity<>(createTopicResponse.topicArn(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam final String topicArn) {
        final DeleteTopicRequest deleteTopicRequest = DeleteTopicRequest.builder()
                .topicArn(topicArn)
                .build();
        SnsClient snsClient = credentialService.getSnsClient();
        final DeleteTopicResponse response = snsClient.deleteTopic(deleteTopicRequest);

        if (!response.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(response);
        }
        snsClient.close();
        return new ResponseEntity<>(String.format("%s removed.",topicArn), HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody SubscriptionData subscriptionData) {
        final SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol(subscriptionData.getProtocol())
                .topicArn(subscriptionData.getTopicArn())
                .endpoint(subscriptionData.getEndpoint())
                .build();
        SnsClient snsClient = credentialService.getSnsClient();
        final SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);

        if (!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(subscribeResponse);
        }
        log.info("topicARN to subscribe = " + subscribeResponse.subscriptionArn());
        log.info("subscription list = " + snsClient.listSubscriptions());
        snsClient.close();
        return new ResponseEntity<>(subscribeResponse.subscriptionArn(), HttpStatus.OK);
    }

    private ResponseStatusException getResponseStatusException(SnsResponse response) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, response.sdkHttpResponse().statusText().get()
        );
    }

}
