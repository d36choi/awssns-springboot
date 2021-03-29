package com.example.awssns.controller;

import com.example.awssns.configuration.AWSConfig;
import com.example.awssns.service.CredentialService;
import com.example.awssns.service.MessageRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    MessageRequestService messageRequestService;

    public MessageController(AWSConfig awsConfig, CredentialService credentialService, MessageRequestService messageRequestService) {
        this.awsConfig = awsConfig;
        this.credentialService = credentialService;
        this.messageRequestService = messageRequestService;
    }

    @ApiOperation(value = "publish message", notes = "")
    @PostMapping("/publish")
    public String publish(@ApiParam(value = "where to send message") @RequestParam String topicArn,
                          @RequestBody Map<String, String> message) throws JsonProcessingException {
        //TODO: 발행 시간 추가
        SnsClient snsClient = credentialService.getSnsClient();
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(message.toString())
                .build();
        PublishResponse publishResponse = null;
        // 지양
        try {
            publishResponse = snsClient.publish(publishRequest);
        } catch (Exception e) {
            return e.getMessage().trim();
        }

        log.info("message status:" + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();

        messageRequestService.insert(publishRequest,message);


        return "sent MSG ID = " + publishResponse.messageId();
    }
}
