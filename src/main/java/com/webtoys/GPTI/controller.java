package com.webtoys.GPTI;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class controller {

    @GetMapping
    public String basic() {
        return "Hello World";
    }

    @GetMapping("/mbti/questions")
    public String questions() {
        
        return "Hello World";
    }

}
