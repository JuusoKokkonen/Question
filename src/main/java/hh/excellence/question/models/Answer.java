package hh.excellence.question.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="answers")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Answer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @Size(max=1000)
    @Column(name="free_text")
    private String freeText;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="question_id", nullable=false)
    private Question question;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="uuid", nullable=false)
    private MyUUID uuid;
    
    @JsonIgnore
    @ManyToMany 
    @JoinTable(name="answers_choices",
        joinColumns={@JoinColumn(name="choice_id")},
        inverseJoinColumns={@JoinColumn(name="answer_id")}
    )
    private Set<Choice> choices;
}