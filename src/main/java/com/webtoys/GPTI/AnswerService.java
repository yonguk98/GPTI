package com.webtoys.GPTI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {
    // 사용자 답변을 받아서
    // gpt로 전달할 프롬프트를 만들고
    // gpt로 전달
    // gpt로 부터 답변 받아서
    // 사용자에게 전달.

    public void makeAnswer(UserAnswerDto userAnswerDto) {
        List<Map<String ,String>> questionAndAnswerList =  userAnswerDto.getQuestionsAndAnswers();

        String finalQuestion = makeQuestionForGPT(questionAndAnswerList);

    }

    private String makeQuestionForGPT(List<Map<String ,String>> questionAndAnswerList) {

        StringBuilder sb = new StringBuilder();

        for (Map<String ,String> questionAndAnswer: questionAndAnswerList) {
            String question = questionAndAnswer.get("question");
            String answer = questionAndAnswer.get("answer");

            sb.append(question).append(" : ").append(answer).append("\n");

        }

        return sb.toString();
    }

    private void sendQuestionToGPT(){

    }


}
