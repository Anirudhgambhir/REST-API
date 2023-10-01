package taskManagement.api;

import lombok.Getter;
import taskManagement.service.Task;

import java.util.Objects;

@Getter
public class TaskResponse {
    private final String identifier;
    private final String title;
    private final String description;
    private final String createdAt;
    private final boolean completed;

    public TaskResponse(Task task) {
        this.identifier = task.getIdentifier();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.createdAt = task.getCreatedAt().toString();
        this.completed = task.isCompleted();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TaskResponse that = (TaskResponse) o;

        return completed == that.completed
                && Objects.equals(identifier, that.identifier)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, title, description, createdAt, completed);
    }

}
