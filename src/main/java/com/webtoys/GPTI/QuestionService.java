package com.webtoys.GPTI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**

 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {
    private QuestionRepository questionRepository;

    // 질문 가져오기
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    // public List<QuestionResponseDto> getRandomQuestions(){
        
        
        
    // }

    // 질문 등록하기
    public void addQuestion(String string, String stringType){
        questionRepository.save(
                Question.builder()
                        .question(questionStringCheck(string))
                        .questionType(stringTypeToInteger(stringType))
                        .build()
        );
    }

    private String questionStringCheck(String string){
        if (string.isBlank()){
            log.error("question sentence is blank");
            throw new IllegalArgumentException("question sentence is blank");
        }
        return string;
    }

    private Integer stringTypeToInteger(String stringType){
        if(stringType.equalsIgnoreCase("I") || stringType.equalsIgnoreCase("E")){
            return 1;
        } else if(stringType.equalsIgnoreCase("S") || stringType.equalsIgnoreCase("N")){
            return 2;
        } else if(stringType.equalsIgnoreCase("T") || stringType.equalsIgnoreCase("F")){
            return 3;
        } else if(stringType.equalsIgnoreCase("J") || stringType.equalsIgnoreCase("P")){
            return 4;
        } else {
            return 0;
        }
    }


}
