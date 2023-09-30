package taskManagement.service;

import lombok.Getter;
import taskManagement.validator.impl.TaskUpdateValidatorImpl;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Task {
    private final String identifier;
    private final String title;
    private final String description;
    private final Instant createdAt;
    private final boolean completed;


    private final TaskUpdateValidatorImpl validator = new TaskUpdateValidatorImpl();


    private Task(TaskBuilder builder){
        this.identifier = builder.identifier;
        this.title = builder.title;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
        this.completed = builder.completed;
    }

    public Task update(TaskUpdate update) {
        validator.validate(update);
        return Task.builder(update.getTitle(), update.getDescription()).build();
    }

    public static TaskBuilder builder(String title, String description) {
        return new TaskBuilder(title, description);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return completed == task.completed
                && identifier.equals(task.identifier)
                && title.equals(task.title)
                && description.equals(task.description)
                && createdAt.equals(task.createdAt);
    }

    public static class TaskBuilder {
        private String title;
        private String description;

        private String identifier;
        private Instant createdAt;
        private boolean completed;

        private TaskBuilder(String title, String description) {
            this.title = title;
            this.description = description;
            this.identifier = UUID.randomUUID().toString();
            this.createdAt = Instant.now();
            this.completed = false;
        }

        public TaskBuilder withTitle(String title) {

            this.title = title;
            return this;
        }

        public TaskBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder withIdentifier(String identifier) {

            this.identifier = identifier;
            return this;
        }

        public TaskBuilder withCreatedAt(Instant createdAt) {

            this.createdAt = createdAt;
            return this;
        }

        public TaskBuilder withCompleted(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

}
