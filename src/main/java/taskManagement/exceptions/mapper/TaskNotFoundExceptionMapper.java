package taskManagement.exceptions.mapper;

import taskManagement.exceptions.ExceptionMessage;
import taskManagement.exceptions.TaskNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TaskNotFoundExceptionMapper implements ExceptionMapper<TaskNotFoundException> {

    @Override
    public Response toResponse(TaskNotFoundException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ExceptionMessage(e.getMessage()))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
