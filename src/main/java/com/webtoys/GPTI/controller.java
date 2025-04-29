package com.webtoys.GPTI;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/mbti/answers")
    public String answers(@RequestBody UserAnswerDto userAnswerDto) {

        System.out.println(userAnswerDto.getQuestionsAndAnswers().get(0));

        return "Hello World";
    }

}
