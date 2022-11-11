package hh.excellence.question.dao;

import hh.excellence.question.models.Choice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends CrudRepository<Choice, Long> {
    public int countByQuestionId(Long questionId);
}