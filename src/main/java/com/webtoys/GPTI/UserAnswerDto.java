package com.webtoys.GPTI;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class UserAnswerDto {

    private List<Map<String, String>> questionsAndAnswers;

}
