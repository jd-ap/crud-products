package io.tech.proof;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logmanager.Level;

import java.util.logging.Logger;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

    private Logger logger = Logger.getLogger(GeneralExceptionMapper.class.getName());

    @Override
    public Response toResponse(Exception e) {
        var error = new ErrorDto("undefined", e.getMessage(), new String[]{});

        logger.log(Level.SEVERE, e.getMessage());
        e.printStackTrace();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }
}
