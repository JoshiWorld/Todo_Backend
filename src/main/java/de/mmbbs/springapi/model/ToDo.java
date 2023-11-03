package de.mmbbs.springapi.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Document(collection = "todos")
public class ToDo {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean done;

    public ToDo() {

    }

    public ToDo(String title, String description, boolean done) {
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ToDo))
            return false;
        ToDo toDo = (ToDo) o;
        return Objects.equals(this.id, toDo.id) && Objects.equals(this.title, toDo.title)
                && Objects.equals(this.description, toDo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description);
    }
}
