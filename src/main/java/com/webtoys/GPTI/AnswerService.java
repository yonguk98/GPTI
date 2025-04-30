package com.webtoys.GPTI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
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

    @Value("${spring.openai.api-key}")
    private static String openAIApiKey;

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

    // httpClient 라이브러리를 이용해 openAi로 gpt 호출하고 응답 받아오기
    private void sendQuestionToGPT(String question) {

        // 클라이언트 기본값으로 생성
        HttpClient client = HttpClient.newHttpClient();

        // 요청보낼 http request에 대한 target uri, header, method 설정 및 데이터 추가
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer " + openAIApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(String.format("""
                        {
                            "model" : "gpt-4o-mini",
                            "messages" : [
                                {
                                    "role": "user",
                                    "content": "%s"
                                }
                            ]
                        }""", question)))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
