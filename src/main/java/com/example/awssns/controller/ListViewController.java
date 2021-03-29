package com.example.awssns.controller;


import com.example.awssns.entity.MessageRequest;
import com.example.awssns.service.MessageRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequestMapping("/list")
@Controller
public class ListViewController {

    MessageRequestService messageRequestService;

    public ListViewController(MessageRequestService messageRequestService) {
        this.messageRequestService = messageRequestService;
    }

    @GetMapping("")
    public String listTest(Model model) {
        List<MessageRequest> requests = messageRequestService.findAll();
        model.addAttribute("requests",requests);
        return "messageList";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("id") String id) {
        messageRequestService.deleteById(id);
        List<MessageRequest> requests = messageRequestService.findAll();
        model.addAttribute("requests",requests);
        return "messageList";
    }
}
