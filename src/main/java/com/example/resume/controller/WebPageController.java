package com.example.resume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Resume Generator");
        model.addAttribute("message", "This page was generated.");

        return "index";
    }
}
