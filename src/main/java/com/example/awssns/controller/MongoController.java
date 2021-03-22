package com.example.awssns.controller;

import com.example.awssns.entity.MessageRequest;
import com.example.awssns.service.MessageRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/db")
@RestController
public class MongoController {

    MessageRequestService messageRequestService;

    public MongoController(MessageRequestService messageRequestService) {
        this.messageRequestService = messageRequestService;
    }

    @ResponseBody
    @GetMapping("/findAll")
    public ResponseEntity<List<MessageRequest>> findAll() {
        List<MessageRequest> messages = messageRequestService.findAll();
        return new ResponseEntity<>(messages,HttpStatus.OK);
    }
}
