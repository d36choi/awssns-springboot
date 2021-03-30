package com.example.awssns.controller;


import com.example.awssns.entity.MessageRequest;
import com.example.awssns.service.MessageRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j @RequiredArgsConstructor
@RequestMapping("/message")
@Controller
public class MessageListController {

    private final MessageRequestService messageRequestService;

    @GetMapping("/list")
    public String messageList(Model model) {
        model.addAttribute("requests",messageRequestService.findAll());
        return "messageList";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("id") String id) {
        messageRequestService.deleteById(id);
        model.addAttribute("requests",messageRequestService.findAll());
        return "messageList";
    }
}
