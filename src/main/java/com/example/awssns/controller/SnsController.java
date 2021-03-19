package com.example.awssns.controller;

import com.example.awssns.configuration.AWSConfig;
import com.example.awssns.pojo.SubscriptionData;
import com.example.awssns.service.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;
import java.util.Map;

@Slf4j
@RestController
public class SnsController {

    AWSConfig awsConfig;
    CredentialService credentialService;

    public SnsController(AWSConfig awsConfig, CredentialService credentialService) {
        this.awsConfig = awsConfig;
        this.credentialService = credentialService;
    }

    @PostMapping("/createTopic")
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


    @PostMapping("/publish")
    public String publish(@RequestParam String topicArn, @RequestBody Map<String, Object> message) {
        SnsClient snsClient = credentialService.getSnsClient();
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(message.toString())
                .build();
        PublishResponse publishResponse = snsClient.publish(publishRequest);
        log.info("message status:" + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();

        return "sent MSG ID = " + publishResponse.messageId();

    }

    private ResponseStatusException getResponseStatusException(SnsResponse response) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, response.sdkHttpResponse().statusText().get()
        );
    }

}