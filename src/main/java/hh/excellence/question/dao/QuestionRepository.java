package hh.excellence.question.dao;

import hh.excellence.question.models.Question;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    public List<Question> findAllByOrderByOrderNumberAsc();
}