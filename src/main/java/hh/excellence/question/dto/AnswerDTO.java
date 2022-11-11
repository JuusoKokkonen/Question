package hh.excellence.question.dto;

import java.util.List;

import org.springframework.stereotype.Service;

import hh.excellence.question.models.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

//This class converts our recieved POST to an Answer-class
@NoArgsConstructor
@Data
@Service
public class AnswerDTO {
    private Long questionId;
    private String freeText;
    private List<Long> choices;
    public Answer createAnswer(){
        Answer answer = new Answer();
        if(freeText != null){
            answer.setFreeText(freeText);
        }
        return answer;
    }
}