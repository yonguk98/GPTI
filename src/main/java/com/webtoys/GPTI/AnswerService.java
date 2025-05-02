package com.webtoys.GPTI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${secret.openai.api-key}")
    private String openAIApiKey;

    @Value(value = "${secret.gpt.role}")
    String gptRole;

    public String makeAnswer(UserAnswerDto userAnswerDto) {
        List<Map<String ,String>> questionAndAnswerList =  userAnswerDto.getQuestionsAndAnswers();

        String finalQuestion = makeQuestionForGPT(questionAndAnswerList);

        String requestBody = makeRequestBodyStringToJson(finalQuestion);

        String responseBody = sendQuestionToGPT(requestBody);

        return parseGPTResponse(responseBody);
    }

    private String makeQuestionForGPT(List<Map<String ,String>> questionAndAnswerList) {

        StringBuilder sb = new StringBuilder();

        for (Map<String ,String> questionAndAnswer: questionAndAnswerList) {
            String question = questionAndAnswer.get("question");
            String answer = questionAndAnswer.get("answer");

            sb.append(question).append(" : ").append(answer).append("  ");

        }

        return sb.toString();
    }

    // httpClient 라이브러리를 이용해 openAi로 gpt 호출하고 응답 받아오기
    private String sendQuestionToGPT(String requestBody)  {

        // 클라이언트 기본값으로 생성
        HttpClient client = HttpClient.newHttpClient();

        // 요청보낼 http request에 대한 target uri, header, method 설정 및 데이터 추가
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("Authorization",String.format("Bearer %s", openAIApiKey))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            return response.body();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private String makeRequestBodyStringToJson(String question) {


        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> message1 = Map.of(
                "role", "developer",
                "content", gptRole
        );

        Map<String, Object> message2 = Map.of(
                "role", "user",
                "content", question
        );

        Map<String, Object> payload = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(message1, message2)
        );

        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseGPTResponse(String responseBody)  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode contentNode = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content");

            return contentNode.asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
