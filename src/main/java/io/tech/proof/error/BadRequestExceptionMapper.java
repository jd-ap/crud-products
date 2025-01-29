package io.tech.proof.error;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {

        var error = new ErrorDto("BAD_REQUEST", exception.getMessage(), null);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }

}
