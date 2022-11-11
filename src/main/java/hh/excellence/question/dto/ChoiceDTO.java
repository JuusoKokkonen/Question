package hh.excellence.question.dto;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Service
public class ChoiceDTO {
    private Long questionId;
    private String choice;
}