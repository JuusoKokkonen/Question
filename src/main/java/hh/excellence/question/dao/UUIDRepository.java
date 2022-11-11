package hh.excellence.question.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hh.excellence.question.models.MyUUID;

@Repository
public interface UUIDRepository extends CrudRepository<MyUUID, Long> {

}