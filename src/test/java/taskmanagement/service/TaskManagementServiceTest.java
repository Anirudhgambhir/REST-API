//package taskmanagement.service;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.mockito.ArgumentCaptor;
//import taskManagement.exceptions.TaskNotFoundException;
//import taskManagement.service.Task;
//import taskManagement.service.TaskManagementRepository;
//import taskManagement.service.TaskManagementService;
//import taskManagement.service.TaskUpdate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//
//public class TaskManagementServiceTest {
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Test
//    public void testCreate() {
//        TaskManagementRepository repository = mock(TaskManagementRepository.class);
//        TaskManagementService service = new TaskManagementService(repository);
//
//        String title = "test-title";
//        String description = "test-description";
//
//        Task returnedTask = service.create(title, description);
//
//        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
//        verify(repository, times(1)).save(taskArgumentCaptor.capture());
//        verifyNoMoreInteractions(repository);
//
//        Task capturedTask = taskArgumentCaptor.getValue();
//        assertEquals(title, capturedTask.getTitle());
//        assertEquals(description, capturedTask.getDescription());
//
//        assertEquals(returnedTask, capturedTask);
//    }
//
//    @Test
//    public void testUpdate() {
//        TaskManagementRepository repository = mock(TaskManagementRepository.class);
//        TaskManagementService service = new TaskManagementService(repository);
//
//        Task existingTask = Task.builder("test-title", "test-description").build();
//        when(repository.get(anyString())).thenReturn(Optional.of(existingTask));
//
//        String newTitle = "new-title";
//        String newDescription = "new-description";
//        boolean isCompleted = true;
//        TaskUpdate update = new TaskUpdate(newTitle, newDescription, isCompleted);
//
//        Task updatedTask = Task.builder(newTitle, newDescription)
//                .withIdentifier(existingTask.getIdentifier())
//                .withCreatedAt(existingTask.getCreatedAt())
//                .withCompleted(isCompleted)
//                .build();
//
//        Task returnedTask = service.update(existingTask.getIdentifier(), update);
//
//        assertEquals(updatedTask, returnedTask);
//
//        verify(repository, times(1)).get(existingTask.getIdentifier());
//        verify(repository, times(1)).save(updatedTask);
//        verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    public void testUpdateTaskNotFound() {
//        TaskManagementRepository repository = mock(TaskManagementRepository.class);
//        TaskManagementService service = new TaskManagementService(repository);
//
//        when(repository.get(anyString())).thenReturn(Optional.empty());
//
//        String newTitle = "new-title";
//        String newDescription = "new-description";
//        boolean isCompleted = true;
//        TaskUpdate update = new TaskUpdate(newTitle, newDescription, isCompleted);
//
//        String taskID = "task-ID-123";
//
//        thrown.expect(TaskNotFoundException.class);
//        thrown.expectMessage("Task with the given identifier cannot be found - " + taskID);
//
//        try {
//            service.update(taskID, update);
//        } finally {
//            verify(repository, times(1)).get(taskID);
//            verifyNoMoreInteractions(repository);
//        }
//    }
//
//    @Test
//    public void testDelete() {
//        TaskManagementRepository repository = mock(TaskManagementRepository.class);
//        TaskManagementService service = new TaskManagementService(repository);
//
//        String taskID = "test-id-123";
//        service.delete(taskID);
//
//        verify(repository, times(1)).delete(taskID);
//        verifyNoMoreInteractions(repository);
//
//    }
//
//    @Test
//    public void testRetrieveAll() {
//        TaskManagementRepository repository = mock(TaskManagementRepository.class);
//        TaskManagementService service = new TaskManagementService(repository);
//
//        Task task = Task.builder("test-title-1", "test-description-1").build();
//        Task anotherTask = Task.builder("test-title-2", "test-description-2").build();
//        List<Task> tasks = new ArrayList<>();
//        tasks.add(task);
//        tasks.add(anotherTask);
//
//        when(repository.getAll()).thenReturn(tasks);
//
//        List<Task> retrievedTasks = service.retrieveAll();
//
//        assertEquals(tasks, retrievedTasks);
//    }
//
//    @Test
//    public void testRetrieve() {
//        TaskManagementRepository repository = mock(TaskManagementRepository.class);
//        TaskManagementService service = new TaskManagementService(repository);
//
//        Task task = Task.builder("test-title", "test-description").build();
//        when(repository.get(anyString())).thenReturn(Optional.of(task));
//
//        Task retrievedTask = service.retrieve(task.getIdentifier());
//
//        assertEquals(task, retrievedTask);
//
//        verify(repository, times(1)).get(task.getIdentifier());
//        verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    public void testRetrieveTaskNotFound() {
//        TaskManagementRepository repository = mock(TaskManagementRepository.class);
//        TaskManagementService service = new TaskManagementService(repository);
//
//        when(repository.get(anyString())).thenReturn(Optional.empty());
//
//        String taskID = "test-id-123";
//        thrown.expect(TaskNotFoundException.class);
//        thrown.expectMessage("Task with the given identifier cannot be found - " + taskID);
//
//        try {
//            service.retrieve(taskID);
//        } finally {
//            verify(repository, times(1)).get(taskID);
//            verifyNoMoreInteractions(repository);
//        }
//    }
//}