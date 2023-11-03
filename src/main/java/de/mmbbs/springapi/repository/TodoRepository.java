package de.mmbbs.springapi.repository;

import de.mmbbs.springapi.model.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodoRepository extends MongoRepository<ToDo, String> {
    List<ToDo> findByTitleContaining(String title);
    List<ToDo> findByDescriptionContaining(String description);
    List<ToDo> findByDone(boolean done);
}
