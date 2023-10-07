package taskManagement.exceptions.mapper;

import taskManagement.exceptions.ExceptionMessage;
import taskManagement.exceptions.InvalidRequestException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class InvalidRequestExceptionMapper implements ExceptionMapper<InvalidRequestException> {
    @Override
    public Response toResponse(InvalidRequestException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ExceptionMessage(e.getMessage()))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
