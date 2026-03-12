package com.vyaparimitra.vyapari_mitra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "व्यापारी मित्र API कार्यरत आहे! 🚀";
    }

    @GetMapping("/marathi")
    public String marathi() {
        return "तुमचे स्वागत आहे! उधारी व्यवस्थापन अॅप सुरू झाले.";
    }
}