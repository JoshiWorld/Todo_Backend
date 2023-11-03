package de.mmbbs.springapi.controllers;

import de.mmbbs.springapi.model.ToDo;
import de.mmbbs.springapi.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/")
    public ResponseEntity<List<ToDo>> getAllTodos(@RequestParam(required = false) String title) {
        try {
            List<ToDo> toDoList = new ArrayList<ToDo>();

            if (title == null)
                todoRepository.findAll().forEach(toDoList::add);
            else
                todoRepository.findByTitleContaining(title).forEach(toDoList::add);

            if (toDoList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(toDoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable("id") String id) {
        Optional<ToDo> todoData = todoRepository.findById(id);
        return todoData.map(toDo -> new ResponseEntity<>(toDo, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<ToDo> createTodo(@RequestBody ToDo toDo) {
        try {
            ToDo _todo = todoRepository.save(new ToDo(toDo.getTitle(), toDo.getDescription(), false));
            return new ResponseEntity<>(_todo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateTodo(@PathVariable("id") String id, @RequestBody ToDo toDo) {
        Optional<ToDo> todoData = todoRepository.findById(id);

        if (todoData.isPresent()) {
            ToDo _todo = todoData.get();
            _todo.setTitle(toDo.getTitle());
            _todo.setDescription(toDo.getDescription());
            _todo.setDone(toDo.isDone());
            return new ResponseEntity<>(todoRepository.save(_todo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable("id") String id) {
        try {
            todoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllTodos() {
        try {
            todoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/done")
    public ResponseEntity<List<ToDo>> findByDone() {
        try {
            List<ToDo> toDoList = todoRepository.findByDone(true);

            if (toDoList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(toDoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
