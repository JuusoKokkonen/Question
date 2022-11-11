package hh.excellence.question.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.excellence.question.dao.QuestionRepository;

@RestController
@CrossOrigin
public class SurveyController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping(path = "/api/questions", produces = "application/json")
    public ObjectNode getQuestions() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode obj = mapper.createObjectNode();
        obj.set("questions", mapper.valueToTree(questionRepository.findAllByOrderByOrderNumberAsc()));
        return obj;
    }
}