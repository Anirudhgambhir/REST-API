package taskmanagement.storage;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import taskManagement.helper.MongoDBTask;
import taskManagement.service.Task;
import taskManagement.storage.MongoDBTaskManagementRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


public class MongoDBTaskManagementRepositoryTest {

    @Mock
    MongoCollection<MongoDBTask> collection;

    @Mock
    FindIterable<MongoDBTask> findIterable;

    @Mock
    MongoCursor<MongoDBTask> mongoCursor;

    @InjectMocks
    MongoDBTaskManagementRepository repository;

    @Captor
    ArgumentCaptor<ReplaceOptions> replaceOptionsArgumentCaptor;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {

        Instant now = Instant.parse("2022-07-27T19:09:04.120Z");
        Task task = Task.builder("test-title", "test-description")
                .withCreatedAt(now)
                .withCompleted(true)
                .build();

        repository.save(task);

        MongoDBTask mongoDBTask = new MongoDBTask();
        mongoDBTask.setCompleted(task.isCompleted());
        mongoDBTask.setCreatedAt(now.toEpochMilli());
        mongoDBTask.setIdentifier(task.getIdentifier());
        mongoDBTask.setTitle(task.getTitle());
        mongoDBTask.setDescription(task.getDescription());

        verify(collection).replaceOne(eq(Filters.eq("_id", task.getIdentifier())), eq(mongoDBTask), replaceOptionsArgumentCaptor.capture());
        verifyNoMoreInteractions(collection);

        assertTrue(replaceOptionsArgumentCaptor.getValue().isUpsert());
    }

    @Test
    public void testGetAll() {
        Instant now = Instant.parse("2022-07-27T19:09:04.120Z");
        Task task = Task.builder("test-title", "test-description")
                .withCreatedAt(now)
                .withCompleted(true)
                .build();

        MongoDBTask mongoDBTask = new MongoDBTask();
        mongoDBTask.setCompleted(task.isCompleted());
        mongoDBTask.setCreatedAt(now.toEpochMilli());
        mongoDBTask.setIdentifier(task.getIdentifier());
        mongoDBTask.setTitle(task.getTitle());
        mongoDBTask.setDescription(task.getDescription());

        when(mongoCursor.hasNext()).thenReturn(true, false);
        when(mongoCursor.next()).thenReturn(mongoDBTask);
        when(findIterable.iterator()).thenReturn(mongoCursor);
        when(collection.find()).thenReturn(findIterable);

        List<Task> tasks = repository.getAll();

        assertEquals(Collections.singletonList(task), tasks);
        verify(collection).find();
        verifyNoMoreInteractions(collection);
    }

    @Test
    public void testGet() {

        Instant now = Instant.parse("2022-07-27T19:09:04.120Z");

        Task expectedTask = Task.builder("test-title", "test-description")
                .withCreatedAt(now)
                .withCompleted(true)
                .build();

        MongoDBTask mongoDBTask = new MongoDBTask();
        mongoDBTask.setCompleted(expectedTask.isCompleted());
        mongoDBTask.setCreatedAt(now.toEpochMilli());
        mongoDBTask.setIdentifier(expectedTask.getIdentifier());
        mongoDBTask.setTitle(expectedTask.getTitle());
        mongoDBTask.setDescription(expectedTask.getDescription());

        when(findIterable.first()).thenReturn(mongoDBTask);
        when(collection.find(Filters.eq("_id", expectedTask.getIdentifier()))).thenReturn(findIterable);

        Optional<Task> actualTask = repository.get(expectedTask.getIdentifier());

        assertTrue(actualTask.isPresent());
        assertEquals(expectedTask, actualTask.get());

        verify(collection).find(Filters.eq("_id", expectedTask.getIdentifier()));
        verifyNoMoreInteractions(collection);
    }

    @Test
    public void testGetForNullTask() {

        String taskID = "test-task-id";
        when(findIterable.first()).thenReturn(null);
        when(collection.find(Filters.eq("_id", taskID))).thenReturn(findIterable);

        Optional<Task> task = repository.get(taskID);

        assertFalse(task.isPresent());

        verify(collection).find(Filters.eq("_id", taskID));
        verifyNoMoreInteractions(collection);
    }

    @Test
    public void testDelete() {
        String taskID = "test-task-id";

        repository.delete(taskID);

        verify(collection).deleteOne(new Document("_id", taskID));
        verifyNoMoreInteractions(collection);
    }

}