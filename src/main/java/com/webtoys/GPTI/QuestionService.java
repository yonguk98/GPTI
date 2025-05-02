package com.webtoys.GPTI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


/**

 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 질문 가져오기
    private List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    private Optional<List<Question>> getQuestionsByType(Integer questionType) {
        return Optional.ofNullable(questionRepository.findAllByQuestionType(questionType))
                .filter(list -> !list.isEmpty());
    }

     public List<QuestionResponseDto> makeRandomQuestionResponseDtoList(){
        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();

         for (int i = 1; i <= 4; i++) {
             QuestionResponseDto dto = getQuestionsByType(i)
                     .map(list -> list.get(new Random().nextInt(list.size())))
                     .map(question -> new QuestionResponseDto(question.getQuestionContents()))
                     .orElseThrow(() -> new RuntimeException("No question found"));

             questionResponseDtoList.add(dto);
         }

         return questionResponseDtoList;
     }

    public List<String> makeRandomQuestionStringList(){

        List<String> questionStringList = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 4; i++) {
            getQuestionsByType(i).ifPresentOrElse(list ->
                    questionStringList.add(
                        list.get(random.nextInt(list.size()))
                            .getQuestionContents()),
                    () -> {
                        throw new NullPointerException("No question found");
                    }
            );
        }

        return questionStringList;
    }

    // 질문 등록하기
    public void addQuestion(String string, String stringType){
        questionRepository.save(
                Question.builder()
                        .questionContents(questionStringCheck(string))
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
