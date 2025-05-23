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

//    @GetMapping("/mbti/questions")
//    public List<QuestionResponseDto> questions() {
//
//        return questionService.makeRandomQuestionResponseDtoList();
//    }

    @GetMapping("/mbti/questions")
    public List<String> questionsString() {

        return questionService.makeRandomQuestionStringList();
    }

    @PostMapping("/mbti/answers")
    public String answers(@RequestBody UserAnswerDto userAnswerDto) {

        return answerService.makeAnswer(userAnswerDto);
    }

}
