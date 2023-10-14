package taskManagement.storage;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import taskManagement.helper.MongoDBTask;
import taskManagement.service.Task;
import taskManagement.service.TaskManagementRepository;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MongoDBTaskManagementRepository implements TaskManagementRepository {


    private final MongoCollection<MongoDBTask>  tasksCollection;

    @Override
    public void save(Task task) {
        MongoDBTask mongoDBTask = toMongoDBTask(task);
        ReplaceOptions replaceOptions = new ReplaceOptions()
                .upsert(true);
        tasksCollection.replaceOne(eq("_id", task.getIdentifier()), mongoDBTask, replaceOptions);
    }

    @Override
    public List<Task> getAll() {
        FindIterable<MongoDBTask> mongoDBTasks = tasksCollection.find();

        List<Task> tasks = new ArrayList<>();
        for (MongoDBTask mongoDBTask : mongoDBTasks) {
            tasks.add(fromMongoDBTask(mongoDBTask));
        }

        return tasks;
    }

    @Override
    public Optional<Task> get(String taskID) {
        Optional<MongoDBTask> mongoDBTask = Optional.ofNullable(
                tasksCollection.find(eq("_id", taskID)).first());

        return mongoDBTask.map(this::fromMongoDBTask);
    }

    @Override
    public void delete(String taskID) {
        Document taskIDFilter = new Document("_id", taskID);
        tasksCollection.deleteOne(taskIDFilter);
    }

    private Task fromMongoDBTask(MongoDBTask mongoDBTask) {
        return Task.builder(mongoDBTask.getTitle(), mongoDBTask.getDescription())
                .withIdentifier(mongoDBTask.getIdentifier())
                .withCompleted(mongoDBTask.isCompleted())
                .withCreatedAt(Instant.ofEpochMilli(mongoDBTask.getCreatedAt()))
                .build();
    }

    private MongoDBTask toMongoDBTask(Task task) {
        MongoDBTask mongoDBTask = new MongoDBTask();
        mongoDBTask.setIdentifier(task.getIdentifier());
        mongoDBTask.setTitle(task.getTitle());
        mongoDBTask.setDescription(task.getDescription());
        mongoDBTask.setCreatedAt(task.getCreatedAt().toEpochMilli());
        mongoDBTask.setCompleted(task.isCompleted());

        return mongoDBTask;
    }
}
