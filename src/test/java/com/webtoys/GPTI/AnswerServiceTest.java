package com.webtoys.GPTI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AnswerServiceTest {

    @Value(value = "${secret.openai.api-key}")
    String openAIApiKey;

    @Value(value = "${secret.gpt.role}")
    String gptRole;

    @MockitoBean
    AnswerService answerService;

    @Test
    @DisplayName("질문만들기테스트")
    void makeAnswer() {
    }

    @Test
    void makeQuestionToJsonTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String body = """
                "messages": [
                      {
                        "role": "developer",
                        "content": "You are a helpful assistant."
                      },
                      {
                        "role": "user",
                        "content": "Hello!"
                      }
                    ]
                """;

//        record Message(String role, String content) {}
//        record Request(String model, List<Message> messages) {}
//
//        Request request = new Request("gpt-4o-mini", List.of(new Message("user", "Hello!")));
//        String json = mapper.writeValueAsString(request);


        System.out.println(mapper.writeValueAsString(body));
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

    @Test
    void sendQuestionToGPTTest(){
        ObjectMapper objectMapper = new ObjectMapper();

        // 클라이언트 기본값으로 생성
        HttpClient client = HttpClient.newHttpClient();

        // 요청보낼 http request에 대한 target uri, header, method 설정 및 데이터 추가
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("Authorization",String.format("Bearer %s", openAIApiKey))
                .POST(HttpRequest.BodyPublishers.ofString(makeRequestBodyStringToJson())
                )
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            e.getMessage();
        }
    }
}