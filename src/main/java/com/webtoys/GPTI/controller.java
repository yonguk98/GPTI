package com.webtoys.GPTI;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class controller {

    private final AnswerService answerService;

    @GetMapping
    public String basic() {
        return "Hello World";
    }

    @GetMapping("/mbti/questions")
    public String questions() {
        
        return "Hello World";
    }

    @PostMapping("/mbti/answers")
    public String answers(@RequestBody UserAnswerDto userAnswerDto) throws JsonProcessingException {

        answerService.makeAnswer(userAnswerDto);

        return "Hello World";
    }

}
