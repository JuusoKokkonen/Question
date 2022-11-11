package hh.excellence.question.web;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hh.excellence.question.dao.QuestionRepository;
import hh.excellence.question.models.Question;

@RestController
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping(path = "/api/questions/{id}", produces = "application/json")
    public ObjectNode getQuestionByQuestionId(@PathVariable Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode obj = mapper.createObjectNode();
        Optional<Question> question = questionRepository.findById(id);
        obj.put("status", false);        
        if(question.isPresent()) {
            obj.put("status", true);
            obj.set("question", mapper.valueToTree(question.get()));
        }
        return obj;
    }

}