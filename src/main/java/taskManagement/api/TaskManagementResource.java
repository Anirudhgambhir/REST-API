package taskManagement.api;

import taskManagement.service.Task;
import taskManagement.service.TaskManagementService;
import taskManagement.service.TaskUpdate;
import taskManagement.validator.Validator;
import taskManagement.validator.impl.ObjectValidatorImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tasks")
public class TaskManagementResource {

    private final TaskManagementService service;

    public TaskManagementResource(TaskManagementService service) {
        this.service = service;
    }

    private final static Validator<Object> validator = new ObjectValidatorImpl();

    //TODO Implementation to be added as per API request
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTask(TaskCreateRequest taskCreateRequest) {
        validator.validate(taskCreateRequest);

        Task task = service.create(taskCreateRequest.getTitle(), taskCreateRequest.getDescription());

        String taskID = task.getIdentifier();

        URI taskRelativeURI = URI.create("tasks/" + taskID);
        return Response.created(taskRelativeURI).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskResponse> getTasks() {
        return service.retrieveAll().stream()
                .map(TaskResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @PATCH
    @Path("/{taskID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("taskID") String taskID, TaskUpdateRequest taskUpdateRequest) {
        validator.validate(taskUpdateRequest);

        TaskUpdate update = new TaskUpdate(taskUpdateRequest.getTitle(), taskUpdateRequest.getDescription(),
                taskUpdateRequest.getCompleted());

        service.update(taskID, update);
        return Response.ok().build();
    }

    @GET
    @Path("/{taskID}")
    @Produces(MediaType.APPLICATION_JSON)
    public TaskResponse getTask(@PathParam("taskID") String taskID) {
        Task task = service.retrieve(taskID);
        return new TaskResponse(task);
    }

    @DELETE
    @Path("/{taskID}")
    public Response deleteTask(@PathParam("taskID") String taskID) {
        service.delete(taskID);
        return Response.noContent().build();
    }
}
