package com.example.awssns.controller;


import com.example.awssns.entity.PublishMessageRequest;
import com.example.awssns.service.MongodbService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/list")
@Controller
public class ListViewController {

    MongodbService mongodbService;

    public ListViewController(MongodbService mongodbService) {
        this.mongodbService = mongodbService;
    }

    @GetMapping()
    public String list(Model model) {
        List<PublishMessageRequest> allMessages = mongodbService.getAllMessages();
        model.addAttribute("messages",allMessages);
        return "messageList";
    }

    @GetMapping("/delete")
    public String deleteOne(Model model, @RequestParam("id") String id) {
        mongodbService.deleteById(id);
        List<PublishMessageRequest> allMessages = mongodbService.getAllMessages();
        model.addAttribute("messages",allMessages);
        return "messageList";
    }
}
