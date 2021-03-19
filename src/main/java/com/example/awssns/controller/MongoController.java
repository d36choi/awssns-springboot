package com.example.awssns.controller;

import com.example.awssns.entity.PublishMessageRequest;
import com.example.awssns.service.MongodbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/db")
@RestController
public class MongoController {

    MongodbService mongodbService;

    public MongoController(MongodbService mongodbService) {
        this.mongodbService = mongodbService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<PublishMessageRequest>> findAll() {
        List<PublishMessageRequest> messages = mongodbService.getAllMessages();
        return new ResponseEntity<>(messages,HttpStatus.OK);
    }
}
