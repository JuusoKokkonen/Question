package hh.excellence.question.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import hh.excellence.question.models.QuestionType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="questions")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @NotNull
    @Size(max=1000)
    @Column(name="question", nullable=false)
    private String question;

    @NotNull
    @Column(name="order_number", nullable=false, columnDefinition = "integer default 0")
    private int orderNumber;

    @Enumerated(EnumType.ORDINAL)
    private QuestionType type;

    @JsonIgnore
    @OneToMany(mappedBy="question")
    private List<Answer> answers;

    @JsonManagedReference
    @OneToMany(mappedBy="question")
    private List<Choice> choices;

    public String getQuestionTypeDescription(int questionType) {
        String description;
        switch(questionType){
            case 0:
                description = "Select One";
            break;
            case 1:
                description = "Select Multiple";
            break;
            case 2:
                description = "Select One (Dropdown)";
            break;
            case 3:
                description = "Free Text (Multi line)";
            break;
            case 4:
                description = "Free Text (Single line)";
            break;
            case 5:
                description = "Number Field";
            break;
            case 6:
                description = "Slider";
            break;
            default:
                description = "No Information Given";
        }
        return description;
    }
}