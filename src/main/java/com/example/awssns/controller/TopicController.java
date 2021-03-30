package com.example.awssns.controller;

import com.example.awssns.pojo.SubscriptionData;
import com.example.awssns.service.TopicRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/topic")
@RestController
public class TopicController {

    TopicRequestService topicRequestService;

    @PostMapping("/create")
    public ResponseEntity<String> createTopic(@RequestParam final String topicName) {
        return topicRequestService.createTopic(topicName);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam final String topicArn) {
        return topicRequestService.deleteTopic(topicArn);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody SubscriptionData payload) {
        return topicRequestService.subscribe(payload);
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String subscriptionArn) {
        return topicRequestService.unsubscribe(subscriptionArn);
    }


}
