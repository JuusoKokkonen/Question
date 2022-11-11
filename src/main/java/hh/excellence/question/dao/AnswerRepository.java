package hh.excellence.question.dao;

import hh.excellence.question.models.Answer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    public List<Answer> findByUuidId(Long uuidId);
    public int countByQuestionId(Long questionId);
}