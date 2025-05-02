package com.webtoys.GPTI;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class controller {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping
    public String basic() {
        return "Hello World";
    }

    @GetMapping("/mbti/questions")
    public List<QuestionResponseDto> questions() {

        return questionService.makeRandomQuestionList();
    }

    @PostMapping("/mbti/answers")
    public String answers(@RequestBody UserAnswerDto userAnswerDto) throws JsonProcessingException {

        answerService.makeAnswer(userAnswerDto);

        return "Hello World";
    }

}
