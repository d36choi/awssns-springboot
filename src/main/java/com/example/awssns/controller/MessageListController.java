package com.example.awssns.controller;


import com.example.awssns.service.MessageRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RequestMapping("/message")
@Controller
public class MessageListController {

    private final MessageRequestService messageRequestService;

    public MessageListController(MessageRequestService messageRequestService) {
        this.messageRequestService = messageRequestService;
    }

    @GetMapping("/list")
    public String messageList(Model model) {
        model.addAttribute("requests",messageRequestService.getAllMessageRequests());
        return "messageList";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("id") String id) {
        messageRequestService.removeMessageRequest(id);
        model.addAttribute("requests",messageRequestService.getAllMessageRequests());
        return "messageList";
    }
}
