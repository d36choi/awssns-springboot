package com.example.awssns.controller;


import com.example.awssns.service.MessageRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j @RequiredArgsConstructor
@RequestMapping("/message")
@RestController
public class MessageController {

    private final MessageRequestService messageRequestService;

    @ApiOperation(value = "publish message", notes = "")
    @PostMapping("/publish")
    public ResponseEntity<String> publish(@ApiParam(value = "where to send message") @RequestParam String topicArn,
                          @RequestBody Map<String, String> message) throws JsonProcessingException {

        return messageRequestService.publishMessage(topicArn,message);
    }
}
