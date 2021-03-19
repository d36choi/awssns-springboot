package com.example.awssns.controller;

import com.example.awssns.configuration.AWSConfig;
import com.example.awssns.entity.PublishMessageRequest;
import com.example.awssns.service.CredentialService;
import com.example.awssns.service.MongodbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.Map;

@Slf4j
@RequestMapping("/message")
@RestController
public class MessageController {
    AWSConfig awsConfig;
    CredentialService credentialService;
    MongodbService mongodbService;

    public MessageController(AWSConfig awsConfig, CredentialService credentialService, MongodbService mongodbService) {
        this.awsConfig = awsConfig;
        this.credentialService = credentialService;
        this.mongodbService = mongodbService;
    }

    @PostMapping("/publish")
    public String publish(@RequestParam String topicArn, @RequestBody Map<String, Object> message) {
        SnsClient snsClient = credentialService.getSnsClient();
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(message.toString())
                .build();
        PublishResponse publishResponse = null;
        try {
            publishResponse = snsClient.publish(publishRequest);
        } catch (Exception e) {
            return e.getMessage().trim();
        }

        log.info("message status:" + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();

        mongodbService.insert(
                PublishMessageRequest.of(publishRequest)
        );

        return "sent MSG ID = " + publishResponse.messageId();
    }
}
