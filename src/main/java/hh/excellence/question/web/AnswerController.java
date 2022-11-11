package hh.excellence.question.web;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import hh.excellence.question.dao.UUIDRepository;
import hh.excellence.question.dao.AnswerRepository;
import hh.excellence.question.dao.ChoiceRepository;
import hh.excellence.question.dao.QuestionRepository;
import hh.excellence.question.dto.AnswerDTO;
import hh.excellence.question.models.Answer;
import hh.excellence.question.models.Choice;
import hh.excellence.question.models.MyUUID;
import hh.excellence.question.models.Question;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
public class AnswerController {
    
    @Autowired
    private UUIDRepository uuidRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @PostMapping("/api/questions")
    public ObjectNode saveAnswers(@RequestBody List<AnswerDTO> answerDTOs){
        /* Should use of entity manager transaction property ? */
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode obj = mapper.createObjectNode();
        obj.put("status", false);
        if(answerDTOs.size() > 0) {
            /* Generating UUID for each query */
            /* Maybe make use of transaction so can remove uuid if answer saving goes wrong */
            MyUUID uuid = new MyUUID();
            uuidRepository.save(uuid);
            int succeededSaves = answerDTOs.size();
            for(AnswerDTO answerDTO : answerDTOs) {
                Question question = questionRepository.findById(answerDTO.getQuestionId()).get();
                Answer answer = answerDTO.createAnswer();
                answer.setUuid(uuid);
                answer.setQuestion(question);
                List<Long> choiceList = answerDTO.getChoices();
                if(choiceList != null && !choiceList.isEmpty()) {
                    Set<Choice> setOfChoices = new HashSet<>();
                    for(Long choiceId : choiceList){
                        Optional<Choice> optionalChoice = choiceRepository.findById(choiceId);
                        if(optionalChoice.isPresent()){
                            Choice choice = optionalChoice.get();
                            setOfChoices.add(choice);
                        }
                    }
                    answer.setChoices(setOfChoices);
                }
                if(answerRepository.save(answer) != null){
                    succeededSaves--;
                }
            }
            if(succeededSaves == 0){
                obj.put("status", true);
            }
        } 
        return obj;
    }
}