package hh.excellence.question.web;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import hh.excellence.question.dao.AnswerRepository;
import hh.excellence.question.dao.ChoiceRepository;
import hh.excellence.question.dao.QuestionRepository;
import hh.excellence.question.dao.UUIDRepository;
import hh.excellence.question.dto.OrderDTO;
import hh.excellence.question.dto.ChoiceDTO;
import hh.excellence.question.models.Answer;
import hh.excellence.question.models.Choice;
import hh.excellence.question.models.MyUUID;
import hh.excellence.question.models.Question;

@Controller
public class AdminController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ChoiceRepository choiceRepository;

    @Autowired
    UUIDRepository uuidRepository;


    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/questions";
        }
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "dashboard/index";
    }

    @ResponseBody
    @PostMapping("/changeQuestionOrders")
    public ObjectNode changeQuestionOrders(@RequestBody List<OrderDTO> orderDTOs) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode obj = mapper.createObjectNode();
        if(orderDTOs.size() > 0) {
            for(OrderDTO orderDTO : orderDTOs) {
                Question question = questionRepository.findById(orderDTO.getObjectId()).get();
                if(question.getOrderNumber() != orderDTO.getUpdatedOrderNumber()){
                    question.setOrderNumber(orderDTO.getUpdatedOrderNumber());
                    questionRepository.save(question);
                }
            }
        }
        obj.put("status", true);
        return obj;
    }

    @ResponseBody
    @PostMapping("/changeChoiceOrders")
    public ObjectNode changeChoiceOrders(@RequestBody List<OrderDTO> orderDTOs) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode obj = mapper.createObjectNode();
        obj.put("status", false);
        if(orderDTOs != null) {
            for(OrderDTO orderDTO : orderDTOs) {
                Choice choice = choiceRepository.findById(orderDTO.getObjectId()).get();
                if(choice.getOrderNumber() != orderDTO.getUpdatedOrderNumber()){
                    choice.setOrderNumber(orderDTO.getUpdatedOrderNumber());
                    choiceRepository.save(choice);
                }
            }
            obj.put("status", true);
        } 
        return obj;
    }

    @ResponseBody
    @PostMapping("/createChoice")
    public Choice changeChoiceOrders(@RequestBody ChoiceDTO choiceDTO) {
        Question question = questionRepository.findById(choiceDTO.getQuestionId()).get();
        Choice choice = new Choice();
        choice.setQuestion(question);
        choice.setChoice(choiceDTO.getChoice());
        choice.setOrderNumber(choiceRepository.countByQuestionId(choiceDTO.getQuestionId()) + 1);
        return choiceRepository.save(choice);
    }

    @GetMapping("/questions")
    public String questionsList(Model model) {
        model.addAttribute("questions", questionRepository.findAllByOrderByOrderNumberAsc());
        return "dashboard/questionlist";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        List<Answer> answers = questionRepository.findById(id).get().getAnswers();
        for (Answer answer : answers) {
            answerRepository.delete(answer);
        }
        List<Choice> choices = questionRepository.findById(id).get().getChoices();
        for (Choice choice : choices) {
            choiceRepository.delete(choice);
        }
    	questionRepository.deleteById(id);
    	return "redirect:/questions";
    }

    @GetMapping("/choice/delete/{id}")
    public String deleteChoice(@PathVariable Long id) {
        Choice choice = choiceRepository.findById(id).get();
        Long questionId = choice.getQuestion().getId();
        if(choice != null) {
            choiceRepository.delete(choice);
        }
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/newquestion")
    public String newQuestion(Model model) {
        model.addAttribute("quest", new Question());
        return "dashboard/addquestion";
    }

    @PostMapping("/save")
    public String saveNewQuestion(@ModelAttribute("quest") Question question) {
        question.setOrderNumber((int)questionRepository.count() + 1);
        questionRepository.save(question);
        return "redirect:/questions";
    }
    
    @GetMapping("/questions/{id}")
    public String viewQuestion(@PathVariable Long id, Model model, 
        @RequestParam(value="fromAction", required = false) String fromAction,
        @RequestParam(value="fromKey", required = false) Long fromKey) {
        Question question = questionRepository.findById(id).get();
        int numberOfAnswers = answerRepository.countByQuestionId(question.getId());
        boolean choiceable = question.getType().toString().equals("NUMBER") 
        || question.getType().toString().equals("INPUT") 
        || question.getType().toString().equals("TEXTAREA") 
        ? false : true;
        model.addAttribute("fromAction", fromAction);
        model.addAttribute("fromKey", fromKey);
        if(fromAction != null && fromAction.equals("viewAnswers")){
            MyUUID answerer = uuidRepository.findById(fromKey).get();
            model.addAttribute("answerer", answerer);
        }
        if(choiceable) {
            model.addAttribute("deletableChoices", numberOfAnswers > 0 ? false : true);
        }
        model.addAttribute("question", question);
        model.addAttribute("choiceable", choiceable);
        return "dashboard/question";
    }

    @GetMapping("/statistics")
    public String viewStatistics(Model model) {
        model.addAttribute("questionCount", (int) questionRepository.count());
        model.addAttribute("answerCount", (int) answerRepository.count());
        model.addAttribute("answererCount", (int) uuidRepository.count());
        return "dashboard/statistics";
    }

    @GetMapping("/answerers")
    public String viewAnswerers(Model model) {
        model.addAttribute("answerers", uuidRepository.findAll());
        return "dashboard/answerers";
    }

    @PostMapping("/answerers")
    public String passToAnswers(@ModelAttribute("answerer") MyUUID uuid, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("uuidId", uuid.getId());
        return "redirect:/answers";
    }

    @GetMapping("/answers")
    public String viewAnswers(Model model){
        if(model.getAttribute("uuidId") != null) {
            long uuid = (long) model.getAttribute("uuidId");
            List<Answer> answers = answerRepository.findByUuidId(uuid);
            model.addAttribute("answers", answers);
            return "dashboard/answers";
        } else {
            return "redirect:/answerers";
        }
    }

}