package taskManagement.service;

import lombok.RequiredArgsConstructor;
import taskManagement.exceptions.TaskNotFoundException;

import javax.inject.Inject;
import java.util.List;
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TaskManagementService {
    private final TaskManagementRepository repository;

    public Task create(String title, String description)  {
        Task task = Task.builder(title, description).build();

        repository.save(task);

        return task;
    }

    public Task update(String taskID, TaskUpdate taskUpdate) {
        Task oldTask = retrieve(taskID);

        Task newTask = oldTask.update(taskUpdate);
        repository.save(newTask);

        return newTask;
    }

    public List<Task> retrieveAll() {
        return repository.getAll();
    }

    public Task retrieve(String taskID) {
        return repository.get(taskID).orElseThrow(() ->
                new TaskNotFoundException("Task with the given identifier cannot be found - " + taskID));
    }

    public void delete(String taskID) {
        repository.delete(taskID);
    }
}
